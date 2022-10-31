package com.example.projectemarketg3.controller;

import com.example.projectemarketg3.dto.RatingDto;
import com.example.projectemarketg3.entity.Product;
import com.example.projectemarketg3.entity.Rating;
import com.example.projectemarketg3.entity.User;
import com.example.projectemarketg3.exception.NotFoundException;
import com.example.projectemarketg3.repository.ProductRepository;
import com.example.projectemarketg3.repository.RatingRepository;
import com.example.projectemarketg3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;

@RestController
@RequestMapping("/api/v1/rating")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    //    LAY RA DANH GIA CUA SAN PHAM THEO ID VA DA DUOC ADMIN PUBLIC
    @GetMapping("/product/{id}")
    public List<Rating> getAllRatingProduct(@PathVariable Long id) {
        List<Rating> ratings = ratingRepository.findByProduct_IdOrderByCreateAtDesc(id);
        List<Rating> ratingList = new ArrayList<>();
        for (Rating r : ratings
        ) {
            if (r.getChecking()) {
                ratingList.add(r);
            }
        }
        return ratingList;
    }

    // get all rating rest api
    @GetMapping
    public List<Rating> getAllRating(){
        return ratingRepository.findAll();
//        return ratingRepository.getByOrderByCreateAtDesc();
    }



    // get rating by ID rest api
    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable Long id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("rating not exist with id :" + id));
        return ResponseEntity.ok(rating);
    }


//    Get product star
    @GetMapping("/{star}/star")
    public List<Rating> getAllByStar(@PathVariable Integer star){
        return ratingRepository.getByStarOrderByCreateAtDesc(star);
    }

    //    Get AVG star of product
    @GetMapping("/avg/{nameProduct}")
    public String getAVGStarOfProduct(@PathVariable String nameProduct){
        return ratingRepository.getAVGStarOfProduct(nameProduct);
    }

    //    Get Count star of products
    @GetMapping("/count/{nameProduct}")
    public String getCountStarOfProduct(@PathVariable String nameProduct){
        return ratingRepository.getCountStarOfProduct(nameProduct);
    }


    @GetMapping("/userrating/{nameProduct}")
    public List<Rating> getUserRating(@PathVariable String nameProduct){
        return ratingRepository.getUserRating(nameProduct);
    }
}
