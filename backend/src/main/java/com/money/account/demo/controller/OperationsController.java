package com.money.account.demo.controller;

import com.money.account.demo.service.OperationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static com.money.account.demo.model.OperationType.ADD;
import static com.money.account.demo.model.OperationType.WITHDRAW;

@RestController
@RequestMapping("/api/operations")
public class OperationsController {

    @Resource
    private OperationsService operationsService;

    @PostMapping("/withdraw")
    public ResponseEntity withdrawMoneyFromUser(@RequestParam String userId, @RequestParam BigDecimal amount)
            throws Throwable {
        operationsService.withdrawMoneyFromUserByUserId(userId, amount);
        return ResponseEntity.ok().body("Operation succeeded");
    }

    @PostMapping("/add")
    public ResponseEntity addMoneyToUser(@RequestParam String userId, @RequestParam BigDecimal amount) throws Throwable {
        operationsService.addMoneyToUserByUserId(userId, amount);
        return ResponseEntity.ok().body("Operation succeeded");
    }

}
