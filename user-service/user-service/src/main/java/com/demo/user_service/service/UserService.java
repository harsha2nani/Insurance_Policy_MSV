package com.demo.user_service.service;

import com.demo.user_service.DTO.Address;
import com.demo.user_service.DTO.UserResDTO;
import com.demo.user_service.Model.UserModel;
import com.demo.user_service.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResDTO addUser(UserModel userModel) {
        String pwd = passwordEncoder.encode(userModel.getPassword());
        String roles = "ROLE_USER";

        userModel.setPassword(pwd);
        userModel.setRoles(roles);

        UserModel userModelRes = userRepo.save(userModel);

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
