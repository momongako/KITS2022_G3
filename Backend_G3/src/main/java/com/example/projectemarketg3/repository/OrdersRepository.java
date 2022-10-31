package com.example.projectemarketg3.repository;

import com.example.projectemarketg3.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> getByUser_IdOrderByCreateAtDesc(Long id);

    List<Orders> findByUser_Id(Long id);

@Query(value = "SELECT * FROM orders o WHERE o.create_at LIKE %/?1/% ",nativeQuery = true)
    List<Orders> getOrdersByMonth(int month);

    List<Orders> findByStatus_IdOrderByCreateAtDesc(Long id);



}