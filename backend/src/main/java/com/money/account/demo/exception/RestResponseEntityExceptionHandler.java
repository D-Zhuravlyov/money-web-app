package com.money.account.demo.exception;

import com.money.account.demo.exception.custom.UserNotFoundException;
import com.money.account.demo.exception.custom.WithdrawOperationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value =  Exception.class)
    protected ResponseEntity<Object> handleGlobalException(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Server error occurred. Please contact your system administrator.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = WithdrawOperationException.class)
    protected ResponseEntity<Object> handleOverlimitedWithdraw(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Unable to proceed the operation: insufficient funds.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    protected ResponseEntity<Object> handleUnknownUserId(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Unable to find user with provided id.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


}