package com.example.projectemarketg3.repository;

import com.example.projectemarketg3.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> getByStarOrderByCreateAtDesc(Integer star);

    List<Rating> findByUser_IdOrderByCreateAtDesc(Long id);

    List<Rating> findByProduct_IdOrderByCreateAtDesc(Long id);

//    @Query("select new com.example.projectemarketg3.dto.RatingDto(r.createAt,r.note,r.image,r.star,r.checking,r.user.id,r.product.id) " +
//            "from Rating r order by r.createAt DESC")
//    List<RatingDto> getByOrderByCreateAtDesc();

    @Query("select AVG(r.star) from Rating r inner join Product p\n" +
            "                           on p.id = r.productID\n" +
            "                           where r.checking = true and p.name = :nameProduct")
    String getAVGStarOfProduct(String nameProduct);


    @Query("select COUNT(r.star) from Rating r inner join Product p\n" +
            "                           on p.id = r.productID\n" +
            "                           where r.checking = true and p.name = :nameProduct")
    String getCountStarOfProduct(String nameProduct);

    @Query(value = "SELECT * FROM user u INNER JOIN rating r ON u.id = r.user_id\n" +
            "                    INNER JOIN product p ON p.id = r.product_id\n" +
            "    WHERE r.checking = true and p.name = :nameProduct", nativeQuery = true)
    List<Rating> getUserRating(String nameProduct);

}