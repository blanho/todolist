package com.blanho.todolist.service;

import com.blanho.todolist.domain.User;
import com.blanho.todolist.dto.auth.JwtAuthResponse;
import com.blanho.todolist.dto.auth.LoginDTO;
import com.blanho.todolist.dto.auth.RegisterDTO;
import com.blanho.todolist.dto.auth.RegisterResponseDTO;
import org.springframework.stereotype.Service;



public interface AuthService {
    JwtAuthResponse login(LoginDTO loginDto);
    RegisterResponseDTO register(RegisterDTO registerDTO);
}
