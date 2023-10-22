package com.example.gamesservice.controller;

import com.example.gamesservice.model.AccountCredentials;
import com.example.gamesservice.model.UserAccount;
import com.example.gamesservice.service.AccountService;
import com.example.gamesservice.util.CredentialsValidator;
import com.example.gamesservice.util.ResponseMessages;
import com.example.gamesservice.util.SessionValidator;
import com.example.gamesservice.util.counter.VisitCounter;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final SessionValidator sessionValidator;
    private final VisitCounter visitCounter;

    public AccountController(AccountService accountService, SessionValidator sessionValidator, VisitCounter visitCounter) {
        this.accountService = accountService;
        this.sessionValidator = sessionValidator;
        this.visitCounter = visitCounter;
    }

    @GetMapping
    public ResponseEntity<Object> getAccount(HttpSession httpSession) {
        return sessionValidator.validate(httpSession, ResponseEntity::ok);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerAccount(HttpSession httpSession, @RequestBody AccountCredentials credentials) {
        String username = credentials.username();
        String password = credentials.password();
        if(username == null || password == null)
            return ResponseEntity.status(400).body(ResponseMessages.INVALID_USERNAME_OR_PASSWORD);

        if(CredentialsValidator.invalidUsername(username) || CredentialsValidator.invalidPassword(password))
            return ResponseEntity.status(400).body(ResponseMessages.INVALID_USERNAME_OR_PASSWORD);

        UserAccount account = accountService.findAccountByName(username);
        if(account != null)
            return ResponseEntity.status(409).body(ResponseMessages.USER_ALREADY_EXISTS);

        account = accountService.createAccount(username, credentials.password(), username.equals("root") ? "admin" : "user");
        httpSession.setAttribute("userId", account.getId());
        visitCounter.increment();
        return ResponseEntity.ok(account);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginAccount(HttpSession httpSession, @RequestBody AccountCredentials credentials) {
        String username = credentials.username();
        String password = credentials.password();
        if(username == null || password == null)
            return ResponseEntity.status(400).body(ResponseMessages.INVALID_USERNAME_OR_PASSWORD);

        if(CredentialsValidator.invalidUsername(username) || CredentialsValidator.invalidPassword(password))
            return ResponseEntity.status(400).body(ResponseMessages.INVALID_USERNAME_OR_PASSWORD);

        UserAccount account = accountService.findAccountByName(username);
        if(account == null)
            return ResponseEntity.status(404).body(ResponseMessages.NOT_FOUND);

        if(!account.getPassword().equals(password))
            return ResponseEntity.status(400).body(ResponseMessages.WRONG_USERNAME_OR_PASSWORD);

        httpSession.setAttribute("userId", account.getId());
        visitCounter.increment();
        return ResponseEntity.ok(account);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpSession httpSession) {
        return sessionValidator.validate(httpSession, userAccount -> {
            httpSession.invalidate();
            return ResponseEntity.ok(ResponseMessages.LOGOUT);
        });
    }



}
