package cn.yinjiahui.controller;


import cn.yinjiahui.service.ArticleService;
import cn.yinjiahui.utils.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping(value = "/article")
    public DataMap getArticleById(@RequestParam("articleId") Integer articleId) {
        return articleService.getArticleById(articleId);
    }

    @GetMapping(value = "/myArticles")
    public DataMap myArticles(@RequestParam("rows") int rows,
                             @RequestParam("pageNum") int pageNum){
       return articleService.findAllArticles(pageNum,rows);
    }
}
