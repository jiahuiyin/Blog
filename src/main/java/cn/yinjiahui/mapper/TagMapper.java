package cn.yinjiahui.mapper;


import org.apache.ibatis.annotations.*;

import java.util.List;



public interface TagMapper {

    @Insert("insert into tags(tagName) values(#{tagName})")
    void insertTag(String  tagName);



    @Select("select tagName from tags")
    List<String> findTags();

    @Select("select id from tags where tagName=#{tagName}")
    Integer findIsExistByTagName(@Param("tagName") String tagName);


    @Select("SELECT COUNT(*) FROM tags")
    Integer getTagNum();
}
