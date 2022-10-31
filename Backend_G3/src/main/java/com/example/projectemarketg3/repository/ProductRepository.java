package com.example.projectemarketg3.repository;

import com.example.projectemarketg3.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findTop6AllByOrderBySoldDesc();

    List<Product> findByNameLikeIgnoreCase(String name);

    List<Product> getByNameStartsWithIgnoreCaseOrderByNameAsc(String name);

    List<Product> findByCategory_Name(String name);

    List<Product> findByPriceBetweenOrderByPriceAsc(Long sellPriceStart, Long sellPriceEnd);

    Product getProductById(Long id);

    List<Product> getByCategory_NameContainsIgnoreCase(String category);

    List<Product> getByNameStartsWithIgnoreCaseAndPriceBetween(String name, Long sellPriceStart, Long sellPriceEnd);

    List<Product> getByNameLikeAndCategory_NameLikeAndPriceBetween(String name, String category, Long sellPriceStart, Long sellPriceEnd);

    @Query(value = "SELECT * FROM product p\n" +
            "INNER JOIN category c ON c.id = p.category_id\n" +
            "INNER JOIN supplier s ON s.id = p.supplier_id\n" +
            "WHERE (p.category_id = ?2) \n" +
            "AND (p.description LIKE %?1% OR p.name LIKE %?1% OR p.origin LIKE %?1% OR s.name  LIKE %?1%)\n" +
            "AND (p.price BETWEEN ?3 AND ?4)"
            , nativeQuery = true)
    List<Product> findProductByCategoryAndNameAndPrice(String name, Long categoryId, Long start, Long end);

    List<Product> findByCategory_Id(Long id);

    List<Product> findByAvgRatingBetweenOrderByAvgRatingDesc(Double avgRatingStart, Double avgRatingEnd);



@Query(nativeQuery = true,value = "SELECT DISTINCT *\n" +
        "FROM product p \n" +
        "INNER JOIN category c ON c.id = p.category_id \n" +
        "INNER JOIN supplier s ON s.id = p.supplier_id\n" +
        "WHERE\n" +
        "(p.name LIKE %:name% OR p.description LIKE %:name% OR s.name LIKE %:name%)\n" +
        "AND (c.name LIKE %:category%)\n" +
        "AND (p.origin LIKE %:origin%)\n" +
        "AND (p.price BETWEEN :start AND :end)\n" +
        "ORDER BY p.price")
    List<Product> searchProductByKeyword(@Param("name") String name,
                                         @Param("origin") String origin,
                                         @Param("category") String category,
                                         @Param("start") Long start,
                                         @Param("end") Long end);



}