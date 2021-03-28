package cn.yinjiahui.mapper;


import cn.yinjiahui.pojo.FeedBack;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface FeedBackMapper {

    @Insert("insert into feedback(feedbackContent,contactInfo,personId,feedbackDate) values(#{feedbackContent},#{contactInfo},#{personId},#{feedbackDate})")
    void save(FeedBack feedBack);

    @Select("select * from feedback")
    List<FeedBack> getAllFeedback();

}
