package cn.yinjiahui.controller;


import cn.yinjiahui.pojo.User;
import cn.yinjiahui.service.LeaveMessageService;
import cn.yinjiahui.service.UserService;
import cn.yinjiahui.utils.DataMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
import java.util.UUID;


@RestController
@Slf4j
@RequestMapping("/user")
public class UserControl {

    @Value("${yjh.imgpath}")
    private String imgPath;

    @Autowired
    UserService userService;

    @Autowired
    LeaveMessageService leaveMessageService;



    @PostMapping(value = "/uploadHead")
    public DataMap uploadHead(@RequestParam("file")MultipartFile file,
                              Principal principal)throws Exception{
        String phone =principal.getName();
        String filePath =imgPath;
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = "/"+UUID.randomUUID()+suffixName;
        file.transferTo(new File(filePath+fileName));
        userService.updateAvatarImgUrlByPhone(phone,fileName);
        String url="/"+fileName;
        return DataMap.success().setData(url);
    }


    @GetMapping(value = "/userPersonalInfo")
    public DataMap getUserPersonalInfo(Principal principal){
        String phone = principal.getName();
        DataMap data=null;
        try {
            data = userService.getUserPersonalInfoByPhone(phone);
            return data;
        } catch (Exception e){
            log.error("[{}] get user personal info exception", phone, e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }


    @PostMapping(value = "/savePersonalDate")
    public DataMap savePersonalDate(User user,
                                    Principal principal){

        String username = principal.getName();
        try {
            DataMap data = userService.saveUserByPhone(user, username);
            return data;
        } catch (Exception e){
            log.error("[{}] save user info [{}] exception", username, user, e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }


    @GetMapping(value = "/userLeaveWord")
    public DataMap getUserLeaveMessage(@RequestParam("rows") int rows,
                                      @RequestParam("pageNum") int pageNum,
                                      Principal principal){
        String username = principal.getName();
        try {
            DataMap data = leaveMessageService.getUserLeaveMessage(pageNum, rows, username);
            return data;
        } catch (Exception e){
            log.error("[{}] get leaveWord exception", username, e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }
}
