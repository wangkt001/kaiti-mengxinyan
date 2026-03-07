package com.campus.secondhand.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Evaluation implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer orderId;

    private Integer evaluatorId;

    private Integer evaluatedId;

    private Integer rating;

    private String comment;

    private LocalDateTime createdAt;
}
