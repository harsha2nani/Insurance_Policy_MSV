package com.demo.user_service.Repository;

import com.demo.user_service.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserModel,Long> {

    Optional<UserModel> findByName(String username);
}
