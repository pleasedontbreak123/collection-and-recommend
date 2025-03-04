package org.example.collectionandrecommend.demos.web.service;


import com.github.pagehelper.PageInfo;
import org.example.collectionandrecommend.demos.web.model.dto.PostDTO;
import org.example.collectionandrecommend.demos.web.model.entity.Post;
import org.example.collectionandrecommend.demos.web.model.vo.PostVO;

import java.time.LocalDateTime;
import java.util.List;

public interface PostService {
    void createPost(PostDTO postDTO);
    List<PostVO> getAllPosts();

    void deleteById(Integer postId);

    PageInfo<PostVO> pageListBy(Integer pageNum, Integer pageSize, String content);

    void adminDelete(Integer postId);
}
