package org.example.collectionandrecommend.demos.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.collectionandrecommend.demos.web.mapper.CommentMapper;
import org.example.collectionandrecommend.demos.web.model.dto.CommentDTO;
import org.example.collectionandrecommend.demos.web.model.entity.Comment;
import org.example.collectionandrecommend.demos.web.model.vo.CommentVO;
import org.example.collectionandrecommend.demos.web.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 发评论
     *
     * @param commentDTO
     */
    @Override
    public void addComment(CommentDTO commentDTO) {

        Comment comment = new Comment();
        comment.setUserId(commentDTO.getUserId());
        comment.setPostId(commentDTO.getPostId());
        comment.setCommentParentId(commentDTO.getCommentParentId());
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        commentMapper.insertComment(comment);
    }

    /**
     * 查所有评论
     *
     * @param postId
     * @return
     */
    @Override
    public List<CommentVO> getCommentsByPostId(Integer postId) {

        return commentMapper.findCommentsByPostId(postId);
    }

    /**
     * 删除单条评论
     *
     * @param userId
     * @param commentId
     */
    @Transactional
    @Override
    public void delete(Integer userId, Integer commentId) {

        //根据userId和commentId删除评论

        //用户只能删除自己的评论
        Comment comment = commentMapper.selectOne(userId, commentId);
        //该用户没发表过评论
        if(comment == null)
        {
            return;
        }
        else {
            // 初始化一个集合来存储所有相关的评论 ID
            Set<Integer> allCommentIds = new HashSet<>();

            // 先把根评论添加到集合
            allCommentIds.add(commentId);

            //循环查询所有子评论，并将其添加到集合
            List<Integer> currentLevelComments = new ArrayList<>();
            currentLevelComments.add(commentId);

            while (!currentLevelComments.isEmpty()) {
                // 查询当前层次的子评论
                List<Integer> childComments = commentMapper.findChildComments(currentLevelComments);

                //如果有子评论，将子评论的 ID 加入集合
                if (childComments != null && !childComments.isEmpty()) {
                    allCommentIds.addAll(childComments);
                    currentLevelComments = childComments;  // 继续查找子评论的子评论
                } else {
                    break;  // 如果没有子评论，退出循环
                }
            }
            //批量删除所有相关评论
            commentMapper.deleteCommentsBatch(new ArrayList<>(allCommentIds));
        }

    }

    /**
     * 分页查询—一级评论
     * @param postId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<CommentVO> pageOne(Integer postId, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        List<CommentVO> comments = commentMapper.findCommentsByPostId(postId);

        PageInfo<CommentVO> pageInfo = new PageInfo<>(comments);

        return pageInfo;
    }

    /**
     * 分页查询——一级评论下的其他评论
     * @param commentId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Transactional
    @Override
    public PageInfo<CommentVO> pageTwo(Integer commentId, Integer pageNum, Integer pageSize) {

        // 初始化一个集合来存储所有相关的评论 ID
        Set<Integer> allCommentIds = new HashSet<>();

        // 先把根评论添加到集合
        allCommentIds.add(commentId);

        //循环查询所有子评论，并将其添加到集合
        List<Integer> currentLevelComments = new ArrayList<>();
        currentLevelComments.add(commentId);

        while (!currentLevelComments.isEmpty()) {
            // 查询当前层次的子评论
            List<Integer> childComments = commentMapper.findChildComments(currentLevelComments);

            //如果有子评论，将子评论的 ID 加入集合
            if (childComments != null && !childComments.isEmpty()) {
                allCommentIds.addAll(childComments);
                currentLevelComments = childComments;  // 继续查找子评论的子评论
            } else {
                break;  // 如果没有子评论，退出循环
            }
        }

        //去除一级评论
        allCommentIds.remove(commentId);

        //找到所有其他级别的评论后开始分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<CommentVO> comments = commentMapper.findCommentsByCommentIds(new ArrayList<>(allCommentIds));
        PageInfo<CommentVO> pageInfo = new PageInfo<>(comments);
        return pageInfo;
    }

    /**
     * 管理端删评论
     * @param commentId
     */
    @Transactional
    @Override
    public void adminDelete(Integer commentId) {
        // 初始化一个集合来存储所有相关的评论 ID
        Set<Integer> allCommentIds = new HashSet<>();

        // 先把根评论添加到集合
        allCommentIds.add(commentId);

        //循环查询所有子评论，并将其添加到集合
        List<Integer> currentLevelComments = new ArrayList<>();
        currentLevelComments.add(commentId);

        while (!currentLevelComments.isEmpty()) {
            // 查询当前层次的子评论
            List<Integer> childComments = commentMapper.findChildComments(currentLevelComments);

            //如果有子评论，将子评论的 ID 加入集合
            if (childComments != null && !childComments.isEmpty()) {
                allCommentIds.addAll(childComments);
                currentLevelComments = childComments;  // 继续查找子评论的子评论
            } else {
                break;  // 如果没有子评论，退出循环
            }
        }
        //批量删除所有相关评论
        commentMapper.deleteCommentsBatch(new ArrayList<>(allCommentIds));
    }
}