package com.demo.user_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private Long id;
    private String country;
    private String state;
    private String city;
    private String street;
    private String zipcode;
}
