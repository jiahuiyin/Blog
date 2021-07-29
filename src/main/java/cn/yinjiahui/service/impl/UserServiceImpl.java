package cn.yinjiahui.service.impl;



import cn.yinjiahui.mapper.UserMapper;
import cn.yinjiahui.pojo.User;
import cn.yinjiahui.service.UserService;
import cn.yinjiahui.utils.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByPhone(String phone) {
        return userMapper.findUserByPhone(phone);
    }

    @Override
    public String findUsernameByPhone(String phone) {
        return userMapper.findUsernameByPhone(phone);
    }


    @Override
    public String findUsernameById(int id) {
        return userMapper.findUsernameById(id);
    }

    @Override
    public String getAvatarUrlById(int id) {
        return userMapper.getAvatarUrlByUserId(id);
    }

    @Override
    public int findUserIdByPhone(String phone) throws Exception{
        Integer id=userMapper.findUserIdByPhone(phone);
        if(id==null)
            throw new Exception();
        return id;
    }

    @Override
    public void updateAvatarImgUrlByPhone(String phone,String url){
        userMapper.updateAvatarImgUrlByPhone(phone,url);
    }

    @Override
    public DataMap getUserPersonalInfoByPhone(String phone) {
        User user = userMapper.getUserPersonalInfoByPhone(phone);

        return DataMap.success().setData(user);
    }
    @Override
    public DataMap saveUserByPhone(User user, String phone) {
        userMapper.updateUserByPhone(user,phone);
        return getUserPersonalInfoByPhone(phone);
    }

    @Override
    public int getUserNum() {
        return userMapper.getUserNum();
    }

}
