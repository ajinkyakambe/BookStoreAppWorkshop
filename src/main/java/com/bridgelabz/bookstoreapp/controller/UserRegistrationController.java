package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.LoginDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.dto.UserRegistrationDTO;
import com.bridgelabz.bookstoreapp.model.UserRegistrationData;
import com.bridgelabz.bookstoreapp.services.IUserRegistrationService;
import com.bridgelabz.bookstoreapp.util.TokenUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User Registration Controller API
 */


@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/api/userservice")
@Slf4j
public class UserRegistrationController {

    /**
     * Autowired IUserService to inject its dependency here
     */

    @Autowired
    IUserRegistrationService userRegistrationService;

    /**
     * Autowired TokenUtil to inject its dependency here
     */

    @Autowired
    TokenUtil tokenUtil;

    /**
     * Ability to call api to retrieve all user records
     */
    @GetMapping(value = {"", "/", "/get"})
    public ResponseEntity<ResponseDTO> getUserData() {
        List<UserRegistrationData> usersList = userRegistrationService.getUserDetails();
        ResponseDTO response = new ResponseDTO("Get call success", usersList);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    /**
     * Ability to call api to retrieve single user records
     */
    @GetMapping("/get/{userId}")
    public ResponseEntity<ResponseDTO> getUserDataById(@PathVariable("userId") int userId) {
        UserRegistrationData userDetails = userRegistrationService.getUserById(userId);
        ResponseDTO response = new ResponseDTO("Get call success for id", userDetails);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

    }

    /**
     * Ability to call api to register user record
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUserRegistrationData(@Valid @RequestBody UserRegistrationDTO userDTO) {
        String userDetails = userRegistrationService.userRegistration(userDTO);
        log.debug("User Registration input details: " + userDTO.toString());
        ResponseDTO response = new ResponseDTO("Successfully Registered the user", userDetails);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    /**
     * Ability to call api to retrieve All user records by token
     */
    @GetMapping(value = "/getAll/{token}")
    public ResponseEntity<ResponseDTO> getAllUserDataByToken(@PathVariable String token)
    {
        List<UserRegistrationData> listOfUserByToken = userRegistrationService.getAllUserDataByToken(token);
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully:",listOfUserByToken);
        return new ResponseEntity(dto,HttpStatus.OK);
    }

    /**
     * Ability to call api to verify token
     */
    @GetMapping("/verify/{token}")
    ResponseEntity<ResponseDTO> verifyUser(@PathVariable String token) {
        String userVerification = userRegistrationService.verifyUser(token);
        if (userVerification != null) {
            ResponseDTO responseDTO = new ResponseDTO("User verified :", userVerification);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            ResponseDTO responseDTO = new ResponseDTO("User Not verified data:", userVerification);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
    }

    /**
     * Ability to call api to login user
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> userLogin(@RequestBody LoginDTO userLoginDTO) {
        return new ResponseEntity<ResponseDTO>(userRegistrationService.loginUser(userLoginDTO), HttpStatus.OK);
    }

    /**
     * Ability to call api to update User By id
     */
    @PutMapping("/update/{userId}")
    public ResponseEntity<ResponseDTO> updateContactData(@PathVariable("userId") int userId,
                                                         @Valid @RequestBody UserRegistrationDTO userDTO) {
        UserRegistrationData updateUser = userRegistrationService.updateUserRegistrationData(userId, userDTO);
        log.debug("User Registration Data After Update " + updateUser.toString());
        ResponseDTO response = new ResponseDTO("Updated contact data for" + userId, updateUser);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    /**
     * Ability to call api to Delete User By id
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable("userId") int userId) {
        userRegistrationService.deleteUser(userId);
        ResponseDTO response = new ResponseDTO("Delete call success for id ", "deleted id:" + userId);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

    }

    /**
     * Ability to call api to get token if forgot password
     */
    @GetMapping("/getToken/{email}")
    public ResponseEntity<ResponseDTO> getToken(@PathVariable String email){
        String generatedToken=userRegistrationService.getToken(email);
        ResponseDTO responseDTO=new ResponseDTO("Token for mail id sent on mail successfully",generatedToken);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }
}