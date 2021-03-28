package cn.yinjiahui.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data

public class User implements Serializable {

    private Integer id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String gender;



    /**
     * 生日
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;


    /**
     * 邮箱
     */
    private String email;

    /**
     * 最后登录时间
     */
    private String recentlyLanded;

    /**
     * 头像地址
     */
    private String avatarImgUrl;


    private List<Role> roles;


    public String getAvatarImgUrl() {
        return avatarImgUrl;
    }
}
