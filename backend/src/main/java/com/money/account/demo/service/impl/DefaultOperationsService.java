package com.money.account.demo.service.impl;

import com.money.account.demo.exception.custom.UserNotFoundException;
import com.money.account.demo.exception.custom.WithdrawOperationException;
import com.money.account.demo.model.MoneyTransaction;
import com.money.account.demo.model.OperationType;
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

@Service
public class DefaultOperationsService implements OperationsService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserRepository userRepository;

    @Resource
    private MoneyTransactionRepository moneyTransactionRepository;

    @Transactional
    public void performMoneyOperationByUserId(final String userId, final BigDecimal amount, OperationType operationType)
            throws UserNotFoundException {
        final User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new UserNotFoundException("Unknown id: " + userId));

        LOG.debug("Found user: " + userId + ";  " + user.getName());

        final MoneyTransaction lastTransaction = moneyTransactionRepository.findAll(sortByDate()).stream()
                .findFirst().orElse(null);

        LOG.debug("Found lastTransaction: " + lastTransaction + "; Balance: " + lastTransaction
                .getRemainedBalance());

        final BigDecimal newBalance = calculateNewBalance(lastTransaction, amount, operationType);

        final MoneyTransaction currentTransaction = new MoneyTransaction(new Date(), newBalance, amount, operationType,
                user);

        moneyTransactionRepository.saveAndFlush(currentTransaction);
        LOG.debug("New transaction flushed.");
    }

    private BigDecimal calculateNewBalance(MoneyTransaction lastTransaction, BigDecimal amount,
            OperationType operationType) {
        if (operationType.equals(ADD)) {
            if (lastTransaction != null) {
                final BigDecimal balance = lastTransaction.getRemainedBalance();
                return balance.add(amount);
            }
            return amount;
        } else if (lastTransaction != null) {
            if (lastTransaction.getRemainedBalance().compareTo(amount) < 0) {
                throw new WithdrawOperationException(
                        "Remained balance is less then withdraw operation requires. Unable to withdraw.");
            }
            return lastTransaction.getRemainedBalance().subtract(amount);

        }
        throw new WithdrawOperationException("No transactions were made for this account yet. Unable to withdraw.");
    }

    private Sort sortByDate() {
        return new Sort(Sort.Order.by("transactionDate"));
    }
}
