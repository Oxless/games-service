package com.example.gamesservice.util;

public class ResponseMessages {

    public static final String NOT_FOUND = "{\"message\": \"not_found\"}";

    public static final String WRONG_USERNAME_OR_PASSWORD = "{\"message\": \"wrong_username_or_password\"}";
    public static final String INVALID_USERNAME_OR_PASSWORD = "{\"message\": \"invalid_username_or_password\"}";
    public static final String USER_ALREADY_EXISTS = "{\"message\": \"user_already_exists\"}";

    public static final String ACCESS_DENIED = "{\"message\": \"access_denied\"}";
    public static final String ROOT_USER_IS_IMMUTABLE = "{\"message\": \"root_user_is_immutable\"}";
    public static final String ROLE_NOT_FOUND = "{\"message\": \"role_not_found\"}";
    public static final String UNAUTHORIZED = "{\"message\": \"unauthorized\"}";
    public static final String LOGOUT = "{\"message\": \"successfully_logout\"}";
}
