package com.blanho.todolist.dto.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @NotEmpty(message = "Password should not be null or empty")
    private String password;

    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

}
