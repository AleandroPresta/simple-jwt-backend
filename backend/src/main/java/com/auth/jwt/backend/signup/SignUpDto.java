package com.auth.jwt.backend.signup;

public record SignUpDto(String firstName, String lastName, String login, char[] password) {
    
}
