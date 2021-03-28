package cn.yinjiahui.mapper;


import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;




public interface VisitorMapper {

    @Select("select visitorNum from visitor")
    int getTotalVisitor();

    @Update("update visitor set visitorNum=visitorNum+1")
    void addVisitorNum();
}
