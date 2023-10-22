package com.example.gamesservice.controller;

import com.example.gamesservice.model.UserAccount;
import com.example.gamesservice.service.AccountService;
import com.example.gamesservice.util.ApplicationConstants;
import com.example.gamesservice.util.CredentialsValidator;
import com.example.gamesservice.util.ResponseMessages;
import com.example.gamesservice.util.SessionValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AccountService accountService;
    private final SessionValidator sessionValidator;

    public UserController(AccountService accountService, SessionValidator sessionValidator) {
        this.accountService = accountService;
        this.sessionValidator = sessionValidator;
    }

    @GetMapping
    public ResponseEntity<Object> allUsers(HttpSession httpSession) {
        return sessionValidator.validate(httpSession, userAccount -> ResponseEntity.ok(accountService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(HttpSession httpSession, @PathVariable String id) {
        return sessionValidator.validate(httpSession, userAccount -> {
            UserAccount account = accountService.findAccountById(id);
            if(account == null)
                return ResponseEntity.status(404).body(ResponseMessages.NOT_FOUND);

            return ResponseEntity.ok(account);
        });
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(HttpSession httpSession, @PathVariable String id, @RequestBody UserAccount account) {
        return sessionValidator.validate(httpSession, userAccount -> {
            if(!userAccount.getRole().equals("admin"))
                return ResponseEntity.status(403).body(ResponseMessages.ACCESS_DENIED);

            UserAccount target = accountService.findAccountById(id);
            if(target == null)
                return ResponseEntity.status(404).body(ResponseMessages.NOT_FOUND);

            if(target.getUsername().equals("root"))
                return ResponseEntity.status(403).body(ResponseMessages.ROOT_USER_IS_IMMUTABLE);

            String username = account.getUsername();
            String role = account.getRole();

            if(username != null) {
                if(CredentialsValidator.invalidUsername(username))
                    return ResponseEntity.status(400).body(ResponseMessages.INVALID_USERNAME_OR_PASSWORD);
                UserAccount accountByName = accountService.findAccountByName(username);
                if(accountByName != null)
                    return ResponseEntity.status(409).body(ResponseMessages.USER_ALREADY_EXISTS);
                target.setUsername(username);
            }

            if(role != null) {
                if(!ApplicationConstants.ROLES.contains(role.toLowerCase()))
                    return ResponseEntity.status(400).body(ResponseMessages.ROLE_NOT_FOUND);
                target.setRole(role);
            }

            accountService.updateAccount(target);
            return ResponseEntity.ok(accountService.findAll());
        });
    }

}
