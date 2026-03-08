package com.campus.secondhand.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Dispute implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer orderId;

    private Integer userId;

    private String title;

    private String description;

    private String status;

    private Integer adminId;

    private String resolution;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String orderNumber;

    private String userName;
}
