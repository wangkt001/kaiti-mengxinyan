package com.campus.secondhand.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String password;

    private String role;

    private String idNumber;

    private String email;

    private String phone;

    private String avatar;

    private String realName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
