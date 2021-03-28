package cn.yinjiahui.mapper;

import cn.yinjiahui.pojo.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {

    @Select("SELECT NAME FROM ROLE WHERE id IN(SELECT Role_id FROM user_role WHERE User_id=#{id})")
    List<Role> getRoleNameByUserId(@Param("id") String id);

    @Insert("insert into user_role(User_id,Role_id) values(#{userId},1)")
    void insertUserRole(int userId);

}
