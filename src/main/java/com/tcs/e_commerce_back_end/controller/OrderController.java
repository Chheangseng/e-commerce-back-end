package com.tcs.e_commerce_back_end.controller;

import com.tcs.e_commerce_back_end.emuns.OrderStatus;
import com.tcs.e_commerce_back_end.emuns.ValidateOrder;
import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.order.DtoMailMessage;
import com.tcs.e_commerce_back_end.model.dto.order.DtoOrderDetail;
import com.tcs.e_commerce_back_end.model.dto.order.DtoOrdersList;
import com.tcs.e_commerce_back_end.model.dto.order.DtoUpdateOrder;
import com.tcs.e_commerce_back_end.model.dto.order.crud.DtoCreateOrder;
import com.tcs.e_commerce_back_end.model.modelAttribute.ModelPagination;
import com.tcs.e_commerce_back_end.service.OrderService;
import com.tcs.e_commerce_back_end.service.authcode.OneTimeCodeService;
import com.tcs.e_commerce_back_end.utils.pageable.PaginationEntityResponse;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/order")
public class OrderController {
    private final OrderService service;
    private final OneTimeCodeService oneTimeCodeService;
    
    public OrderController(OrderService service, OneTimeCodeService oneTimeCodeService) {
        this.service = service;
        this.oneTimeCodeService = oneTimeCodeService;
    }
    @GetMapping
    public ResponseEntity<PaginationEntityResponse<DtoOrdersList>> getOrdersList (
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search,
            @ModelAttribute ModelPagination pagination
    ){
        return ResponseEntity.ok(new PaginationEntityResponse<>(service.listOrder(status,search,pagination)));
    }
    @GetMapping("/code/{code}")
    public ResponseEntity<String> verifyCode (@PathVariable String code){
        var res = oneTimeCodeService.useCode(code);
        if (res.getValue().isPresent()){
            return ResponseEntity.ok(service.paymentCompleteOrder(Integer.parseInt(res.getValue().get())));
        }
        throw new ApiExceptionStatusException("something went wrong",400);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoOrderDetail> viewDetail (
            @PathVariable Long id
            ) {
        return ResponseEntity.ok(service.getViewDetail(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDetail (
            @PathVariable Long id,
            @RequestBody DtoUpdateOrder order
    ) {
        service.updateOrder(id,order);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<String> orderProduct (@RequestBody DtoCreateOrder order) {
        return ResponseEntity.ok(service.orderProduct(order));
    }

    @PostMapping("/message")
    public ResponseEntity<Void> orderSendMessage (@Valid @RequestBody DtoMailMessage message) {
        service.sendOrderEmailUser(message);
        return ResponseEntity.ok().build();
    }
    @PostMapping(value = "upload-receipt/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadReceipt (@RequestParam MultipartFile file,@PathVariable Long id){
        service.uploadReceipt(file,id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Void> updateOrderStatus (@RequestParam OrderStatus status, @PathVariable Long id){
        service.updateOrderStatus(id,status);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteOrder (@RequestParam Long id){
        service.deleteOrders(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> viewOrder (@PathVariable String id){
        final var documentContent = service.viewImage(id);
        var headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, documentContent.getContentType());
        return ResponseEntity.ok().headers(headers).body(documentContent.getResource());
    }
    @PostMapping("validate/{id}")
    public ResponseEntity<Void> validateProduct (@PathVariable Long id, @RequestParam ValidateOrder order){
        service.validateProduct(id,order);
        return ResponseEntity.ok().build();
    }
}
