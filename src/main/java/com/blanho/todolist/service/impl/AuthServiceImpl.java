package com.blanho.todolist.service.impl;

import com.blanho.todolist.config.security.JwtTokenProvider;
import com.blanho.todolist.domain.User;
import com.blanho.todolist.dto.auth.JwtAuthResponse;
import com.blanho.todolist.dto.auth.LoginDTO;
import com.blanho.todolist.dto.auth.RegisterDTO;
import com.blanho.todolist.dto.auth.RegisterResponseDTO;
import com.blanho.todolist.enums.Role;
import com.blanho.todolist.exception.ToDoApiException;
import com.blanho.todolist.repository.UserRepository;
import com.blanho.todolist.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private ModelMapper mapper;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            ModelMapper mapper
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mapper = mapper;
    }


    @Override
    public JwtAuthResponse login(LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return JwtAuthResponse.builder().
                accessToken(token)
                .tokenType("Bearer")
                .build();
    }

    @Override
    public RegisterResponseDTO register(RegisterDTO registerDTO) {
      if (userRepository.existsByEmail(registerDTO.getEmail())) {
          throw new ToDoApiException(HttpStatus.BAD_REQUEST, "Email already exists");
      }

      User user = User.builder()
              .name(registerDTO.getName())
              .email(registerDTO.getEmail())
              .password(registerDTO.getPassword())
              .role(Role.USER)
              .build();

      User savedUser = userRepository.save(user);
      return mapToDto(savedUser);
    }

    private RegisterResponseDTO mapToDto(User user) {
        return mapper.map(user, RegisterResponseDTO.class);
    }
}
