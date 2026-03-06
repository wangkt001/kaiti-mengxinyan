package com.campus.secondhand.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("disputes")
public class Dispute implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
}
