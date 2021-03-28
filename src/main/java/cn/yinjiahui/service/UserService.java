package cn.yinjiahui.service;


import cn.yinjiahui.pojo.User;
import cn.yinjiahui.utils.DataMap;


public interface UserService {


    User findUserByPhone(String phone);

    String findUsernameByPhone(String phone);

    int findUserIdByPhone(String phone) throws Exception;


    String findUsernameById(int id);

    String getAvatarUrlById(int id);

    void updateAvatarImgUrlByPhone(String phone,String url);

    DataMap getUserPersonalInfoByPhone(String phone);

    DataMap saveUserByPhone(User user, String phone);

    int getUserNum();


}
