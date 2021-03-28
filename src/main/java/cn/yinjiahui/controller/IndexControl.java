package cn.yinjiahui.controller;


import cn.yinjiahui.pojo.FeedBack;
import cn.yinjiahui.service.*;
import cn.yinjiahui.utils.DataMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RestController
@Slf4j
public class IndexControl {

    @Autowired
    VisitorService visitorService;

    @Autowired
    ArticleService articleService;

    @Autowired
    TagService tagService;

    @Autowired
    UserService userService;

    @Autowired
    FeedBackService feedBackService;

    @Autowired
    LeaveMessageService leaveMessageService;
    /**
     * 增加访客量
     * @return  网站总访问量以及访客量
     */
    @GetMapping(value = "/visitorNum")
    public DataMap getVisitorNumByPageName(HttpSession session){
        try {
            String visited=(String)session.getAttribute("visited");
            boolean addVisit=false;
            if(visited==null) {
                session.setAttribute("visited", "1");
                addVisit=true;
            }
            DataMap data = visitorService.getVisitorNum(addVisit);
            return data;
        } catch (Exception e){
            log.error("pageName [{}] get visitor num exception", e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }


    @GetMapping(value = "/findArchivesCategoriesTagsNum")
    public DataMap findArchivesCategoriesTagsNum(){
        try {
            Map<String, Integer> dataMap = new HashMap<>(4);
            dataMap.put("tagsNum", tagService.getTagNum());
            dataMap.put("categoriesNum",articleService.getCategoryNum());
            dataMap.put("archivesNum", articleService.getArticleNum());
            return DataMap.success().setData(dataMap);
        } catch (Exception e){
            log.error("Get archives categories and tags num exception", e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }

    @PostMapping(value = "/submitFeedback")
    public DataMap submitFeedback(FeedBack feedBack,
                                  Principal principal){
        String phone = principal.getName();
        try {
            feedBack.setPersonId(userService.findUserIdByPhone(phone));
            feedBackService.submitFeedback(feedBack);
            return DataMap.success();
        } catch (Exception e){
            log.error("[{}] submit feedback [{}] exception", feedBack, e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);

    }


    @GetMapping(value = "/siteInfo")
    public DataMap getSiteInfo(){
        try {
            Map<String, Integer> dataMap = new HashMap<>(4);
            dataMap.put("tagsNum", tagService.getTagNum());
            dataMap.put("categoriesNum",articleService.getCategoryNum());
            dataMap.put("archivesNum", articleService.getArticleNum());
            dataMap.put("leaveWordNum", leaveMessageService.getLeaveMessageNum());
            return DataMap.success().setData(dataMap);
        } catch (Exception e){
            log.error("Get web site info exception", e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);

    }

    @GetMapping(value = "/newLeaveWord")
    public DataMap newLeaveWord(@RequestParam("rows") int pageSize,
                               @RequestParam("pageNum") int pageNum){
        try {
            DataMap data = leaveMessageService.findNewLeaveMessage(pageNum, pageSize);
            return data;
        } catch (Exception e){
            log.error("Get new leaveword exception", e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);

    }
}
