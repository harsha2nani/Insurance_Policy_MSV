package com.demo.policy_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/policy")
public class PolicyController {

    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfo(){
        return ResponseEntity.status(200).body("Policy Details Returned Successfully....");
    }
}
