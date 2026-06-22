package com.mentorconnect.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // "mentor" or "mentee"

    private String department;
    
    private String subjects; // e.g. "Data Structures, Operating Systems"
    
    @Column(columnDefinition = "TEXT")
    private String bio;
    
    private Double rating = 5.0;

    private String theme = "Light Mode";

    private Boolean notifications = true;

    private String avatar;
}
