package com.blanho.todolist.controller;

import com.blanho.todolist.domain.User;
import com.blanho.todolist.dto.auth.JwtAuthResponse;
import com.blanho.todolist.dto.auth.LoginDTO;
import com.blanho.todolist.dto.auth.RegisterDTO;
import com.blanho.todolist.dto.auth.RegisterResponseDTO;
import com.blanho.todolist.service.AuthService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(
           @Valid @RequestBody RegisterDTO registerDTO
    ) {
        return new ResponseEntity<>(authService.register(registerDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(
            @RequestBody LoginDTO loginDTO
    ) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }


}
