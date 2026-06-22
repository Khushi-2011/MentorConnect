package com.mentorconnect.backend.controller;

import com.mentorconnect.backend.model.Session;
import com.mentorconnect.backend.model.User;
import com.mentorconnect.backend.repository.SessionRepository;
import com.mentorconnect.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/book")
    public ResponseEntity<?> bookSession(@RequestBody Map<String, Object> request) {
        try {
            Long mentorId = Long.valueOf(request.get("mentorId").toString());
            Long menteeId = Long.valueOf(request.get("menteeId").toString());
            String type = (String) request.get("type");
            String date = (String) request.get("date");
            String timeSlot = (String) request.get("timeSlot");

            Optional<User> mentorOpt = userRepository.findById(mentorId);
            Optional<User> menteeOpt = userRepository.findById(menteeId);

            if (mentorOpt.isEmpty() || menteeOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Mentor or Mentee not found"));
            }

            Session session = new Session(
                null,
                mentorOpt.get(),
                menteeOpt.get(),
                type == null ? "Scheduled" : type,
                date,
                timeSlot,
                "Pending"
            );

            Session saved = sessionRepository.save(session);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid booking request format"));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Session>> getUserSessions(@PathVariable Long userId) {
        return ResponseEntity.ok(sessionRepository.findByMentorIdOrMenteeId(userId, userId));
    }

    @PutMapping("/{sessionId}/status")
    public ResponseEntity<?> updateSessionStatus(@PathVariable Long sessionId, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        Optional<Session> sessionOpt = sessionRepository.findById(sessionId);
        
        if (sessionOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Session not found"));
        }

        Session session = sessionOpt.get();
        session.setStatus(status);
        Session saved = sessionRepository.save(session);
        return ResponseEntity.ok(saved);
    }
}
