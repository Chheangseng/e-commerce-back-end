package com.tcs.e_commerce_back_end.exception.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ApiException {
  private final String massage;
  private final int statusCode;
  private final HttpStatus status;
  private final ZonedDateTime zonedDateTime;
}
