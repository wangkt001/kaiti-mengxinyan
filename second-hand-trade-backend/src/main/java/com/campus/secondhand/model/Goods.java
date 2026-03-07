package com.campus.secondhand.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private Integer categoryId;

    private Integer userId;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
