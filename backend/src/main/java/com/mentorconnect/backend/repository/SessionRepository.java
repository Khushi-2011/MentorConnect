package com.mentorconnect.backend.repository;

import com.mentorconnect.backend.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    
    List<Session> findByMentorId(Long mentorId);
    
    List<Session> findByMenteeId(Long menteeId);

    List<Session> findByMentorIdOrMenteeId(Long mentorId, Long menteeId);
}
