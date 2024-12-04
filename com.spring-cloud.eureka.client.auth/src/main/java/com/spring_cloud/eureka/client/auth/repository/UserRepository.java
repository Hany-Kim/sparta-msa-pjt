package com.spring_cloud.eureka.client.auth.repository;

import com.spring_cloud.eureka.client.auth.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);
}
