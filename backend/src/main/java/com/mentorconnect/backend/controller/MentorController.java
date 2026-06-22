package com.mentorconnect.backend.controller;

import com.mentorconnect.backend.model.User;
import com.mentorconnect.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void seedMentors() {
        if (userRepository.findByRole("mentor").isEmpty()) {
            User m1 = new User(
                null,
                "Dr. Alex Johnson",
                "alex@mentorconnect.com",
                "password",
                "mentor",
                "Computer Science",
                "React, Node.js, Java, Spring Boot",
                "Full stack developer with 10+ years of experience. Specialized in React and Node.js.",
                4.9,
                "Light Mode",
                true,
                "mentor.png"
            );
            
            User m2 = new User(
                null,
                "Lisa Prdes",
                "lisa@mentorconnect.com",
                "password",
                "mentor",
                "Business Administration",
                "Product Management, Agile, Leadership",
                "Senior product manager with experience of 5 years in industry.",
                4.8,
                "Light Mode",
                true,
                "mentor.png"
            );

            User m3 = new User(
                null,
                "Prof. Sarah Jenkins",
                "sarah@mentorconnect.com",
                "password",
                "mentor",
                "Information Technology",
                "Databases, SQL, Cloud Computing",
                "Associate professor and cloud architect. Expert in database design.",
                5.0,
                "Light Mode",
                true,
                "mentor.png"
            );

            userRepository.save(m1);
            userRepository.save(m2);
            userRepository.save(m3);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getMentors(@RequestParam(required = false) String query) {
        if (query != null && !query.trim().isEmpty()) {
            return ResponseEntity.ok(userRepository.searchMentors(query));
        }
        return ResponseEntity.ok(userRepository.findByRole("mentor"));
    }
}
