package com.example.projectemarketg3.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "note")
    private String note;

    @Column(name = "image")
    private String image;

    @Column(name = "star")
    private Integer star;

    @Column(name = "checking")
    private Boolean checking;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(name = "product_id",insertable = false,updatable = false)
    private Long productID;

}