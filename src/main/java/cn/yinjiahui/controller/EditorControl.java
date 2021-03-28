package cn.yinjiahui.controller;

import cn.yinjiahui.pojo.Article;
import cn.yinjiahui.service.ArticleService;
import cn.yinjiahui.service.TagService;
import cn.yinjiahui.service.UserService;
import cn.yinjiahui.utils.DataMap;
import cn.yinjiahui.utils.TabloidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;


@RestController
@Slf4j
@RequestMapping("/editor")
public class EditorControl {

    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;

    /**
     * 发表博客
     * @param principal 当前登录用户
     * @param article 文章
     * @return
     */
    @PostMapping (value = "/article")
    public DataMap publishArticle(Principal principal,
                                  Article article,
                                  String articleHtmlContent
                                 ) {
        try {
            tagService.insertTags(article.getArticleTagArray());
            String articleTabloid = TabloidUtil.buildTabloid(articleHtmlContent);
            article.setArticleTabloid(articleTabloid + "...");

            String username = userService.findUsernameByPhone(principal.getName());
            if(article.getId()!=null){
                article.setUpdateDate(new Date());
                DataMap data = articleService.updateArticleById(article);
                return data;
            }
            article.setAuthor(username);


            DataMap data = articleService.insertArticle(article);

            return data;
        } catch (Exception e){
            log.error("Get draft article exception", e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);


    }


    /**
     * 获得是否有未发布的草稿文章或是修改文章
     */
    @GetMapping(value = "/draftArticle")
    public DataMap getDraftArticle(Integer id){
        try {
            //判断是否为修改文章
            if(id != null){
                DataMap data = articleService.getArticleById(id);
                return data;
            }

            return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
        } catch (Exception e){
            log.error("Get draft article exception", e);
        }
        return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }

}
