package com.bridgelabz.bookstoreapp.services;

import com.bridgelabz.bookstoreapp.dto.LoginDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.dto.UserRegistrationDTO;
import com.bridgelabz.bookstoreapp.model.UserRegistrationData;

import java.util.List;

public interface IUserRegistrationService {

    List<UserRegistrationData> getUserDetails();

    UserRegistrationData getUserById(int userId);

    String userRegistration(UserRegistrationDTO userDTO);

    UserRegistrationData updateUserRegistrationData(int userId, UserRegistrationDTO userDTO);

    void deleteUser(int userId);

    List<UserRegistrationData> getAllUserDataByToken(String token);

    String verifyUser(String token);

    ResponseDTO loginUser(LoginDTO loginDto);

    String getToken(String email);
}
