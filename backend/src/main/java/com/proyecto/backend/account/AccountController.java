package com.proyecto.backend.account;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend.account.dto.AccountRegisterRequest;
import com.proyecto.backend.account.dto.AccountRegisterResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<AccountRegisterResponse> register(@Valid @RequestBody AccountRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.register(request));
    }

    @GetMapping
    public List<Account> list() {
        return accountService.list();
    }
}
