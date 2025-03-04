package org.example.collectionandrecommend.demos.web.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.example.collectionandrecommend.demos.web.model.dto.PostDTO;
import org.example.collectionandrecommend.demos.web.model.vo.PostVO;
import org.example.collectionandrecommend.demos.web.response.Result;
import org.example.collectionandrecommend.demos.web.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * 新增帖子
     * @param postDTO
     * @return
     */
    @Operation(description = "新增帖子")
    @PostMapping("add")
    public Result createPost(@RequestBody PostDTO postDTO) {
        postService.createPost(postDTO);
        return Result.success("添加成功");
    }

    /**
     * 获取所有帖子
     * @return
     */
    @Operation(description = "获取所有帖子")
    @PostMapping("/getAll")
    public Result<List<PostVO>> getAllPosts() {
        return Result.success(postService.getAllPosts());
    }

    /**
     * 删除帖子和关联的评论
     * @param postId
     * @return
     */
    @Operation(description = "根据id删除帖子和帖子关联的评论")
    @PostMapping("/delete/{postId}")
    public Result deleteById(@PathVariable Integer postId) {
        postService.deleteById(postId);
        return Result.success("删除成功");
    }

    /**
     * 条件分页查询 支持字段模糊查询
     * @param pageNum
     * @param pageSize
     * @param content
     * @return
     */
    @Operation(description = "条件分页查询帖子")
    @PostMapping("/pageListBy")
    public Result<PageInfo<PostVO>> pageListBy(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "5") Integer pageSize,
                                               @RequestParam(required = false) String content
    ) {
        PageInfo<PostVO> pageInfo = postService.pageListBy(pageNum, pageSize, content);
        return Result.success(pageInfo);
    }

    /**
     * 管理端删帖
     * @param postId
     * @return
     */
    @Operation(description = "管理端删除帖子")
    @PostMapping("/admin/delete")
    public Result admindelete(@RequestParam Integer postId){

        postService.adminDelete(postId);
        return Result.success("删除成功");
    }
}
