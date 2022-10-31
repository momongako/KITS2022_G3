package com.example.projectemarketg3.repository;

import com.example.projectemarketg3.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface StatusRepository extends JpaRepository<Status, Long> {
}