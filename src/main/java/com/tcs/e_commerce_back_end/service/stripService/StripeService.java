package com.tcs.e_commerce_back_end.service.stripService;

import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.service.stripService.dto.DtoCreateCheckout;
import com.tcs.e_commerce_back_end.service.stripService.dto.DtoItem;
import com.tcs.e_commerce_back_end.service.stripService.enums.SupportCurrency;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
  public String createCheckoutSession(DtoCreateCheckout checkout) {
    if (Objects.isNull(checkout.getItems()) || checkout.getItems().isEmpty()) {
      throw new ApiExceptionStatusException("Have an least one Item", 400);
    }
    SessionCreateParams params =
        SessionCreateParams.builder()
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.ALIPAY)
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(checkout.getSuccessUrl())
            .setCancelUrl(checkout.getCancelUrl())
            .addAllLineItem(handleItems(checkout.getItems()))
            .build();
    try {
      Session session = Session.create(params);
      return session.getUrl();
    } catch (StripeException stripeException) {
      throw new ApiExceptionStatusException(stripeException.getMessage(), 400);
    }
  }

  public List<SessionCreateParams.LineItem> handleItems(List<DtoItem> dtoItems) {
    List<SessionCreateParams.LineItem> result = new ArrayList<>();
    dtoItems.forEach(
        dtoItem -> {
          result.add(
              SessionCreateParams.LineItem.builder()
                  .setPriceData(
                      SessionCreateParams.LineItem.PriceData.builder()
                          .setCurrency(
                              Objects.requireNonNullElse(
                                  dtoItem.getCurrency().toString(), SupportCurrency.USD.toString()))
                          .setUnitAmount(dtoItem.getUnitPrice() * 100)
                          .setProductData(
                              SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                  .setName(dtoItem.getName())
                                  .setDescription(dtoItem.getDescription())
                                  .addImage(dtoItem.getUrlImage())
                                  .build())
                          .build())
                  .setQuantity(dtoItem.getQuantity())
                  .build());
        });
    return result;
  }
}
