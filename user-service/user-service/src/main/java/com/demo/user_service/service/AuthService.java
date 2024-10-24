package com.demo.user_service.service;

import com.demo.user_service.Model.UserModel;
import com.demo.user_service.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepo userRepo;

    public String generateToken(String name) {
        Optional<UserModel> userRes = userRepo.findByName(name);
        if(userRes.isPresent()){
             return jwtService.generateToken(name,userRes.get().getRoles());
        }else{
            throw new RuntimeException("User Not Found");
        }
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
