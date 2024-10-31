package com.alexcasey.quizzly.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexcasey.quizzly.dto.UserDto;
import com.alexcasey.quizzly.exception.UserNotFoundException;
import com.alexcasey.quizzly.mapper.UserMapper;
import com.alexcasey.quizzly.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;
    
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userRepository.findById(id).map(UserMapper::toDto).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toDto).toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
