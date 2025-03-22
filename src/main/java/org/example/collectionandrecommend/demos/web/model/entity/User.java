package org.example.collectionandrecommend.demos.web.model.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String realName;
    private String avatarUrl;
    private java.util.Date createdAt;
    private java.util.Date updatedAt;
}