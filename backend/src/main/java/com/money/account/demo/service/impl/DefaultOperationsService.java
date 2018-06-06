package com.money.account.demo.service.impl;

import com.money.account.demo.exception.custom.UserNotFoundException;
import com.money.account.demo.exception.custom.WithdrawOperationException;
import com.money.account.demo.model.MoneyTransaction;
import com.money.account.demo.model.User;
import com.money.account.demo.repository.MoneyTransactionRepository;
import com.money.account.demo.repository.UserRepository;
import com.money.account.demo.service.OperationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

import static com.money.account.demo.model.OperationType.ADD;
import static com.money.account.demo.model.OperationType.WITHDRAW;
import static com.money.account.demo.service.ServiceUtils.calculateNewBalance;
import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
public class DefaultOperationsService implements OperationsService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserRepository userRepository;

    @Resource
    private MoneyTransactionRepository moneyTransactionRepository;

    @Transactional
    public void addMoneyToUserByUserId(final String userId, final BigDecimal amount) throws UserNotFoundException {
        final User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new UserNotFoundException("Unknown id: " + userId));

        LOG.debug("Starting adding money to user.");
        LOG.debug("Found user: " + userId + ";  " + user.getName());

        final MoneyTransaction lastTransaction = moneyTransactionRepository.findAll(sortByDate()).stream()
                .findFirst().orElse(null);

        LOG.debug("Found lastTransaction: " + lastTransaction + "; Balance: " + lastTransaction
                .getRemainedBalance().setScale(2, ROUND_HALF_UP));

        final BigDecimal newBalance = calculateNewBalance(lastTransaction, amount, ADD);

        final MoneyTransaction currentTransaction = new MoneyTransaction(new Date(), newBalance, amount, ADD,
                user);

        moneyTransactionRepository.saveAndFlush(currentTransaction);
        LOG.debug("New transaction flushed.");
    }

    @Transactional
    public void withdrawMoneyFromUserByUserId(final String userId, final BigDecimal amount)
            throws UserNotFoundException, WithdrawOperationException {
        final User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new UserNotFoundException("Unknown id: " + userId));

        LOG.debug("Starting withdrawing money from user.");
        LOG.debug("Found user: " + userId + ";  " + user.getName());

        final MoneyTransaction lastTransaction = moneyTransactionRepository.findAll(sortByDate()).stream()
                .findFirst().orElse(null);

        LOG.debug("Found lastTransaction: " + lastTransaction + "; Balance: " + lastTransaction
                .getRemainedBalance().setScale(2, ROUND_HALF_UP));

        final BigDecimal newBalance = calculateNewBalance(lastTransaction, amount, WITHDRAW);

        final MoneyTransaction currentTransaction = new MoneyTransaction(new Date(), newBalance, amount, WITHDRAW,
                user);

        moneyTransactionRepository.save(currentTransaction);
        LOG.debug("New transaction flushed.");
    }

    private Sort sortByDate() {
        return new Sort(Sort.Order.by("transactionDate"));
    }
}
