package com.example.projectemarketg3.dto;

import lombok.*;

import java.sql.Date;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {
    private Date createAt;
    private String note;
    private String image;
    private Integer star;
    private Boolean checking;
    private Long userId;
    private Long productId;
}
