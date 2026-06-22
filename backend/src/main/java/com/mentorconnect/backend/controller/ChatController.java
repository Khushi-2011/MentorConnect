package com.mentorconnect.backend.controller;

import com.mentorconnect.backend.model.Message;
import com.mentorconnect.backend.model.User;
import com.mentorconnect.backend.repository.MessageRepository;
import com.mentorconnect.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/messages")
    public ResponseEntity<?> getChatHistory(@RequestParam Long user1, @RequestParam Long user2) {
        return ResponseEntity.ok(messageRepository.findChatHistory(user1, user2));
    }

    @PostMapping("/messages")
    public ResponseEntity<?> sendMessage(@RequestBody Message message) {
        if (message.getSenderId() == null || message.getReceiverId() == null || message.getContent() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid message format"));
        }
        message.setTimestamp(LocalDateTime.now());
        Message saved = messageRepository.save(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<User>> getRecentChatPartners(@RequestParam Long userId) {
        List<Long> partnerIds = messageRepository.findRecentChatUserIds(userId);
        List<User> partners = new ArrayList<>();
        
        for (Long id : partnerIds) {
            userRepository.findById(id).ifPresent(partners::add);
        }
        
        // If there are no recent chats, return all mentors as suggested contacts
        if (partners.isEmpty()) {
            partners.addAll(userRepository.findByRole("mentor"));
            // Filter out self if the user is a mentor
            partners.removeIf(u -> u.getId().equals(userId));
        }
        
        return ResponseEntity.ok(partners);
    }
}
