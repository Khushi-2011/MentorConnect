package com.mentorconnect.backend.controller;

import com.mentorconnect.backend.model.User;
import com.mentorconnect.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }
        return ResponseEntity.ok(userOpt.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long id, @RequestBody User profileData) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        User user = userOpt.get();
        if (profileData.getName() != null) user.setName(profileData.getName());
        if (profileData.getEmail() != null) user.setEmail(profileData.getEmail());
        if (profileData.getDepartment() != null) user.setDepartment(profileData.getDepartment());
        if (profileData.getSubjects() != null) user.setSubjects(profileData.getSubjects());
        if (profileData.getBio() != null) user.setBio(profileData.getBio());
        if (profileData.getTheme() != null) user.setTheme(profileData.getTheme());
        if (profileData.getNotifications() != null) user.setNotifications(profileData.getNotifications());
        if (profileData.getAvatar() != null) user.setAvatar(profileData.getAvatar());

        User updated = userRepository.save(user);
        return ResponseEntity.ok(updated);
    }
}
