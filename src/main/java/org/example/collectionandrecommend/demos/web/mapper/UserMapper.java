package org.example.collectionandrecommend.demos.web.mapper;

import org.apache.ibatis.annotations.*;
import org.example.collectionandrecommend.demos.web.model.dto.UserFavorDto;
import org.example.collectionandrecommend.demos.web.model.entity.User;
import org.example.collectionandrecommend.demos.web.model.entity.UserFavor;

import java.util.List;


@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (username, password, email, phone, real_name, avatar_url, created_at, updated_at) VALUES (#{username}, #{password}, #{email}, #{phone}, #{realName}, #{avatarUrl}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);
    @Select("SELECT user_id, username, password, email, phone, real_name, avatar_url, created_at, updated_at FROM users WHERE username = #{username}")
    @Results({
            @Result(property = "id", column = "user_id"),

    })
    User findByUsername(String username);

    @Select("SELECT * FROM users WHERE username = #{username} OR email = #{email} LIMIT 1")
    User findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    @Insert("INSERT INTO user_favorites ( user_id, event_id, created_at) VALUES (#{userId},#{eventId},#{createdAt})")
    void insertFavor(UserFavor userFavor);

    @Select("SELECT event_id FROM user_favorites where user_id = #{userId}")
    List<Integer> listFavor(Integer userId);

    @Select("SELECT * FROM user_favorites")
    List<UserFavor> listFavorAll();
}

