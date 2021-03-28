package cn.yinjiahui.service;


import cn.yinjiahui.pojo.LeaveMessage;
import cn.yinjiahui.utils.DataMap;


public interface LeaveMessageService {



    void publishLeaveMessageReply(LeaveMessage leaveMessage, int pId, String phone)throws Exception;


    DataMap leaveMessageNewReply(LeaveMessage leaveMessage);


    DataMap findAllLeaveMessage(String pageName);

    void publishLeaveMessage(String leaveMessageContent, String pageName, String phone)throws Exception;

    DataMap getUserLeaveMessage(int pageNum, int pageSize, String phone)throws Exception;

    int getLeaveMessageNum();

    DataMap findNewLeaveMessage(int pageNum, int pageSize);

}
