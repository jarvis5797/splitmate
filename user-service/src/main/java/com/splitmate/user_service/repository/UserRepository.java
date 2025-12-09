package com.splitmate.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.splitmate.user_service.entity.User;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
}
