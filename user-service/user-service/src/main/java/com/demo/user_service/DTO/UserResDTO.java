package com.demo.user_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResDTO {
    private Long id;
    private String name;
    private String email;
    private String phnNumber;
    private String password;
    private String roles;
    private Address address;
}
