package com.example.gamesservice.util;

import com.example.gamesservice.model.UserAccount;
import com.example.gamesservice.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SessionValidator {

    private final AccountService accountService;

    public SessionValidator(AccountService accountService) {
        this.accountService = accountService;
    }

    public ResponseEntity<Object> validate(HttpSession httpSession, Function<UserAccount, ResponseEntity<Object>> resultFunction) {
        String userId = (String) httpSession.getAttribute("userId");
        if(userId == null)
            return ResponseEntity.status(401).body(ResponseMessages.UNAUTHORIZED);

        UserAccount account = accountService.findAccountById(userId);
        if(account == null)
            return ResponseEntity.status(401).body(ResponseMessages.UNAUTHORIZED);

        return resultFunction.apply(account);
    }

}
