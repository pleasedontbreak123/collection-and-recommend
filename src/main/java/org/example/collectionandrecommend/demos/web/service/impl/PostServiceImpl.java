package org.example.collectionandrecommend.demos.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.example.collectionandrecommend.demos.web.mapper.CommentMapper;
import org.example.collectionandrecommend.demos.web.mapper.PostMapper;
import org.example.collectionandrecommend.demos.web.model.dto.PostDTO;
import org.example.collectionandrecommend.demos.web.model.entity.Post;
import org.example.collectionandrecommend.demos.web.model.vo.PostVO;
import org.example.collectionandrecommend.demos.web.utils.redisUtil.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 新增帖子
     * @param postDTO
     */
    @Override
    public void createPost(PostDTO postDTO) {
        Post post = new Post();
        post.setUserId(postDTO.getUserId());
        post.setContent(postDTO.getContent());
        post.setCreatedAt(LocalDateTime.now());
        postMapper.insertPost(post);
    }

    /**
     * 获取所有帖子
     * @return
     */
    @Override
    public List<PostVO> getAllPosts() {
        return postMapper.findAllPosts();
    }

    /**
     * 条件分页查询
     * @param pageNum
     * @param pageSize
     * @param content
     * @return
     */
    @Override
    public PageInfo<PostVO> pageListBy(Integer pageNum, Integer pageSize, String content) {

        log.info("pageSize: {}", pageSize);

        PageHelper.startPage(pageNum, pageSize);

        List<PostVO> posts= postMapper.pageListBy(content);

        PageInfo<PostVO> pageInfo = new PageInfo<>(posts);

        return pageInfo;
    }

    /**
     * 管理端删帖
     * @param postId
     */
    @Transactional
    @Override
    public void adminDelete(Integer postId) {

        //删除帖子之前要把帖子关联的评论删除
        commentMapper.deleteByPostId(postId);

        //删除帖子
        postMapper.deleteById(postId);
    }

    /**
     * 根据id删除帖子和关联的评论
     * @param postId
     */
    @Transactional
    @Override
    public void deleteById(Integer postId) {

        //删除帖子之前要把帖子关联的评论删除
        commentMapper.deleteByPostId(postId);

        //删除帖子
        postMapper.deleteById(postId);
    }
}
