package com.internship.tool.controller;

import com.internship.tool.dto.LoginRequest;
import com.internship.tool.dto.UserResponse;
import com.internship.tool.dto.AuthResponse;
import com.internship.tool.entity.User;
import com.internship.tool.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    //  REGISTER
    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody User user) {
        return service.register(user);
    }

    //  LOGIN
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        return service.login(req.getEmail(), req.getPassword());
    }

    //  GET ALL USERS
    @GetMapping
    public List<UserResponse> getAll() {
        return service.getAllUsers()
                .stream()
                .map(service::map)
                .toList();
    }

    //  GET USER BY ID
    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return service.map(service.getUser(id));
    }

    //  UPDATE USER
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody User user) {
        return service.updateUser(id, user);
    }

    //  DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "User deactivated successfully";
    }

    //  SEARCH USERS
    @GetMapping("/search")
    public List<UserResponse> search(@RequestParam String q) {
        return service.searchUsers(q);
    }
}
