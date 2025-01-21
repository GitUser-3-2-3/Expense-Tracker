package com.sc.expenseservice.controller;

import com.sc.expenseservice.dto.ExpenseDTO;
import com.sc.expenseservice.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/createUpdate")
    public ResponseEntity<ExpenseDTO> createOrUpdateExpense(@RequestBody ExpenseDTO expenseDTO) {
        try {
            ExpenseDTO expenseResponse = expenseService.createOrUpdateExpense(expenseDTO);
            return ResponseEntity.ok(expenseResponse);
        }
        catch (Exception rtE) {
            log.error("EXCEPTION IN createUpdateUser:: {}", rtE.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByUserId(@PathVariable String userId) {
        try {
            List<ExpenseDTO> userExpenses = expenseService.getExpenseByUserId(userId);
            return ResponseEntity.ok(userExpenses);
        }
        catch (Exception rtE) {
            log.error("EXCEPTION IN getUserById:: {}", rtE.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}