package com.bridgelabz.bookstoreapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


public @Data class UserRegistrationDTO {

    @Pattern(regexp = "^[A-Z]{1,}[a-zA-z\\s.]{2,}$")
    @NotEmpty(message = "First Name can not be NULL")
    public String firstName;

    @Pattern(regexp = "^[A-Z]{1,}[a-zA-z\\s.]{2,}$")
    @NotEmpty(message = "Last Name can not be NULL")
    public String lastName;

    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email can not be NULL")
    public String email;

    public String address;
    public String password;
}
