package com.demo.user_service.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "country is mandatory")
    private String country;

    @NotBlank(message = "state is mandatory")
    private String state;

    @NotBlank(message = "city is mandatory")
    private String city;

    @NotBlank(message = "street is mandatory")
    private String street;

    @NotBlank(message = "zipcode is mandatory")
    private String zipcode;
}
