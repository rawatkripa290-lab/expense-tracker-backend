package com.expense.expenseapp.controller;

import com.expense.expenseapp.dto.UserDTO;
import com.expense.expenseapp.entity.User;
import com.expense.expenseapp.response.ApiResponse;
import com.expense.expenseapp.service.UserService;
import com.expense.expenseapp.security.JwtService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService,
                          JwtService jwtService) {

        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginUser(
            @RequestBody UserDTO userDTO) {

        User user = userService.loginUser(userDTO);

        String token = jwtService.generateToken(user.getUsername());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Login successful", token)
        );
    }
    @GetMapping("/test")
    public String test() {
        return "WORKING";
    }
}