package com.example.projectemarketg3.controller;

import com.example.projectemarketg3.entity.Ranking;
import com.example.projectemarketg3.entity.User;
import com.example.projectemarketg3.repository.RankingRepository;
import com.example.projectemarketg3.repository.UserRepository;
import com.example.projectemarketg3.request.UserRequest;
import com.example.projectemarketg3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/rank")
public class RankController {

    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Ranking> getAllRank(){
        return rankingRepository.findAll();
    }


}
