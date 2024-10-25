package com.demo.policy_service.controller;

import com.demo.policy_service.DTO.PolicyDTO;
import com.demo.policy_service.Service.PolicyService;
import com.demo.policy_service.Util.HeaderUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

@RestController
@RequestMapping("/policy")
public class PolicyController {

    private static final Logger log = LoggerFactory.getLogger(PolicyController.class);

    @Autowired
    private PolicyService policyService;
    @Autowired
    HeaderUtils headerUtils;

    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfo() {
        List<PolicyDTO> res = policyService.getPolicyInfo();
        if (res.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Policy Data is not Present");
        } else {
            log.info("Response is : {}", res);
            return ResponseEntity.status(200).body(res);
        }
    }

    @PostMapping("/addPolicy")
    public ResponseEntity<?> addPolicy(@RequestBody PolicyDTO policyDTO, @RequestHeader HttpHeaders headers) {
        String token = headerUtils.getToken(headers);
        if (token != null) {
            String name = headerUtils.extractUserName(token);
            PolicyDTO dto = policyService.addNewPolicy(policyDTO, name);
            return ResponseEntity.status(200).headers(headers).body(dto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body("The Record Not Saved To DB");
        }
    }

    @GetMapping("/getItemByCategory")
    public ResponseEntity<?> getItem(@RequestParam String type){
        List<PolicyDTO> policyRes = policyService.getPolicyInfoByType(type);

        if(policyRes!=null && !policyRes.isEmpty()) {
            return ResponseEntity.status(200).body(policyRes);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Failed while fetching data with insurance type : "+type);
        }
    }

    @PutMapping("/updatePolicy")
    public ResponseEntity<?> updatePolicy(@RequestParam Long policyId,@RequestBody PolicyDTO policyDTO,
                                          @RequestHeader HttpHeaders httpHeaders){

        String token = headerUtils.getToken(httpHeaders);
        if (token != null) {
            String name = headerUtils.extractUserName(token);
            PolicyDTO res = policyService.updatePolicy(policyId,policyDTO,name);
            return ResponseEntity.status(200).headers(httpHeaders).body(res);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body("The Record Not Saved To DB");
        }

    }

    @DeleteMapping("/deletePolicy")
    public ResponseEntity<?> deletePolicy(@RequestParam Long policyId){
        String status = policyService.deletePolicyById(policyId);
        return ResponseEntity.status(200).body(status);
    }


}

