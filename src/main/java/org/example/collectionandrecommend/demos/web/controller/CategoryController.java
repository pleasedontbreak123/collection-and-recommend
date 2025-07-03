package org.example.collectionandrecommend.demos.web.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.collectionandrecommend.demos.web.model.dto.EventCategoryDto;
import org.example.collectionandrecommend.demos.web.model.dto.EventDto;
import org.example.collectionandrecommend.demos.web.model.vo.EventCategoryVo;
import org.example.collectionandrecommend.demos.web.response.Result;
import org.example.collectionandrecommend.demos.web.service.CategoryService;
import org.example.collectionandrecommend.demos.web.service.EventService;
import org.example.collectionandrecommend.demos.web.utils.aop.LogAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor(onConstructor_= { @Autowired})
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(description = "添加竞赛分类")
    @LogAnnotation("添加竞赛分类")
    @PostMapping("/addCate")
    public Result addEvent(@RequestBody EventCategoryDto eventCategoryDto){
        categoryService.addCate(eventCategoryDto);
        return Result.success("添加成功");

    }

    @Operation(description = "展示所有分类项")
    @PostMapping("/listAll")
    public Result listAll(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "20") int pageSize){

        PageInfo<EventCategoryVo> pageInfo = categoryService.listAll(pageNum,pageSize);
        return Result.success("查询结果 ：",pageInfo);
    }

    @Operation(description = "删除指定分类并删除分类关系")
    @PostMapping("/delete")
    public Result deleteCate(@RequestParam Integer CateId){
        categoryService.delete(CateId);
        return Result.success("已删除"+CateId+"分类已经分类关系");

    }
}
