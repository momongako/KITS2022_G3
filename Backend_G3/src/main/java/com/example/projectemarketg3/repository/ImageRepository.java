package com.example.projectemarketg3.repository;

import com.example.projectemarketg3.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    List<Image> getByUser_IdOrderByCreateAtDesc(Long id);

    List<Image> getByProduct_IdOrderByCreateAtDesc(Long id);

}