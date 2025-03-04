package org.example.collectionandrecommend.demos.web.mapper;

import org.apache.ibatis.annotations.*;
import org.example.collectionandrecommend.demos.web.model.entity.Comment;
import org.example.collectionandrecommend.demos.web.model.vo.CommentVO;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("INSERT INTO comments (user_id, comment_parent_id, content, created_at, post_id) " +
            "VALUES (#{userId}, #{commentParentId}, #{content}, #{createdAt}, #{postId})")
    @Options(useGeneratedKeys = true, keyProperty = "commentId")
    void insertComment(Comment comment);

    @Select("SELECT * FROM comments WHERE post_id = #{postId} and comment_parent_id is null")
    List<CommentVO> findCommentsByPostId(Integer postId);

    @Delete("delete FROM comments where post_id = #{postId}")
    void deleteByPostId(Integer postId);

    List<Integer> findChildComments(@Param("currentLevelComments") List<Integer> currentLevelComments);

    void deleteCommentsBatch(@Param("commentIds") ArrayList<Integer> commentIds);

    @Select("select * FROM  comments WHERE comment_id = #{commentId} and user_id = #{userId}")
    Comment selectOne(@Param("userId") Integer userId, @Param("commentId") Integer commentId);

    List<CommentVO> findCommentsByCommentIds(@Param("commentIds")List<Integer> commentIds);


}
