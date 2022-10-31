package com.example.projectemarketg3.repository;

import com.example.projectemarketg3.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>, CrudRepository<CartItem,Long> {
    List<CartItem> getByUser_Id(Long id);

    Set<CartItem> findByUser_Id(Long id);

    CartItem findByProduct_IdAndUser_Id(Long productId, Long userId);

    @Modifying
    @Query(value = "DELETE FROM cart_item ", nativeQuery = true)
    void deleteAllCartItem();
}