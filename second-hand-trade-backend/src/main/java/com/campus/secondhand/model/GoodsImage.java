package com.campus.secondhand.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class GoodsImage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer goodsId;

    private String imagePath;

    private LocalDateTime createdAt;
}
