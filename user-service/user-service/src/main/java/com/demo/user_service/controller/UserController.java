package com.demo.user_service.controller;

import com.demo.user_service.DTO.UserResDTO;
import com.demo.user_service.Model.LoginModel;
import com.demo.user_service.Model.UserModel;
import com.demo.user_service.service.AuthService;
import com.demo.user_service.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> RegisterUser(@RequestBody @Valid UserModel userModel) throws MessagingException {
        UserResDTO userResDTO = userService.addUser(userModel);
        if (userResDTO != null) {

            return ResponseEntity.status(200).body(userResDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration Unsuccessfull");
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginModel loginModel) {

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getName(), loginModel.getPassword()));
        if (auth.isAuthenticated()) {
            return authService.generateToken(loginModel.getName());
        } else {
            throw new RuntimeException("Invalid User");
        }
    }

    @PostMapping("/validate")
    public String Validate(@RequestParam(name = "token") String token) {
        authService.validateToken(token);
        return "Valid Token";
    }
}
