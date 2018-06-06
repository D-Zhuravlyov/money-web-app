package com.money.account.demo.service;

import com.money.account.demo.exception.custom.WithdrawOperationException;
import com.money.account.demo.model.MoneyTransaction;
import com.money.account.demo.model.OperationType;

import java.math.BigDecimal;

import static com.money.account.demo.model.OperationType.ADD;
import static java.math.BigDecimal.ROUND_HALF_UP;

public final class ServiceUtils {

    private ServiceUtils() {
    }

    public static BigDecimal calculateNewBalance(MoneyTransaction lastTransaction, BigDecimal amount,
            OperationType operationType) {
        if (operationType.equals(ADD)) {
            if (lastTransaction != null) {
                final BigDecimal balance = lastTransaction.getRemainedBalance().setScale(2, ROUND_HALF_UP);
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

}
