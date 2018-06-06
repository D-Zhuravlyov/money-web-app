package com.money.account.demo.service;

import com.money.account.demo.exception.custom.UserNotFoundException;

import java.math.BigDecimal;

public interface OperationsService {

    void withdrawMoneyFromUserByUserId(final String userId, final BigDecimal amount)
            throws UserNotFoundException;

    void addMoneyToUserByUserId(final String userId, final BigDecimal amount)
            throws UserNotFoundException;

}
