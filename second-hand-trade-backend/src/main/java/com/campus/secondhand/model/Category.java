package com.campus.secondhand.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
