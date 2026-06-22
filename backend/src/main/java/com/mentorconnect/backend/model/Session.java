package com.mentorconnect.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mentor_id", nullable = false)
    private User mentor;

    @ManyToOne
    @JoinColumn(name = "mentee_id", nullable = false)
    private User mentee;

    private String type; // "Instant", "Scheduled", "Group"

    private String date; // format: "YYYY-MM-DD" or similar

    private String timeSlot; // e.g. "10:00 AM - 11:00 AM"

    private String status = "Pending"; // "Pending", "Confirmed", "Completed"
}
