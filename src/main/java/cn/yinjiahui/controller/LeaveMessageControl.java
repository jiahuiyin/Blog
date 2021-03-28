package cn.yinjiahui.controller;


import cn.yinjiahui.pojo.LeaveMessage;
import cn.yinjiahui.service.LeaveMessageService;
import cn.yinjiahui.utils.DataMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Slf4j

@RestController
public class LeaveMessageControl {

    @Autowired
    LeaveMessageService leaveMessageService;


    @PostMapping(value = "/user/leaveMessage/publish")
    public DataMap publishLeaveMessage(@RequestParam("leaveMessageContent") String leaveMessageContent,
                                       @RequestParam("pageName") String pageName,
                                       Principal principal) {
        try {
            String phone = principal.getName();
            leaveMessageService.publishLeaveMessage(leaveMessageContent, pageName, phone);
            DataMap data = leaveMessageService.findAllLeaveMessage(pageName);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }



    @GetMapping(value = "/leaveMessage/page")
    public DataMap getPageLeaveMessage(@RequestParam("pageName") String pageName) {
        return leaveMessageService.findAllLeaveMessage(pageName);
    }


    @PostMapping(value = "/user/leaveMessage/reply")
    public DataMap publishLeaveMessageReply(LeaveMessage leaveMessage,
                                            @RequestParam("parentId") Integer parentId,
                                            Principal principal){

        try {
            String phone = principal.getName();



            leaveMessageService.publishLeaveMessageReply(leaveMessage, parentId,phone);

            DataMap data = leaveMessageService.leaveMessageNewReply(leaveMessage);
            return data;
        } catch (Exception e){
            e.printStackTrace();
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }

}
