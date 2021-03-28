package cn.yinjiahui.service.impl;

import cn.yinjiahui.mapper.LeaveMessageMapper;
import cn.yinjiahui.pojo.LeaveMessage;
import cn.yinjiahui.service.LeaveMessageService;
import cn.yinjiahui.service.UserService;
import cn.yinjiahui.utils.DataMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class LeaveMessageServiceImpl implements LeaveMessageService {

    @Autowired
    LeaveMessageMapper leaveMessageMapper;
    @Autowired
    UserService userService;

    @Override
    public DataMap findAllLeaveMessage(String pageName) {
        List<LeaveMessage> leaveMessages = leaveMessageMapper.findAllLeaveMessage(pageName);
        return DataMap.success().setData(leaveMessages);
    }

    @Override
    public void publishLeaveMessage(String leaveMessageContent, String pageName, String phone) throws Exception{
        LeaveMessage leaveMessage = new LeaveMessage();
        leaveMessage.setLeaveMessageDate(new Date());
        leaveMessage.setLeaveMessageContent(leaveMessageContent);
        leaveMessage.setPageName(pageName);
        leaveMessage.setAnswererId(userService.findUserIdByPhone(phone));

        leaveMessageMapper.save(leaveMessage);

    }

    @Override
    public void publishLeaveMessageReply(LeaveMessage leaveMessage,int pId, String phone) throws Exception{
        leaveMessage.setPId(pId);
        leaveMessage.setAnswererId(userService.findUserIdByPhone(phone));
        leaveMessage.setLeaveMessageDate(new Date());
        leaveMessageMapper.save(leaveMessage);
    }

    @Override
    public DataMap leaveMessageNewReply(LeaveMessage leaveMessage) {
        Map<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("answerer",userService.findUsernameById(leaveMessage.getAnswererId()));
        dataMap.put("leaveMessageContent",leaveMessage.getLeaveMessageContent());
        dataMap.put("leaveMessageDate",leaveMessage.getLeaveMessageDate());
        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap getUserLeaveMessage(int pageNum, int pageSize, String phone) throws Exception{

        int id = userService.findUserIdByPhone(phone);
        PageHelper.startPage(pageNum, pageSize);
        List<LeaveMessage> leaveMessages = leaveMessageMapper.getUserLeaveMessage(id);
        PageInfo<LeaveMessage> pageInfo = new PageInfo<>(leaveMessages);
        return DataMap.success().setData(pageInfo);
    }

    @Override
    public int getLeaveMessageNum() {
        Integer num=leaveMessageMapper.getLeaveMessageNum();
        if (num!=null)
            return num;
        return 0;
    }

    @Override
    public DataMap findNewLeaveMessage(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<LeaveMessage> fiveLeaveWords = leaveMessageMapper.findNewLeaveWord();
        PageInfo<LeaveMessage> pageInfo = new PageInfo<>(fiveLeaveWords);


        return DataMap.success().setData(pageInfo);
    }

    //    @Override
//    public DataMap updateLikeByPageNameAndId(String pageName, int id) {
//        leaveMessageMapper.updateLikeByPageNameAndId(pageName, id);
//        int likes = leaveMessageMapper.findLikesByPageNameAndId(pageName, id);
//        return DataMap.success().setData(likes);
//    }
//
//
//    @Override
//    public DataMap findFiveNewComment(int rows, int pageNum) {
//
//    }
//

//
//    @Override
//    public void readOneLeaveMessageRecord(int id) {
//        leaveMessageMapper.readOneLeaveMessageRecord(id);
//    }
//
//    @Override
//    public void readAllLeaveMessage(String username) {
//        int respondentId = userService.findIdByUsername(username);
//        leaveMessageMapper.readLeaveMessageRecordByRespondentId(respondentId);
//    }
//
//    /**
//     * 保存评论成功后往redis中增加一条未读评论数
//     */
//    private void addNotReadNews(LeaveMessage leaveMessage){
//        if(leaveMessage.getRespondentId() != leaveMessage.getAnswererId()){
//            Boolean isExistKey = hashRedisServiceImpl.hasKey(leaveMessage.getRespondentId()+ StringUtil.BLANK);
//            if(!isExistKey){
//                UserReadNews news = new UserReadNews(1,0,1);
//                hashRedisServiceImpl.put(String.valueOf(leaveMessage.getRespondentId()), news, UserReadNews.class);
//            } else {
//                hashRedisServiceImpl.hashIncrement(leaveMessage.getRespondentId()+ StringUtil.BLANK, "allNewsNum",1);
//                hashRedisServiceImpl.hashIncrement(leaveMessage.getRespondentId()+ StringUtil.BLANK, "leaveMessageNum",1);
//            }
//        }
//    }
}
