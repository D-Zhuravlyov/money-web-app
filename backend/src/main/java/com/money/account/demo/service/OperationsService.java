package com.money.account.demo.service;

import com.money.account.demo.exception.custom.UserNotFoundException;
import com.money.account.demo.model.MoneyTransaction;

import java.math.BigDecimal;

public interface OperationsService {

    MoneyTransaction withdrawMoneyFromUserByUserId(final String userId, final BigDecimal amount)
            throws UserNotFoundException;

    MoneyTransaction addMoneyToUserByUserId(final String userId, final BigDecimal amount)
            throws UserNotFoundException;

}
