package com.bridgelabz.bookstoreapp.services;

import com.bridgelabz.bookstoreapp.dto.LoginDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.dto.UserRegistrationDTO;
import com.bridgelabz.bookstoreapp.exception.UserRegistrationException;
import com.bridgelabz.bookstoreapp.model.UserRegistrationData;
import com.bridgelabz.bookstoreapp.repository.UserRegistrationRepository;
import com.bridgelabz.bookstoreapp.util.EmailSMTP;
import com.bridgelabz.bookstoreapp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service

public class UserRegistrationService implements IUserRegistrationService{
    //Global var
    String token = null;

    /**
     * Autowired TokenUtil and Email SMTP to inject its dependency here
     */
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    EmailSMTP mailService;

    /**
     * Autowired UserRegistrationRepository Interface to inject its dependency here
     */
    @Autowired
    UserRegistrationRepository userRepo;

    @Override
    public List<UserRegistrationData> getUserDetails() {
        return userRepo.findAll();
    }

    @Override
    public UserRegistrationData getUserById(int userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserRegistrationException("User  with id " + userId + " does not exist in database..!"));
    }

    @Override
    public String userRegistration(UserRegistrationDTO userDTO) {
        Optional<UserRegistrationData> userCheck = userRepo.findByEmailId(userDTO.getEmail());
        if (userCheck.isPresent()) {
            log.error("Email already exists");
            throw new UserRegistrationException("Email already exists");
        } else {
            UserRegistrationData userData = new UserRegistrationData();
            userData.createUser(userDTO);
            userRepo.save(userData);
            token = tokenUtil.createToken(userData.getUserId());
            mailService.sendEmail(userData .getEmail(), "Test Email", "Registered SuccessFully, hii: "
                    + userData.getFirstName() + "Please Click here to get data-> "
                    + mailService.getLink(token));
            return token;
        }
    }

    @Override
    public UserRegistrationData updateUserRegistrationData(int userId, UserRegistrationDTO userDTO) {
        UserRegistrationData userData = this.getUserById(userId);
        userData.updateUser(userDTO);
        return userRepo.save(userData);
    }

    @Override
    public void deleteUser(int userId) {
        UserRegistrationData userData = this.getUserById(userId);
        userRepo.delete(userData);

    }

    @Override
    public List<UserRegistrationData> getAllUserDataByToken(String token) {
        int id = Math.toIntExact(tokenUtil.decodeToken(token));
        Optional<UserRegistrationData> isContactPresent = userRepo.findById(id);
        if (isContactPresent.isPresent()) {
            List<UserRegistrationData> listOfUsers = userRepo.findAll();
            mailService.sendEmail("ajinkyakambe@gmail.com", "Test Email", "Get your data with this token, hii!!!: "
                    +isContactPresent.get().getEmail()+"Please Click here to get data-> "
                    +mailService.getLink(token));
            return listOfUsers;
        } else {
            System.out.println("Exception, Token not found!");
            return null;
        }
    }

    @Override
    public String verifyUser(String token) {
        int id = Math.toIntExact(tokenUtil.decodeToken(token));
        Optional<UserRegistrationData> user = userRepo.findById(id);

        if (user.isPresent()) {
            return user.toString();
        } else
            return null;
    }

    @Override
    public ResponseDTO loginUser(LoginDTO loginDto) {
        ResponseDTO dto = new ResponseDTO();
        Optional<UserRegistrationData> login = userRepo.findByEmailId(loginDto.getEmail());
        if (login.isPresent()) {
            String pwd = login.get().getPassword();
            if (pwd.equals(loginDto.getPassword())) {
                dto.setMessage("login successful ");
                dto.setData(login.get());
                mailService.sendEmail("ajinkyakambe@gmail.com", "Login Success", "LoggedIn User Details,\nUserEmail- "
                        + userRepo.findByEmailId(loginDto.getEmail()));
                return dto;
            } else {
                dto.setMessage("Sorry! login is unsuccessful");
                dto.setData("Wrong password");
                return dto;
            }
        }
        return new ResponseDTO("User not found!", "Wrong email");
    }

    @Override
    public String getToken(String email) {
        Optional<UserRegistrationData> userRegistration=userRepo.findByEmailId(email);
        String token=tokenUtil.createToken(userRegistration.get().getUserId());
        mailService.sendEmail("ajinkyakambe@gmail.com","Welcome User:  "+userRegistration.get().getFirstName(),"Token for changing password is :"+token);
        return token;
    }


}
