package com.auth.jwt.backend.messages;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {
    
    @GetMapping("/messages")
    public ResponseEntity<String> getMessages() {
        return new ResponseEntity<>("Hello, World!", org.springframework.http.HttpStatus.OK);
    }

}
