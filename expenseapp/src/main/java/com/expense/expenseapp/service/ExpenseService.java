package com.expense.expenseapp.service;

import com.expense.expenseapp.entity.Expense;
import com.expense.expenseapp.entity.User;
import com.expense.expenseapp.repository.ExpenseRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(
            ExpenseRepository expenseRepository) {

        this.expenseRepository = expenseRepository;
    }

    // ADD EXPENSE
    public Expense addExpense(
            Expense expense) {

        return expenseRepository.save(expense);
    }

    // GET ALL EXPENSES
    public List<Expense> getAllExpenses() {

        return expenseRepository.findAll();
    }

    // GET EXPENSE BY ID
    public Expense getExpenseById(
            Long id) {

        return expenseRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Expense not found"
                        )
                );
    }

    // GET USER-SPECIFIC EXPENSES
    public List<Expense> getExpensesByUser(
            User user) {

        return expenseRepository.findByUser(user);
    }

    // UPDATE EXPENSE
    public Expense updateExpense(
            Long id,
            Expense updatedExpense) {

        Expense existingExpense =
                expenseRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Expense not found"
                                )
                        );

        existingExpense.setTitle(
                updatedExpense.getTitle()
        );

        existingExpense.setAmount(
                updatedExpense.getAmount()
        );

        return expenseRepository.save(
                existingExpense
        );
    }

    // DELETE EXPENSE
    public void deleteExpense(
            Long id) {

        expenseRepository.deleteById(id);
    }
}