package com.money.account.demo.exception.custom;

public class WithdrawOperationException extends RuntimeException {
    public WithdrawOperationException(String message) {
        super(message);
    }
}
