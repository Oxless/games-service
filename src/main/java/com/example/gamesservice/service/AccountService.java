package com.example.gamesservice.service;

import com.example.gamesservice.model.UserAccount;
import com.example.gamesservice.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<UserAccount> findAll() {
        return accountRepository.findAll();
    }

    public UserAccount createAccount(String username, String password, String role) {
        UserAccount account = new UserAccount(username.toLowerCase(), password, role);
        account = accountRepository.save(account);
        return account;
    }

    public UserAccount findAccountById(String id) {
        return accountRepository.findById(id).orElse(null);
    }

    public UserAccount findAccountByName(String name) {
        return accountRepository.findByName(name.toLowerCase());
    }

    public UserAccount updateAccount(UserAccount userAccount) {
        return accountRepository.save(userAccount);
    }

}
