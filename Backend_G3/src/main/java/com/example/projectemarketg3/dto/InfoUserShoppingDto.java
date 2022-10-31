package com.example.projectemarketg3.dto;

import lombok.*;

import java.sql.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InfoUserShoppingDto {
    private Long userId;
    private String note;
    private String addressUser;
    private String nameUser;
    private String phoneUser;
    private Double point;
}
