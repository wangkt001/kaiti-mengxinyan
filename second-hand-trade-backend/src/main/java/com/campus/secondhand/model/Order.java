package com.campus.secondhand.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String orderNumber;

    private Integer buyerId;

    private Integer sellerId;

    private Integer goodsId;

    private String goodsName;

    private String buyerName;

    private String sellerName;

    private Integer quantity;

    private BigDecimal totalPrice;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
