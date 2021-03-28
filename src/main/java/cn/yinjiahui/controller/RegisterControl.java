package cn.yinjiahui.controller;


import cn.yinjiahui.pojo.User;
import cn.yinjiahui.service.RegisterService;
import cn.yinjiahui.service.UserService;
import cn.yinjiahui.utils.DataMap;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/register")
@Slf4j
public class RegisterControl {

    @Autowired
    UserService userService;
    @Autowired
    RegisterService registerService;



    @PostMapping(value = "/validation_codes")
    public void sendCodes(String phone)  {

        try {
            registerService.sendCode(phone);
        } catch (TencentCloudSDKException e) {
            log.error("User [{}] register exception", e);
        }
    }

    @PostMapping(value = "/register")
    public DataMap register(User user, String authCode){
        try {
            return registerService.register(user,authCode);

        } catch (Exception e){
            log.error("User [{}] register exception", user, e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }






}
