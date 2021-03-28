package cn.yinjiahui;

import cn.yinjiahui.mapper.ArticleMapper;
import cn.yinjiahui.mapper.UserMapper;
import cn.yinjiahui.pojo.Article;
import cn.yinjiahui.service.ESService;
import cn.yinjiahui.utils.ArticleUtils;
import org.junit.jupiter.api.Test;
import org.markdownj.MarkdownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    ESService esService;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UserMapper userMapper;


    //将数据库中文章导入es中
    @Test
    void articleToES() throws IOException {
        MarkdownProcessor markdownProcessor = new MarkdownProcessor();
        List<Article> articles = articleMapper.getAllArticle();
        for(Article article : articles){
            int id=article.getId();
            String title=article.getArticleTitle();
            String context=article.getArticleContent();
            String html = markdownProcessor.markdown(context);
            String text= ArticleUtils.htmlToText(html);
            esService.saveArticle(id,text,title);
        }
    }




    @Test
    void test1(){
    }

}
