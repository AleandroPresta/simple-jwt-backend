package com.auth.jwt.backend.auth;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.jwt.backend.credentials.CredentialsDto;
import com.auth.jwt.backend.signup.SignUpDto;
import com.auth.jwt.backend.user.UserDto;
import com.auth.jwt.backend.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // Allow cross-origin requests from Angular
public class AuthController {
    
    private final UserService userService;

    @PostMapping("/api/v1/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto user = userService.login(credentialsDto);
        return ResponseEntity.ok(user);
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/api/v1/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto user = userService.register(signUpDto);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }
}
