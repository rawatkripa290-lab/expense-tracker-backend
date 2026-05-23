package com.expense.expenseapp.controller;

import com.expense.expenseapp.entity.Expense;
import com.expense.expenseapp.service.ExpenseService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.expense.expenseapp.dto.ExpenseDTO;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.expense.expenseapp.response.ApiResponse;
import org.springframework.security.core.Authentication;
import com.expense.expenseapp.entity.User;
import com.expense.expenseapp.repository.UserRepository;
@RestController
@RequestMapping("/expenses")
public class HomeController {

    private final ExpenseService expenseService;
    private final UserRepository userRepository;
    public HomeController(ExpenseService expenseService ,UserRepository userRepository) {
        this.expenseService = expenseService;
        this.userRepository = userRepository;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<Expense>>
    addExpense(

            @Valid
            @RequestBody Expense expense,

            Authentication authentication) {

        String username =
                authentication.getName();

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        expense.setUser(user);

        Expense savedExpense =
                expenseService.addExpense(expense);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Expense added successfully",
                        savedExpense
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Expense>>>
    getUserExpenses(Authentication authentication) {

        String username =
                authentication.getName();

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        List<Expense> expenses =
                expenseService.getExpensesByUser(user);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Expenses fetched successfully",
                        expenses
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Expense>>
    updateExpense(

            @PathVariable Long id,

            @RequestBody Expense expense,

            Authentication authentication) {

        String username =
                authentication.getName();

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        Expense existingExpense =
                expenseService.getExpenseById(id);

        if(!existingExpense.getUser()
                .getId()
                .equals(user.getId())) {

            throw new RuntimeException(
                    "Unauthorized access"
            );
        }

        Expense updatedExpense =
                expenseService.updateExpense(
                        id,
                        expense
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Expense updated successfully",
                        updatedExpense
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>>
    deleteExpense(
            @PathVariable Long id,Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository
                .findByUsername(username)
                        .orElseThrow(()->
                                new RuntimeException(
                                        "User Not Found"
                                )
                        );
        Expense expense = expenseService
                .getExpenseById(id);
        if(!expense.getUser().getId().equals(user.getId())){
            throw new RuntimeException("RUnauthorized access");
        }

        expenseService.deleteExpense(id);

        return ResponseEntity.ok(new ApiResponse<>(
                true , "Expense deleted successfully",null
                )
                );
    }
    @GetMapping("/secure")
    public String secureApi() {

        return "You are authenticated";
    }
}