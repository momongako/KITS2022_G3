package com.example.projectemarketg3.request;

import com.example.projectemarketg3.entity.Status;
import com.example.projectemarketg3.entity.User;

import java.sql.Date;
import java.util.Set;

public class OrderOffRequest {
    private Date createAt;
    private String note;
    private Long totalPrice;
    private String addressUser;
    private String nameUser;
    private String phoneUser;
    private Integer disscount;
    private Long statusId;
    private Long userId;
    private Long userIdSucceed;
    private Double point;
    private Set<Long> orderDetailsId;
}
