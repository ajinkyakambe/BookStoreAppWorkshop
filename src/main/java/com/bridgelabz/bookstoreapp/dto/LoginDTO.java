package com.bridgelabz.bookstoreapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {

    @Email
    public String email;
    @NotEmpty(message = "Password can't be null, Enter Password")
    public String password;

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

}