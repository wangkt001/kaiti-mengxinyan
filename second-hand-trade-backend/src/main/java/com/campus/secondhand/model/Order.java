package com.campus.secondhand.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("orders")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String orderNumber;

    private Integer buyerId;

    private Integer sellerId;

    private Integer goodsId;

    private Integer quantity;

    private BigDecimal totalPrice;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
