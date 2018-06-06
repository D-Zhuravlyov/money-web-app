package com.money.account.demo.controller;

import com.money.account.demo.model.MoneyTransaction;
import com.money.account.demo.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}")
public class AccountController {

    @Resource
    private AccountService accountService;

    @GetMapping("/history")
    public List<MoneyTransaction> showHistory(@PathVariable long userId) throws Throwable {
        return accountService.getTransactionsHistoryByUserId(userId);
    }

}
