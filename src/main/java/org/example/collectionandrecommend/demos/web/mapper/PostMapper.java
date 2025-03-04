package org.example.collectionandrecommend.demos.web.mapper;

import org.apache.ibatis.annotations.*;
import org.example.collectionandrecommend.demos.web.model.entity.Post;
import org.example.collectionandrecommend.demos.web.model.vo.PostVO;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PostMapper {

    /**
     * 新增帖子
     * @param post
     */
    @Insert("INSERT INTO posts (user_id, content, created_at) VALUES (#{userId}, #{content}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "postId")
    void insertPost(Post post);

    /**
     * 获取所有帖子
     * @return
     */
    @Select("SELECT * FROM posts")
    List<PostVO> findAllPosts();

    /**
     * 根据id获取帖子
     * @param postId
     * @return
     */
    @Select("SELECT * FROM posts WHERE post_id = #{postId}")
    PostVO findPostById(Long postId);

    @Delete("DELETE FROM posts WHERE post_id = #{postId}")
    void deleteById(Integer postId);

    List<PostVO> pageListBy(String content);
}
