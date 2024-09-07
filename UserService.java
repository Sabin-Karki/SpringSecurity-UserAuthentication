package com.example.finance.services;

import com.example.finance.DTO.UserDTO;
import com.example.finance.entity.User;

public interface UserService {
    User registerUser(UserDTO userDTO);
    User findByUsername(String username);
    boolean authenticateUser(String username, String password);
}