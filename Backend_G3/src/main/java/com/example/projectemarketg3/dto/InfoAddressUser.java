package com.example.projectemarketg3.dto;

import com.example.projectemarketg3.entity.Ranking;
import lombok.*;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InfoAddressUser {
    private Long idUser;
    private String name;
    private Date dob;
    private String gender;
    private String phone;
    private String address;
    private String image;
}
