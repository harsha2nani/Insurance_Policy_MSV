package com.demo.policy_service.Service;

import com.demo.policy_service.DTO.PolicyDTO;
import com.demo.policy_service.Model.PolicyModel;
import com.demo.policy_service.Repository.PolicyRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepo policyRepo;
    @Autowired
    private ModelMapper mapper;

    public PolicyDTO addNewPolicy(PolicyDTO policyDTO,String name) {
        policyDTO.setCreatedBy(name);
        policyDTO.setUpdatedBy(name);
        policyDTO.setCreatedDate(new Date());
        policyDTO.setModifiedDate(new Date());
        PolicyModel policyModel =  mapper.map(policyDTO, PolicyModel.class);
        PolicyModel policyModelRes = policyRepo.save(policyModel);
        return mapper.map(policyModelRes, PolicyDTO.class);
    }

    public List<PolicyDTO> getPolicyInfo() {
        List<PolicyModel> policyList = policyRepo.findAll();
        return policyList.stream().map(policy -> mapper.map(policy, PolicyDTO.class)).toList();
    }

    public List<PolicyDTO> getPolicyInfoByType(String type) {
       Optional<List<PolicyModel>> res = policyRepo.findByPolicyType(type);
       if(res.isPresent()){
          List<PolicyModel> modelList = res.get();
           return modelList.stream().map(policy -> mapper.map(policy, PolicyDTO.class)).toList();
       }else {
           return null;
       }
    }

    public PolicyDTO updatePolicy(Long policyId, PolicyDTO policyDTO, String name) {
      PolicyModel actualPolicyData =  policyRepo.findByPolicyId(policyId).orElseThrow(()-> new RuntimeException("Record not found"));
      PolicyModel updatedData = mapper.map(actualPolicyData,PolicyModel.class);
        // the fields that needs to be updated while making any changes to the Record
        updatedData.setModifiedDate(new Date());
        updatedData.setUpdatedBy(name);
        PolicyModel res = policyRepo.save(updatedData);
        return mapper.map(res, PolicyDTO.class);
    }

    public String deletePolicyById(Long policyId) {
       PolicyModel res = policyRepo.findByPolicyId(policyId).orElseThrow(()-> new
               RuntimeException("Exception occured while fetching policy info from policy id : "+policyId));
       policyRepo.deleteByPolicyId(policyId);
       return "Record deleted Successfully";
    }
}
