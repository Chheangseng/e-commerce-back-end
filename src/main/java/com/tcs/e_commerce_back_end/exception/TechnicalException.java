package com.tcs.e_commerce_back_end.exception;

import lombok.experimental.StandardException;

@StandardException
public class TechnicalException extends RuntimeException {
  protected int statusCode() {
    return 500;
  }
}
