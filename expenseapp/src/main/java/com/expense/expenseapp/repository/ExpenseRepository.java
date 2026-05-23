package com.expense.expenseapp.repository;

import com.expense.expenseapp.entity.Expense;
import com.expense.expenseapp.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(User user);
}