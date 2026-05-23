package com.expense.expenseapp.service;

import com.expense.expenseapp.dto.UserDTO;
import com.expense.expenseapp.entity.User;
import com.expense.expenseapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserDTO userDTO) {

        User user = new User();

        user.setUsername(userDTO.getUsername());

        user.setPassword(
                passwordEncoder.encode(userDTO.getPassword())
        );

        return userRepository.save(user);
    }
    public User loginUser(UserDTO userDTO) {

        Optional<User> optionalUser =
                userRepository.findByUsername(
                        userDTO.getUsername()
                );

        if(optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        boolean isPasswordCorrect =
                passwordEncoder.matches(
                        userDTO.getPassword(),
                        user.getPassword()
                );

        if(!isPasswordCorrect) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}