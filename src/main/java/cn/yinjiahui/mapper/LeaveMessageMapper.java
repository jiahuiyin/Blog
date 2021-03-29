package cn.yinjiahui.mapper;


import cn.yinjiahui.pojo.LeaveMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;




public interface LeaveMessageMapper {

    @Select("select * from leave_message_record where pageName=#{pageName} order by id desc")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "answererId",property = "answererId"),
            @Result(column = "answererId",property = "answererUsername",javaType = String.class,
                    one=@One(select = "cn.yinjiahui.mapper.UserMapper.findUsernameById")),
            @Result(column = "answererId",property = "avatarImgUrl",javaType = String.class,
                    one=@One(select = "cn.yinjiahui.mapper.UserMapper.getAvatarUrlByUserId")),
            @Result(column = "id", property = "replies", javaType = List.class,
                    many = @Many(select = "cn.yinjiahui.mapper.LeaveMessageMapper.findChildLeaveMessage")),
    })
    List<LeaveMessage> findAllLeaveMessage(@Param("pageName") String pageName);

    @Select("select * from leave_message_record where pId=#{pId}")
    @Result(column = "answererId",property = "answererUsername",javaType = String.class,
            one=@One(select = "cn.yinjiahui.mapper.UserMapper.findUsernameById"))
    List<LeaveMessage> findChildLeaveMessage(@Param("pId") int pId);

    @Insert("insert into leave_message_record(pageName,pId,answererId,leaveMessageDate,leaveMessageContent) " +
            "values(#{pageName},#{pId},#{answererId},#{leaveMessageDate},#{leaveMessageContent})")
    void save(LeaveMessage leaveMessage);

    @Select("select id,pId,pageName,answererId,leaveMessageDate,leaveMessageContent from leave_message_record where answererId=#{answererId} ")
    List<LeaveMessage> getUserLeaveMessage(@Param("answererId") int answererId);

    @Select("select count(*) from leave_message_record")
    Integer getLeaveMessageNum();

    @Select("select pageName,answererId,leaveMessageDate,leaveMessageContent from leave_message_record ORDER BY id DESC")
    @Result(column = "answererId",property = "answererUsername",javaType = String.class,
            one=@One(select = "cn.yinjiahui.mapper.UserMapper.findUsernameById"))
    List<LeaveMessage> findNewLeaveWord();

}
