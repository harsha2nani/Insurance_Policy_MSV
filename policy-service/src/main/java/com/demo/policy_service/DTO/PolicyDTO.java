package com.demo.policy_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDTO {

    private Long policyId;
    private String policyType;
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
    private String updatedBy;
}
