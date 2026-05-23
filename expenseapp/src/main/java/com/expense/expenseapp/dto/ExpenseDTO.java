package com.expense.expenseapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Positive(message = "Amount must be positive")
    private double amount;
}