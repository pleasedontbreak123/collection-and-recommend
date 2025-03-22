package org.example.collectionandrecommend.demos.web.model.vo;

import lombok.Data;

@Data
public class UserVo {
    private Integer id;
    private String username;
    private String email;
    private String phone;
    private String realName;
    private String avatarUrl;
    private java.util.Date createdAt;
    private java.util.Date updatedAt;
    private String token;
}