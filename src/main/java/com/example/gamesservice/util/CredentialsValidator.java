package com.example.gamesservice.util;

public class CredentialsValidator {

    public static boolean invalidUsername(String username) {
        return username.length() > 30 || username.length() < 3 || !username.matches("^[A-Za-z0-9][A-Za-z0-9_]+$");
    }

    public static boolean invalidPassword(String password) {
        return password.length() > 30 || password.length() < 8 ||
                !password.matches("^[\u0410-\u042f\u0430-\u044f\u0401\u0451A-Za-z0-9_&-]+$");
    }

}
