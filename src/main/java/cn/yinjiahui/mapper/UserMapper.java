package cn.yinjiahui.mapper;


import cn.yinjiahui.pojo.User;
import org.apache.ibatis.annotations.*;


import java.util.List;


public interface UserMapper {

    @Select("select * from user where phone=#{phone}")
    @Results({

            @Result(column = "id", property = "roles", javaType = List.class, many = @Many(select = "com.yin.mapper.RoleMapper.getRoleNameByUserId")),
    })
    User findUserByPhone(@Param("phone") String phone);

    @Select("SELECT username FROM USER WHERE phone=#{phone}")
    String findUsernameByPhone(@Param("phone")String phone);

    @Select("select username from user where id=#{id}")
    String findUsernameById(@Param("id")int id);

    @Select("select id from user where phone=#{phone}")
    Integer findUserIdByPhone(@Param("phone") String phone);

    @Select("select avatarImgUrl from user where id=#{id}")
    String getAvatarUrlByUserId(@Param("id") int id);

    @Update("update user set avatarImgUrl=#{url} where phone=#{phone}")
    void updateAvatarImgUrlByPhone(@Param("phone") String phone,@Param("url") String url);

    @Select("select username,gender,birthday,email,avatarImgUrl,phone from user where phone=#{phone}")
    User getUserPersonalInfoByPhone(@Param("phone") String phone);


    @Update("update user set username=#{user.username},gender=#{user.gender},birthday=#{user.birthday},email=#{user.email} where phone=#{phone}")
    void updateUserByPhone(@Param("user") User user, @Param("phone") String phone);

    @Select("select count(*) from user")
    int getUserNum();

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into user(phone,username,password,gender) values(#{phone},#{username},#{password},#{gender})")
    void insertUser(User user);

}
