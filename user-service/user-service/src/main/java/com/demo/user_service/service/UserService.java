package com.demo.user_service.service;

import com.demo.user_service.DTO.Address;
import com.demo.user_service.DTO.UserResDTO;
import com.demo.user_service.Helper.EmailTemplate;
import com.demo.user_service.Model.UserModel;
import com.demo.user_service.Notification.EmailService;
import com.demo.user_service.Repository.UserRepo;
import com.demo.user_service.constants.AppConstants;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailTemplate emailTemplate;


    public UserResDTO addUser(UserModel userModel) throws MessagingException {
        String pwd = passwordEncoder.encode(userModel.getPassword());
        String roles = "ROLE_USER";

        userModel.setPassword(pwd);
        userModel.setRoles(roles);

        UserModel userModelRes = userRepo.save(userModel);
        if(userModelRes!=null){
            String body = emailTemplate.EmailBody(userModel.getName(), "You have been Successfully Registered TO The Application.\n Lets Login To the Application Using Credentials to opt your best Insurance thats suits for you!!!!");
           emailService.SendTemplateMail(userModelRes.getEmail(),AppConstants.SUBJECT_REGISTRATION,body);
            log.info("Email sent to the Registered User");
        }

        Address addressDTO = Address.builder()
                .id(userModelRes.getAddress().getId())
                .state(userModelRes.getAddress().getState())
                .city(userModelRes.getAddress().getCity())
                .country(userModelRes.getAddress().getCountry())
                .street(userModelRes.getAddress().getStreet())
                .zipcode(userModelRes.getAddress().getZipcode())
                .build();

        return UserResDTO.builder()
                .id(userModelRes.getId())
                .name(userModelRes.getName())
                .email(userModelRes.getEmail())
                .password(userModelRes.getPassword())
                .phnNumber(userModel.getPhnNumber())
                .roles(userModelRes.getRoles())
                .address(addressDTO)
                .build();
    }

}
