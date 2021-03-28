package cn.yinjiahui.service;

import cn.yinjiahui.pojo.User;
import cn.yinjiahui.utils.DataMap;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

public interface RegisterService {

    void sendCode(String phone) throws TencentCloudSDKException;


    DataMap register(User user, String authCode);
}
