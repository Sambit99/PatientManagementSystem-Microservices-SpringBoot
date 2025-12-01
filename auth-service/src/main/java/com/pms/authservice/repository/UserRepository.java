package com.pms.authservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pms.authservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
