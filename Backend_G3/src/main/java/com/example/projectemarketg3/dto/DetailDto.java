package com.example.projectemarketg3.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailDto {
    private Long userId;
    private Integer quantity;
    private Long total;
    private Long productId;
    private Long cartId;
    private String productImage;
}
