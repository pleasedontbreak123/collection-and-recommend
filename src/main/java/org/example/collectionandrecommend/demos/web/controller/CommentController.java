package org.example.collectionandrecommend.demos.web.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.example.collectionandrecommend.demos.web.model.dto.CommentDTO;
import org.example.collectionandrecommend.demos.web.model.entity.Comment;
import org.example.collectionandrecommend.demos.web.model.vo.CommentVO;
import org.example.collectionandrecommend.demos.web.response.Result;
import org.example.collectionandrecommend.demos.web.service.CommentService;
import org.example.collectionandrecommend.demos.web.utils.aop.LogAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/comment")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;


    /**
     * 新增评论
     * @param commentDTO
     * @return
     */
    @Operation(description = "新增评论")
    @PostMapping("/add")
    public Result addComment(@RequestBody CommentDTO commentDTO) {
        commentService.addComment(commentDTO);
        return Result.success("添加成功");
    }

    /**
     * 根据帖子id获取所有评论
     * @param postId
     * @return
     */
    @Operation(description = "根据帖子id获取所有评论")
    @PostMapping("/getAll/{postId}")
    public Result<List<CommentVO>> getCommentsByPostId(@PathVariable Integer postId) {
        return Result.success(commentService.getCommentsByPostId(postId));
    }

    /**
     * 根据id删除评论
     * @param userId
     * @return
     */
    @Operation(description = "根据id删除评论")
    @PostMapping("/delete/{userId}/{commentId}")
    public Result delete(@PathVariable Integer userId,@PathVariable Integer commentId) {
        commentService.delete(userId, commentId);
        return Result.success("删除成功");
    }

    /**
     * 根据帖子id分页查询评论——一级评论
     * @param postId
     * @return
     */
    @Operation(description = "根据帖子分页查询一级评论")
    @PostMapping("/pageOne")
    public Result<PageInfo<CommentVO>> pageByPostId(@RequestParam(required = true) Integer postId,
                                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                                    @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<CommentVO> pageInfo = commentService.pageOne(postId, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    /**
     * 分页查询一级评论下的其他评论
     * @param commentId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Operation(description = "根据一级评论id分页查询其他级别评论")
    @PostMapping("/pageTwo")
    public Result<PageInfo<CommentVO>> pageByCommentId(@RequestParam(required = true) Integer commentId,
                                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                                       @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<CommentVO> pageInfo = commentService.pageTwo(commentId, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    /**
     * 管理端删评论
     * @param commentId
     * @return
     */
    @Operation(description = "管理端删除评论")
    @PostMapping("/admin/delete")
    public Result adminDelete(@RequestParam Integer commentId) {
        commentService.adminDelete(commentId);
        return Result.success("删除成功");
    }
}
