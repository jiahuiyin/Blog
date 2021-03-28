package cn.yinjiahui.controller;


import cn.yinjiahui.service.ArticleService;
import cn.yinjiahui.service.FeedBackService;
import cn.yinjiahui.service.UserService;
import cn.yinjiahui.service.VisitorService;
import cn.yinjiahui.utils.DataMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("/superAdmin")
public class SuperAdminControl {


    @Autowired
    FeedBackService feedBackService;

    @Autowired
    VisitorService visitorService;

    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;


    @GetMapping(value = "/allFeedback")
    public DataMap getAllFeedback(@RequestParam("rows") int pageSize,
                                  @RequestParam("pageNum") int pageNum){
        try {
            DataMap data = feedBackService.getAllFeedback( pageNum, pageSize);
            return data;
        } catch (Exception e){
            log.error("Get all feedback exception", e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }

    /**
     * 获得统计信息
     * @return
     */
    @GetMapping(value = "/statisticsInfo")
    public DataMap getStatisticsInfo(){
        try {
            Map<String, Object> dataMap = new HashMap<>(8);
            int totalVisitor = visitorService.getVisitorNum();
            dataMap.put("allVisitor", totalVisitor);
            dataMap.put("allUser", userService.getUserNum());
            dataMap.put("articleNum", articleService.getArticleNum());
            DataMap data = DataMap.success().setData(dataMap);
            return data;
        } catch (Exception e){
            log.error("Get statistics info exception", e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }


    @GetMapping(value = "/articleManagement")
    public DataMap getArticleManagement(@RequestParam("rows") int pageSize,
                                        @RequestParam("pageNum") int pageNum){
        try {
            DataMap data = articleService.getArticleManagement(pageNum, pageSize);
            return data;
        } catch (Exception e){

            log.error("Get article management exception", e);
        }
        return null;

    }


    @GetMapping(value = "/deleteArticle")
    public DataMap deleteArticle(@RequestParam("id") Integer id){
        try {
            if( id != null) {
                DataMap data = articleService.deleteArticle(id);
                return data;
            }
        } catch (Exception e){
            log.error("Delete article [{}] exception", id, e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }

}
