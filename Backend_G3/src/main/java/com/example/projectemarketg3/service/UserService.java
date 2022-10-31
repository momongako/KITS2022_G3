package com.example.projectemarketg3.service;


import com.example.projectemarketg3.entity.User;
import com.example.projectemarketg3.exception.NotFoundException;
import com.example.projectemarketg3.repository.UserRepository;
import com.example.projectemarketg3.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private FileService fileService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw new UsernameNotFoundException("Not found email " + email);
        });
    }

    // Lưu user
    public void saveUser(User user) {
        userRepository.save(user);
    }

    // Lấy thông tin của user dựa trên email
    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }

    // Active user
    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    // Upload file
    public String uploadFile(Long id, MultipartFile file) {
        if (userRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Không tồn tại user có id = " + id);
        }

        return fileService.uploadFile(id, file);
    }

    // Xem file
    public byte[] readFile(Long id, String fileId) {
        return fileService.readFile(id, fileId);
    }

    // Xóa file
    public void deleteFile(Long id, String fileId) {
        fileService.deleteFile(id, fileId);
    }

    // Lấy danh sách file
    public List<String> getFiles(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Không tồn tại user có id = " + id);
        }

        return fileService.getFiles(id);
    }

    public UserRequest infoUserByEmail(String email) {
        User user = userRepository.getByEmail(email);
        UserRequest userRequest = UserRequest.builder()
                .id(user.getId())
                .email(user.getEmail())
                .address(user.getAddress())
                .dob(user.getDob())
                .gender(user.getGender())
                .name(user.getName())
                .image(user.getImage())
                .phone(user.getPhone())
                .point(user.getPoint())
                .ranking(user.getRanking())
                .rank_date(user.getRank_date())
                .role(user.getRole())
                .build();

        return userRequest;
    }

    public UserRequest infoUserById(Long id) {
        User user = userRepository.getUserById(id);
        UserRequest userRequest = UserRequest.builder()
                .id(user.getId())
                .email(user.getEmail())
                .address(user.getAddress())
                .dob(user.getDob())
                .gender(user.getGender())
                .name(user.getName())
                .image(user.getImage())
                .phone(user.getPhone())
                .point(user.getPoint())
                .ranking(user.getRanking())
                .rank_date(user.getRank_date())
                .role(user.getRole())
                .build();

        return userRequest;
    }

    public List<UserRequest> findDistinctByRanking_NameOrderByRank_dateDesc(String name) {
        List<UserRequest> userRequestList = new ArrayList<>();
        List<User> userList = userRepository.findDistinctByRanking_NameOrderByRank_dateDesc(name);
        userList.forEach(s-> {
            userRequestList.add(UserRequest.builder()
                    .id(s.getId())
                    .email(s.getEmail())
                    .address(s.getAddress())
                    .dob(s.getDob())
                    .gender(s.getGender())
                    .name(s.getName())
                    .image(s.getImage())
                    .phone(s.getPhone())
                    .point(s.getPoint())
                    .ranking(s.getRanking())
                    .rank_date(s.getRank_date())
                    .build());
        });

        return userRequestList;
    }


}
