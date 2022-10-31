package com.example.projectemarketg3.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StatusOrderRequest {
    private Long orderId;
    private Long userId;
    private Long statusId;
}
