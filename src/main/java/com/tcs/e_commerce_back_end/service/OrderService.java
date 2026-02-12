package com.tcs.e_commerce_back_end.service;

import com.tcs.e_commerce_back_end.emuns.NotificationType;
import com.tcs.e_commerce_back_end.emuns.OrderStatus;
import com.tcs.e_commerce_back_end.emuns.ValidateOrder;
import com.tcs.e_commerce_back_end.emuns.file.FileDirCategory;
import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.notification.DtoNotification;
import com.tcs.e_commerce_back_end.model.dto.order.*;
import com.tcs.e_commerce_back_end.model.dto.order.crud.DtoCreateOrder;
import com.tcs.e_commerce_back_end.model.dto.order.crud.DtoItemOrder;
import com.tcs.e_commerce_back_end.model.entity.order.Orders;
import com.tcs.e_commerce_back_end.model.mapper.OrderListMapper;
import com.tcs.e_commerce_back_end.model.mapper.OrderMapper;
import com.tcs.e_commerce_back_end.model.mapper.PaymentMapper;
import com.tcs.e_commerce_back_end.model.modelAttribute.ModelPagination;
import com.tcs.e_commerce_back_end.repository.order.OrderRepository;
import com.tcs.e_commerce_back_end.repository.product.ProductInventoryRepository;
import com.tcs.e_commerce_back_end.service.authcode.OneTimeCodeService;
import com.tcs.e_commerce_back_end.service.file.FileSystemService;
import com.tcs.e_commerce_back_end.service.mail.MailService;
import com.tcs.e_commerce_back_end.service.stripService.StripeService;
import com.tcs.e_commerce_back_end.service.user.UserAuthService;
import com.tcs.e_commerce_back_end.utils.DocumentContent;
import com.tcs.e_commerce_back_end.utils.url.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class OrderService {
  private final UserAuthService authService;
  private final MailService mailService;
  private final OneTimeCodeService oneTimeCodeService;
  private final StripeService paymentService;
  private final HttpServletRequest request;
  private final OrderRepository orderRepository;
  private final NotificationService notificationService;
  private final ProductInventoryRepository inventoryRepository;

  private final FileSystemService fileSystemService;

  @Transactional
  public String orderProduct(DtoCreateOrder dtoOrder) {
    var entityOrder = OrderMapper.mapToEntity(dtoOrder);
    var user = authService.securityContextUser();
    entityOrder.setUserAccount(user);
    var checkout = PaymentMapper.checkout(dtoOrder);
    var item = dtoOrder.getProductOrders();
    var inventories =
        inventoryRepository.findAllByIdIn(item.stream().map(DtoItemOrder::getInventoryId).toList());
    if (Objects.isNull(inventories) || inventories.isEmpty()) {
      throw new ApiExceptionStatusException("All id is incorrect", 400);
    }
    for (var orderItem : item) {
      for (var inventoryItem : inventories) {
        if (orderItem.getInventoryId().equals(inventoryItem.getId())) {
          entityOrder
              .getOrderProducts()
              .add(OrderMapper.mapItem(entityOrder, orderItem, inventoryItem));
          checkout
              .getItems()
              .add(PaymentMapper.mapItem(orderItem, inventoryItem.getProduct(), request));
          break;
        }
      }
    }
    OrderMapper.mapTotalAndProfile(entityOrder);
    var orderResponse = orderRepository.save(entityOrder);
    var successUrl =
        UrlUtils.addParameter(
            checkout.getSuccessUrl(),
            "code",
            oneTimeCodeService.generateCode(orderResponse.getId().toString()));
    checkout.setSuccessUrl(successUrl);
    return paymentService.createCheckoutSession(checkout);
  }

  public String paymentCompleteOrder(long id) {
    var order =
        orderRepository
            .findById(id)
            .orElseThrow(() -> new ApiExceptionStatusException("Invalid Order " + id, 400));
    BigDecimal profile = new BigDecimal(0);
    for (var item : order.getOrderProducts()) {
      var findInventory = item.getInventory();
      findInventory.setStock(findInventory.getStock() - item.getQuantity());
      inventoryRepository.save(findInventory);
      profile =
          profile.add(item.getUnitPrice().subtract(findInventory.getProduct().getSellPrice()));
    }
    order.setHavePay(true);
    var response = orderRepository.save(order);
    return response.getReceiptId();
  }

  public Page<DtoOrdersList> listOrder(String status, String search, ModelPagination pagination) {
    long userId = 0;
    var entity =
        orderRepository.filterOrders(
            ObjectUtils.defaultIfNull(status, ""),
            ObjectUtils.defaultIfNull(search, ""),
            userId,
            pagination.toPageable());
    return entity.map(OrderListMapper::mapItem);
  }

  public DtoOrderDetail getViewDetail(long id) {
    var entity = findOrder(id);
    var responseOrderItems = new ArrayList<DtoOrderItem>();
    var response = new DtoOrderDetail(entity);
    if (Objects.nonNull(entity.getOrderProducts()) && !entity.getOrderProducts().isEmpty()) {
      for (var item : entity.getOrderProducts()) {
        var dtoItem = new DtoOrderItem(item);
        responseOrderItems.add(dtoItem);
      }
    }
    response.setProductOrders(responseOrderItems);
    return response;
  }

  public void updateOrder(long id, DtoUpdateOrder dto) {
    var entity = findOrder(id);
    dto.convertToOrdersEntity(entity);
    orderRepository.save(entity);
  }

  public void sendOrderEmailUser(DtoMailMessage message) {
    mailService.sendMail(message);
  }

  public void updateOrderStatus(long id, OrderStatus status) {
    var entity = findOrder(id);
    entity.setStatus(status);
    var orderEntity = orderRepository.save(entity);
    var notification = new DtoNotification();
    Map<String, Long> objectRef = new HashMap<>();
    objectRef.put("orderId", entity.getId());
    notification.setJsonObject(objectRef);
    notification.setUserId(orderEntity.getUserAccount().getId());
    notification.setTitle("Order Status");
    notification.setSubtitle("Your order Status have been updated : " + orderEntity.getStatus());
    notification.setNotificationType(NotificationType.ORDER);
    notificationService.createNotification(notification);
    notificationService.pushNotification();
  }

  @Transactional
  public void deleteOrders(long id) {
    orderRepository.deleteById(id);
  }

  public void uploadReceipt(MultipartFile file, long id) {
    var order = findOrder(id);
    var fileId = fileSystemService.saveFile(file, FileDirCategory.ORDER_FILES);
    order.setReceiptId(fileId);
    orderRepository.save(order);
  }

  public DocumentContent viewImage(String id) {
    return fileSystemService
        .viewImage(id)
        .orElseThrow(
            () -> new ApiExceptionStatusException("this order have not upload receipt yet", 404));
  }

  public void validateProduct(long id, ValidateOrder validateOrder) {
    var order = findOrder(id);
    if (validateOrder.equals(ValidateOrder.VALID)) {
      order.setStatus(OrderStatus.WAITING_FOR_SHIP);
    } else {
      order.setStatus(OrderStatus.REFUSE);
    }
    orderRepository.save(order);
  }

  private Orders findOrder(long id) {
    return orderRepository
        .findById(id)
        .orElseThrow(() -> new ApiExceptionStatusException("unable to find this order", 400));
  }
}
