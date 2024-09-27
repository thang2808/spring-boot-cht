package com.digidinos.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Import entity Account
import com.digidinos.shopping.entity.Account;
import com.digidinos.shopping.serviceWithRepo.AccountService;

@RestController
public class UserController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/api/users")
    public ResponseEntity<List<Account>> getUsers() {
        List<Account> users = accountService.findAllUsers(); // Phương thức cần được định nghĩa trong AccountService
        return ResponseEntity.ok(users);
    }
}

