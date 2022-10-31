package com.example.projectemarketg3.repository;

import com.example.projectemarketg3.entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RankingRepository extends JpaRepository<Ranking, Long> {
}