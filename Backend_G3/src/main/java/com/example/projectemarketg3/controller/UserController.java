package com.example.projectemarketg3.controller;

import com.example.projectemarketg3.dto.InfoAddressUser;
import com.example.projectemarketg3.entity.Orders;
import com.example.projectemarketg3.entity.User;
import com.example.projectemarketg3.repository.OrdersRepository;
import com.example.projectemarketg3.repository.UserRepository;
import com.example.projectemarketg3.request.UserRequest;
import com.example.projectemarketg3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrdersRepository ordersRepository;

    //    USER UPDATE ADDRESS
// Sửa thông tin User
    @PutMapping("/update-info")
    public InfoAddressUser updateUser(@RequestBody InfoAddressUser infoUser) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getUserById(infoUser.getIdUser());
        user.setName(infoUser.getName() == null ? user.getName() : infoUser.getName());
        user.setDob(infoUser.getDob() == null ? user.getDob() : infoUser.getDob());
        user.setGender(infoUser.getGender() == null ? user.getGender() : infoUser.getGender());
        user.setPhone(infoUser.getPhone() == null ? user.getPhone() : infoUser.getPhone());
        user.setAddress(infoUser.getAddress() == null ? user.getAddress() : infoUser.getAddress());
        user.setImage(infoUser.getImage() == null ? user.getImage() : infoUser.getImage());

        userRepository.save(user);
        return infoUser;
    }

    @GetMapping("/order-history")
    public List<Orders> historyOrderUser() {
        UserRequest user = (UserRequest) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userRepository.getUserById(id);

        return ordersRepository.getByUser_IdOrderByCreateAtDesc(user.getId());
    }

    @GetMapping("/my-info")
    public UserRequest myInfo(){
        UserRequest user = (UserRequest) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

}
