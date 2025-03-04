package org.example.collectionandrecommend.demos.web.service;

import com.github.pagehelper.PageInfo;
import org.example.collectionandrecommend.demos.web.model.dto.CommentDTO;
import org.example.collectionandrecommend.demos.web.model.entity.Comment;
import org.example.collectionandrecommend.demos.web.model.vo.CommentVO;

import java.util.List;

public interface CommentService {

    void addComment(CommentDTO commentDTO);

    List<CommentVO> getCommentsByPostId(Integer postId);

    void delete(Integer userId, Integer commentId);

    PageInfo<CommentVO> pageOne(Integer postId, Integer pageNum, Integer pageSize);

    PageInfo<CommentVO> pageTwo(Integer commentId, Integer pageNum, Integer pageSize);

    void adminDelete(Integer commentId);
}
