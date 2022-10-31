package com.example.projectemarketg3.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "image")
public class Image {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "url")
    private String url;

    @Column(name = "create_at")
    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
