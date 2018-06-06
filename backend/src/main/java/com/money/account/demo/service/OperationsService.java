package com.money.account.demo.service;

import com.money.account.demo.exception.custom.UserNotFoundException;
import com.money.account.demo.model.OperationType;

import java.math.BigDecimal;

public interface OperationsService {

    void performMoneyOperationByUserId(final String userId, final BigDecimal amount, OperationType operationType)
            throws UserNotFoundException;

}
