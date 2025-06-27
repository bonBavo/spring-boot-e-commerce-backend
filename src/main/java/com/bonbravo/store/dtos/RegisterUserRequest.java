package com.bonbravo.store.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class RegisterUserRequest {

    @NotBlank(message = "name is required")
    @Size(max = 254, message = "Name must not be less the")
    private String name;
    
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(message = "Password must be at between 6 and 26 characters long", min = 6, max = 26)
    private String password;

}