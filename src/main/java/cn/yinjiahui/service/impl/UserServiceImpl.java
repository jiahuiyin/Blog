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
//
//    @Override
//    public DataMap insert(User user) {
//
//        user.setUsername(user.getUsername().trim().replaceAll(" ", StringUtil.BLANK));
//        String username = user.getUsername();
//
//        if(username.length() > 35 || StringUtil.BLANK.equals(username)){
//            return DataMap.fail(CodeType.USERNAME_FORMAT_ERROR);
//        }
//        if(userIsExist(user.getPhone())){
//            return DataMap.fail(CodeType.PHONE_EXIST);
//        }
//        if("male".equals(user.getGender())){
//            user.setAvatarImgUrl("https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/user/avatar/noLogin_male.jpg");
//        } else {
//            user.setAvatarImgUrl("https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/user/avatar/noLogin_female.jpg");
//        }
//        userMapper.save(user);
//        int userId = userMapper.findUserIdByPhone(user.getPhone());
//        insertRole(userId, RoleConstant.ROLE_USER);
//        return DataMap.success();
//    }
//

//
//    @Override
//    public void updatePasswordByPhone(String phone, String password) {
//        userMapper.updatePassword(phone, password);
////        密码修改成功后注销当前用户
//        SecurityContextHolder.getContext().setAuthentication(null);
//    }
//
//    @Override
//    public String findPhoneByUsername(String username) {
//        return userMapper.findPhoneByUsername(username);
//    }
//
//    @Override
//    public int findIdByUsername(String username) {
//        return userMapper.findIdByUsername(username);
//    }
//
//    @Override
//    public User findUsernameByPhone(String phone) {
//        return userMapper.findUsernameByPhone(phone);
//    }
//
//    @Override
//    public void updateRecentlyLanded(String username, String recentlyLanded) {
//        String phone = userMapper.findPhoneByUsername(username);
//        userMapper.updateRecentlyLanded(phone, recentlyLanded);
//    }
//
//    @Override
//    public boolean usernameIsExist(String username) {
//        User user = userMapper.findUsernameByUsername(username);
//        return user != null;
//    }
//
//    @Override
//    public boolean isSuperAdmin(String phone) {
//        int userId = userMapper.findUserIdByPhone(phone);
//        List<Object> roleIds = userMapper.findRoleIdByUserId(userId);
//
//        for(Object i : roleIds){
//            if((int)i == 3){
//                return true;
//            }
//        }
//        return false;
//    }
//

//

//

//

//

//

//
//    /**
//     * 增加用户权限
//     * @param userId 用户id
//     * @param roleId 权限id
//     */
//    private void insertRole(int userId, int roleId) {
//        userMapper.saveRole(userId, roleId);
//    }
//
//    /**
//     * 通过手机号判断用户是否存在
//     * @param phone 手机号
//     * @return true--存在  false--不存在
//     */
//    private boolean userIsExist(String phone){
//        User user = userMapper.findUserByPhone(phone);
//        return user != null;
//    }
}
