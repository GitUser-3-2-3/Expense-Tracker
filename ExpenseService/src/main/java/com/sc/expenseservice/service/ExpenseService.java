package com.sc.expenseservice.service;

import com.sc.expenseservice.dto.ExpenseDTO;
import com.sc.expenseservice.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Transactional
    public ExpenseDTO createOrUpdateExpense(ExpenseDTO expenseDTO) {
        UnaryOperator<ExpenseDTO> updateExpense = request -> {
            updateExpenses(request, expenseDTO);
            return expenseRepository.save(request);
        };
        Supplier<ExpenseDTO> createExpense = () -> expenseRepository.save(expenseDTO);

        return expenseRepository
            .findByUserIdAndExternalId(expenseDTO.getUserId(), expenseDTO.getExternalId())
            .map(updateExpense).orElseGet(createExpense);
    }

    private void updateExpenses(ExpenseDTO oldExpense, ExpenseDTO updatedExpense) {
        oldExpense.setId(updatedExpense.getId());
        oldExpense.setExternalId(updatedExpense.getExternalId());
        oldExpense.setUserId(updatedExpense.getUserId());
        oldExpense.setAmount(updatedExpense.getAmount());
        oldExpense.setMerchant(updatedExpense.getMerchant());
        oldExpense.setCreatedAt(updatedExpense.getCreatedAt());
    }

    public List<ExpenseDTO> getExpenseByUserId(String userId) {
        return expenseRepository.findByUserId(userId);
    }
}