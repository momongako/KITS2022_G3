package com.example.projectemarketg3.controller;

import com.example.projectemarketg3.entity.OrderDetail;
import com.example.projectemarketg3.entity.Orders;
import com.example.projectemarketg3.exception.NotFoundException;
import com.example.projectemarketg3.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {


    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;


    @GetMapping
    public List<Orders> getAllOrder() {
        return ordersRepository.findAll();
    }

//    // get order by ID rest api
//    @GetMapping("/{id}")
//    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
//        Orders order = ordersRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException
//                        ("order not exist with id :" + id));
//        return ResponseEntity.ok(order);
//    }


    //----------------------------------------------- DETAILS-----------------------------------------------------------

    @GetMapping("/details")
    public List<OrderDetail> getAllDetail() {
        return orderDetailRepository.findByOrderByTotalDesc();
    }

    // create a new order detail rest api


    // get order detail by OrderID rest api
    @GetMapping("/{id}/details")
    public List<OrderDetail> getOrderDetailsByOrderId(@PathVariable Long id) {
        return orderDetailRepository.getOrderDetailsByOrdersId(id);
    }

    //     get order detail by detail ID rest api
    @GetMapping("/details/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailsById(@PathVariable Long id) {
        OrderDetail orderDetails = orderDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        ("orderDetails not exist with id :" + id));
        return ResponseEntity.ok(orderDetails);
    }

    //     get order detail by detail ID rest api in 1 order
    @GetMapping("/*/details/{idd}")
    public ResponseEntity<OrderDetail> getOrderDetailsByIdInOneOrder(@PathVariable Long idd) {
        OrderDetail orderDetails = orderDetailRepository.findById(idd)
                .orElseThrow(() -> new NotFoundException
                        ("orderDetails not exist with id :" + idd));
        return ResponseEntity.ok(orderDetails);
    }

//     update order detail rest api
//    @PutMapping("/details/{id}")
//    public  ResponseEntity <OrderDetail> updateorderdetails(@PathVariable Long id, @RequestBody OrderDetail orderDetail){
//        OrderDetail orderdetails = orderDetailRepository.findById(id)
//                .orElseThrow (()->new NotFoundException
//                        ("orderdetails not exist with id :" + id));
//
//        orderdetails.set(orderDetail.get());
//        orderdetails.set(orderDetail.get());
//        orderdetails.set(orderDetail.get());
//
//        OrderDetail updatedOrderDetails = orderDetailRepository.save(orderdetails);
//
//        return  ResponseEntity.ok(updatedorderdetails);
//    }


    @GetMapping("/purchases/{id}")
    public long purchasesProductById(@PathVariable Long id) {
        return orderDetailRepository.countByProduct_Id(id);
    }

}
