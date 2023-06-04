package com.service.auth.controller;

import com.service.auth.model.dto.UserDto;
import com.service.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0.0/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody UserDto user){
        return ResponseEntity.ok(service.login(user));
    }

    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody UserDto user){
        return ResponseEntity.ok(service.save(user));
    }

    @PostMapping("/validate")
    public ResponseEntity<?>validate(@RequestParam String token){
        return ResponseEntity.ok(service.validate(token));
    }
}
