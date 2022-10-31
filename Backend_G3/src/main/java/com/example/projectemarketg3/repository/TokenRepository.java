package com.example.projectemarketg3.repository;

import com.example.projectemarketg3.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE Token c SET c.confirmedAt = ?2 WHERE c.token = ?1")
    void updateConfirmedAt(String token, LocalDateTime confirmedAt);
}