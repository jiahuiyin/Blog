package cn.yinjiahui.controller;


import cn.yinjiahui.service.ArticleService;
import cn.yinjiahui.utils.DataMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SearchControl {


    @Autowired
    ArticleService articleService;

    @GetMapping("/query")
    public DataMap searchArticle(@RequestParam("keyword") String keyword,
                                 @RequestParam("rows") int pageSize,
                                 @RequestParam("pageNum") int pageNum){

        try {
            DataMap data = articleService.searchArticle(keyword,pageNum,pageSize);
            return data;
        } catch (Exception e){
            log.error("Get all feedback exception", e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }
}
