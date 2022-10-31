package com.example.projectemarketg3.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "note",columnDefinition = "TEXT")
    private String note;

    @Column(name = "total_price")
    private Double totalPrice;

    @Type(type = "json")
    @Column(name = "id_detail", columnDefinition = "json")
    private List<String> idDetail;

    @Column(name = "status")
    private String status;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "ship")
    private Integer ship;

    @Column(name = "address_user")
    private String addressUser;

    @Column(name = "name_user")
    private String nameUser;

    @Column(name = "phone_user")
    private String phoneUser;

    @Column(name = "discount")
    private Integer discount;

}