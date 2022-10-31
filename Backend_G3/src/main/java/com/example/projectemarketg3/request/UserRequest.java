package com.example.projectemarketg3.request;

import com.example.projectemarketg3.entity.Ranking;
import lombok.*;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserRequest {
    private Long id;
    private String name;
    private String email;
    private Date dob;
    private String gender;
    private String phone;
    private String address;
    private String image;
    private Double point;
    private Ranking ranking;
    private Date rank_date;
    private List<String> role;
}
