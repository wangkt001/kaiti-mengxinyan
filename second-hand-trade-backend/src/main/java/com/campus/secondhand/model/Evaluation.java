package com.campus.secondhand.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("evaluations")
public class Evaluation implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer orderId;

    private Integer evaluatorId;

    private Integer evaluatedId;

    private Integer rating;

    private String comment;

    private LocalDateTime createdAt;
}
