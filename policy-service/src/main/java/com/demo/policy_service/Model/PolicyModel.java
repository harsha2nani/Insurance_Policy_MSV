package com.demo.policy_service.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;
    @NotBlank(message = "policy type should not be blank")
    @Size(min=2,message = "name should be min 2 chars long")
    private String policyType;
    @NotBlank(message = "policy-Name should not be blank")
    @Size(min=2,message = "name should be min 2 chars long")
    private String policyName;
    private double coverageAmount;
    private double basicAmount;
    private double premiumAmount;
    private Integer planTerm;
    private String renewalTerms;
    private List<String> paymentMethods;
    private List<String> paymentFrequency;
    private Date createdDate;
    private Date modifiedDate;
    private String createdBy;
    private String UpdatedBy;
}
