package org.example.collectionandrecommend.demos.web.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码至少6位")
    private String password;
    
    @Email(message = "邮箱格式不正确")
    private String email;
    private String phone;
    private String realName;
    private String avatarUrl;
}