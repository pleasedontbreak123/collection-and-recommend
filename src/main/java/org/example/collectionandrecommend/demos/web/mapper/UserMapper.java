package org.example.collectionandrecommend.demos.web.mapper;

import org.apache.ibatis.annotations.*;
import org.example.collectionandrecommend.demos.web.model.entity.User;



@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (username, password, email, phone, real_name, avatar_url, created_at, updated_at) VALUES (#{username}, #{password}, #{email}, #{phone}, #{real_name}, #{avatar_url}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);
    @Select("SELECT user_id, username, password, email, phone, real_name, avatar_url, created_at, updated_at FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT * FROM users WHERE username = #{username} OR email = #{email} LIMIT 1")
    User findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);
}

