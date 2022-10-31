package com.example.projectemarketg3.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "buy_price")
    private Long buyPrice;

    @Column(name = "price")
    private Long price;

    @Column(name = "image")
    private String image;

    @Column(name = "sold")
    private Integer sold;

    @Column(name = "origin")
    private String origin;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "slug")
    private String slug;

    @Column(name = "avg_rating")
    private Double avgRating;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Transient
    private MultipartFile file;

    @Column(name = "available")
    private Boolean available;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}