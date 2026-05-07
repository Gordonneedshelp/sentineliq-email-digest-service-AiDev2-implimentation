package com.internship.tool.service;

import com.internship.tool.entity.User;
import com.internship.tool.dto.*;
import com.internship.tool.repository.UserRepository;
import com.internship.tool.security.JwtUtil;
import com.internship.tool.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    //  REGISTER
    public UserResponse register(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setActive(true);
        user.setPassword(encoder.encode(user.getPassword()));

        return map(repo.save(user));
    }

    //  LOGIN
    public AuthResponse login(String email, String password) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return new AuthResponse(jwtUtil.generateToken(email));
    }

    //  GET ALL USERS
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    //  GET USER BY ID
    public User getUser(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    //  UPDATE USER
    public UserResponse updateUser(Long id, User updatedUser) {

        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        user.setUpdatedAt(LocalDateTime.now());

        return map(repo.save(user));
    }

    //  SOFT DELETE USER
    public void deleteUser(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setActive(false);
        user.setUpdatedAt(LocalDateTime.now());

        repo.save(user);
    }

    //  SEARCH USERS
    public List<UserResponse> searchUsers(String name) {
        return repo.findByFullNameContainingIgnoreCase(name)
                .stream()
                .map(this::map)
                .toList();
    }

    //  MAPPER
    public UserResponse map(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .email(u.getEmail())
                .fullName(u.getFullName())
                .role(u.getRole())
                .active(u.isActive())
                .createdAt(u.getCreatedAt())
                .updatedAt(u.getUpdatedAt())
                .build();
    }
}
