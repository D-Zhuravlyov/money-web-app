package com.money.account.demo.controller;

import com.money.account.demo.exception.custom.UserNotFoundException;
import com.money.account.demo.exception.custom.WithdrawOperationException;
import com.money.account.demo.model.MoneyTransaction;
import com.money.account.demo.service.OperationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/operations")
public class OperationsController {

    @Resource
    private OperationsService operationsService;

    @PostMapping("/withdraw")
    @ResponseStatus(value = HttpStatus.OK, reason = "Operation succeeded")
    public MoneyTransaction withdrawMoneyFromUser(@RequestParam String userId, @RequestParam BigDecimal amount)
            throws UserNotFoundException, WithdrawOperationException {
        return operationsService.withdrawMoneyFromUserByUserId(userId, amount);
    }

    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.OK, reason = "Operation succeeded")
    public MoneyTransaction addMoneyToUser(@RequestParam String userId, @RequestParam BigDecimal amount) throws UserNotFoundException {
       return operationsService.addMoneyToUserByUserId(userId, amount);
    }

}
