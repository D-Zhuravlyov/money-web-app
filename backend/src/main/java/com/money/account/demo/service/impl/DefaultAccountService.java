package com.money.account.demo.service.impl;

import com.money.account.demo.exception.custom.UserNotFoundException;
import com.money.account.demo.model.MoneyTransaction;
import com.money.account.demo.model.User;
import com.money.account.demo.repository.MoneyTransactionRepository;
import com.money.account.demo.repository.UserRepository;
import com.money.account.demo.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DefaultAccountService implements AccountService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MoneyTransactionRepository moneyTransactionRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    @ReadOnlyProperty
    public List<MoneyTransaction> getTransactionsHistoryByUserId(long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Unknown id: " +userId ));

        LOG.debug("Found user: " +userId +";  " + user.getName());

        return moneyTransactionRepository.findByUserReturnStream(user);
    }

}
