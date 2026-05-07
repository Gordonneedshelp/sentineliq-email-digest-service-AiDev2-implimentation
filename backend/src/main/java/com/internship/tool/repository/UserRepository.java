package com.internship.tool.repository;

import com.internship.tool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Get all active users
    List<User> findByActiveTrue();

    // Find user by email (useful for login)
    Optional<User> findByEmail(String email);
    
    //  Search users by name
    List<User> findByFullNameContainingIgnoreCase(String name);
}
