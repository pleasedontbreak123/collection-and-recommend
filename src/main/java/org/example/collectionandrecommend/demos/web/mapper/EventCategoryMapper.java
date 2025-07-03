package org.example.collectionandrecommend.demos.web.mapper;

import org.apache.ibatis.annotations.*;
import org.example.collectionandrecommend.demos.web.model.entity.EventCategory;
import org.example.collectionandrecommend.demos.web.model.vo.EventCategoryVo;

import java.util.List;

@Mapper
public interface EventCategoryMapper {

    @Insert("insert into event_categories ( category_name, created_at, status) " +
            "values (#{categoryName},#{createAt},#{status})")
    void addCate(EventCategory eventCategory);

    @Select("SELECT * FROM event_categories where status = 'ACTIVE'")
    @Results({
            @Result(property = "eventCategoryId", column = "category_id"),
            @Result(property = "categoryName", column = "category_name"),
    })
    List<EventCategoryVo> findAll();

    @Update("UPDATE event_categories set status = 'DELETED' where category_id = #{cateId}")
    int deleteCate(Integer cateId);

    @Update("update event_category_relations set status = 'DELETED' where category_id = #{cateId}")
    int deleteRelation(Integer cateId);
}
