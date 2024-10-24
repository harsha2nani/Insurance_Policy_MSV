package com.demo.user_service.service;

import com.demo.user_service.Model.UserModel;
import com.demo.user_service.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> userModel = userRepo.findByName(username);
        return userModel.map(CustomUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("User Not Found with this name : "+username));
    }
}
