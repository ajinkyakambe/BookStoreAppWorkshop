package com.bridgelabz.bookstoreapp.model;


import com.bridgelabz.bookstoreapp.dto.UserRegistrationDTO;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "userregistration")

public @Data
class UserRegistrationData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @Column(name = "first_Name")
    private String firstName;

    @Column(name = "last_Name")
    private String lastName;

    @Column(name = "email_id")
    private String email;

    @Column(name = "address")
    private String address;

    @Column
    private String password;

    public UserRegistrationData() {

    }

    public void createUser(UserRegistrationDTO userDTO) {

        this.firstName = userDTO.firstName;
        this.lastName = userDTO.lastName;
        this.address = userDTO.address;
        this.email = userDTO.email;
        this.password = userDTO.password;
    }

    public void updateUser(UserRegistrationDTO userDTO) {
        this.firstName = userDTO.firstName;
        this.lastName = userDTO.lastName;
        this.address = userDTO.address;
        this.email = userDTO.email;
        this.password = userDTO.password;
    }


}