package com.money.account.demo.service;

import com.money.account.demo.exception.custom.UserNotFoundException;
import com.money.account.demo.model.MoneyTransaction;
import java.util.List;


public interface AccountService {

    List<MoneyTransaction> getTransactionsHistoryByUserId(long userId) throws UserNotFoundException;

}
