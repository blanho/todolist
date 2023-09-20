package com.blanho.todolist.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
