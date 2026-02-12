package com.tcs.e_commerce_back_end.exception;

import lombok.experimental.StandardException;

@StandardException
public class LoginFailedException extends TechnicalException {
    @Override
    protected int statusCode() {
        return 401;
    }
}
