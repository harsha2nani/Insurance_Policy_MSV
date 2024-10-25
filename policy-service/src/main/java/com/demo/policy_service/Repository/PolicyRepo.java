package com.demo.policy_service.Repository;

import com.demo.policy_service.Model.PolicyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepo extends JpaRepository<PolicyModel,Long> {

    Optional<List<PolicyModel>> findByPolicyType(String type);

    Optional<PolicyModel> findByPolicyId(Long policyId);

    void deleteByPolicyId(Long policyId);
}
