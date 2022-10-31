package com.example.projectemarketg3.service;


import com.example.projectemarketg3.entity.Token;
import com.example.projectemarketg3.entity.User;
import com.example.projectemarketg3.exception.BadRequestException;
import com.example.projectemarketg3.repository.TokenRepository;
import com.example.projectemarketg3.repository.UserRepository;
import com.example.projectemarketg3.request.LoginRequest;
import com.example.projectemarketg3.request.RegisterUserRequest;
import com.example.projectemarketg3.request.UserRequest;
import com.example.projectemarketg3.security.UpdatePassRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;


    //    LOGIN
    public UserRequest login(LoginRequest loginRequest, HttpSession session) {
        //tao foi tuong dua tren thong tin xac thuwc
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
//        tien hanh xac thuc (@Bean trong config)
        Authentication authentication = authenticationManager.authenticate(token);
//        luu tru thong tin user dang nhap
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        luu thong tin trong session -> name minh
        session.setAttribute("MY_SESSION", authentication.getName());

//        "chao mung " + loginRequest.getEmail();
        return userService.infoUserByEmail(loginRequest.getEmail());

    }

    // LOGOUT USER
    public String logout(HttpSession session) {
        session.invalidate();
        return "logout success";
    }

    // REGISTER_USER
    public String register(RegisterUserRequest request) {
        //lay ra thong tin user tren email
        boolean userExists = userRepository.findByEmail(request.getEmail()).isPresent();

        if (userExists) {
            //neu user dc tim thay trung cac thuoc tinh chua kich hoat
            // -> gui mail  khich hoat

            throw new RuntimeException("email da ton tai");
        }

        // ma hoa password
        String passwordEncodeString = passwordEncoder.encode(request.getPassword());
        //tao user luu vao CSDL
        User user = new User(request.getName(), request.getEmail(), passwordEncodeString, new ArrayList<>(List.of("USER")));
        userRepository.save(user);


        return generateTokenAndSendEmail(user);
    }

    public String generateTokenAndSendEmail(User user) {
        // sinh ra chuoi token
        String tokenString = UUID.randomUUID().toString();

        //tao ra token va luu vao CSDL
        Token token = new Token(
                tokenString,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        tokenRepository.save(token);

//        GUI MAIL
        String link = "https://market-g3.herokuapp.com/api/auth/confirm?" + tokenString;
        mailService.send(user.getEmail(), "Chúc mừng bạn đã đăng kí tài khoản G3Mart thành công ","Link xác thực : " + link);
        return link;
    }

    public String confirmToken(String tokenString) {
        //lay thong tin token
        Token token = tokenService.getToken(tokenString).orElseThrow(() ->
                new RuntimeException("khong thay token"));

        //xem token confirm chua
        if (token.getConfirmedAt() != null) {
            throw new RuntimeException("token da duoc xac thuc");
        }

//            xe token het han chua
        LocalDateTime expected = token.getExpiresAt();
        if (expected.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("token da het thoi gian dung");
        }


//        active token
        tokenService.setConfirmedAt(tokenString);
//        active user
        userService.enableUser(token.getUser().getEmail());
        return "confirm";
    }

    //DOI PASS
    public String updatePassword(User user, UpdatePassRequest request) {
//        oldPass dung khong
        if (!passwordEncoder.matches(request.getOldPass(), user.getPassword())) {
            throw new BadRequestException("Mat khau cu khong chinh xac");
        }
//            newPass != oldPassword
        if (request.getOldPass().equals(request.getNewPass())) {
            throw new BadRequestException("Mat khau Cu va Moi trung nhau");
        }
//        newPass != newPass2
        if (!request.getNewPass2().equals(request.getNewPass())) {
            throw new BadRequestException("Mat khau moi va mat khau xac nhan khong trung nhau");
        }

        // ma hoa password
        String passwordEncodeString = passwordEncoder.encode(request.getNewPass());

        user.setPassword(passwordEncodeString);
        userRepository.save(user);

        return "doi pass thanh cong";
    }

}
