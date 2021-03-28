package cn.yinjiahui.service.impl;

import cn.yinjiahui.mapper.RoleMapper;
import cn.yinjiahui.mapper.UserMapper;
import cn.yinjiahui.pojo.User;
import cn.yinjiahui.service.RegisterService;
import cn.yinjiahui.utils.DataMap;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${yjh.secretId}")
    private static String secretId;
    @Value("${yjh.secretKey}")
    private static String secretKey;
    @Value("${yjh.templateID}")
    private static String templateID;
    @Value("${yjh.smsSdkAppid}")
    private static String smsSdkAppid;
    @Value("${yjh.sign}")
    private static String sign;


    @Override
    public void sendCode(String phone)throws TencentCloudSDKException{
        String code = String.format("%04d",new Random().nextInt(9999));
        redisTemplate.opsForValue().set(phone,code);
        redisTemplate.expire(phone,300,TimeUnit.SECONDS);
        sendSmsResponse(phone,code);
    }

    private void sendSmsResponse(String phoneNumber, String code) throws TencentCloudSDKException {

        phoneNumber = "+86" + phoneNumber;
        Credential cred = new Credential(secretId, secretKey);

        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        SmsClient client = new SmsClient(cred, "", clientProfile);

        SendSmsRequest req = new SendSmsRequest();
        String[] phoneNumberSet1 = {phoneNumber};
        req.setPhoneNumberSet(phoneNumberSet1);
        String[] templateParamSet1 = {code};
        req.setTemplateParamSet(templateParamSet1);
        req.setTemplateID(templateID);
        req.setSmsSdkAppid(smsSdkAppid);
        req.setSign(sign);
        client.SendSms(req);
    }


    @Override
    public DataMap register(User user, String authCode) {

        try {
            String code = (String) redisTemplate.opsForValue().get(user.getPhone());
            if (!authCode.equals(code))
                return DataMap.fail(DataMap.CodeType.AUTH_CODE_ERROR);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userMapper.insertUser(user);
            roleMapper.insertUserRole(user.getId());
            return DataMap.success();
        }catch (DuplicateKeyException e){
            return DataMap.fail(DataMap.CodeType.PHONE_EXIST);
        }

    }



}
