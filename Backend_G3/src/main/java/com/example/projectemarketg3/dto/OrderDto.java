package com.example.projectemarketg3.dto;

import lombok.*;

import java.sql.Date;
import java.util.Set;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Date createAt;
    private String note;
    private Long totalPrice;
    private Set<Long> orderDetailsId;
    private String status;
    private Long userId;
}
