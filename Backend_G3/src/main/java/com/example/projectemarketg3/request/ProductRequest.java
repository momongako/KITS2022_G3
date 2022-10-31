package com.example.projectemarketg3.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;

    private Integer quantity;

    private Long buyPrice;

    private Long price;

    private String image;

    private Integer sold;

    private String origin;

    private String description;

    private String slug;

    private Long categoryId;

    private Long supplierId;

    private Boolean available;
}
