package com.example.hanghaespring2.account.repository;

import com.example.hanghaespring2.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
