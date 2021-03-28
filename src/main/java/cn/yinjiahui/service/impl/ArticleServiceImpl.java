package cn.yinjiahui.service.impl;

import cn.yinjiahui.mapper.ArticleMapper;
import cn.yinjiahui.pojo.Article;
import cn.yinjiahui.pojo.ArticleInfo;
import cn.yinjiahui.service.ArticleService;
import cn.yinjiahui.service.ESService;
import cn.yinjiahui.utils.DataMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ESService esService;

    @Override
    public DataMap getArticleById(int id) {
        Article article=articleMapper.getArticleById(id);
        if(article!=null) {
            return DataMap.success().setData(article);
        } else {
            return  DataMap.fail(DataMap.CodeType.ARTICLE_NOT_EXIST);
        }
    }

    @Override
    public Article getArticleTitleById(int id) {
        return articleMapper.getArticleTitleById(id);
    }

    @Override
    public DataMap findAllArticles(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Article> articles = articleMapper.findAllArticles();
        PageInfo pageInfo = new PageInfo(articles);
        return DataMap.success().setData(pageInfo);
    }

    @Override
    public DataMap insertArticle(Article article) throws Exception{
        if(StringUtil.isEmpty(article.getOriginalAuthor())){
            article.setOriginalAuthor(article.getAuthor());
        }
        article.setPublishDate(new Date());
        article.setUpdateDate(new Date());
        articleMapper.insertArticle(article);
        esService.saveArticle(article.getId(),article.getArticleContent(),article.getArticleTitle());





        Map<String,Object> dataMap=new HashMap<>();

        dataMap.put("articleTitle",article.getArticleTitle());
        dataMap.put("updateDate",article.getUpdateDate());
        dataMap.put("author",article.getOriginalAuthor());
        dataMap.put("articleUrl","/article/" + article.getId());
        return DataMap.success().setData(dataMap);
    }

    @Override
    public int getArticleNum() {
        return articleMapper.getArticleNum();
    }

    @Override
    public int getCategoryNum(){
        return articleMapper.getCategoryNum();
    }


    @Override
    public DataMap getArticleManagement(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.getArticleManagement();
        PageInfo pageInfo = new PageInfo(articles);
        return DataMap.success().setData(pageInfo);
    }

    @Override
    public DataMap deleteArticle(int id) {
        articleMapper.deleteByArticleId(id);
        esService.deleteArticle(id);
        return DataMap.success();
    }

    @Override
    public DataMap searchArticle(String keyword,int pageNum, int pageSize)throws Exception {

        List<ArticleInfo> articleInfos = esService.findArticle(keyword,pageNum,pageSize);
        List<Article> articles=new LinkedList<>();
        for (ArticleInfo articleInfo:articleInfos){
            Article article = articleMapper.getArticleInfo(articleInfo.getId());
            article.setId(articleInfo.getId());
            if(articleInfo.getTitle()!=null)
                article.setArticleTitle(articleInfo.getTitle());
            if(articleInfo.getContext()!=null)
                article.setArticleTabloid(articleInfo.getContext());
            articles.add(article);
        }
        if(articleInfos.size()==0)
            return DataMap.fail(DataMap.CodeType.NO_RESULTS_FOUND);

        return DataMap.success().setData(articles);
    }

    @Override
    public DataMap updateArticleById(Article article) throws Exception{
        esService.saveArticle(article.getId(),article.getArticleContent(),article.getArticleTitle());
        articleMapper.updateArticleById(article);
        Map<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("articleTitle",article.getArticleTitle());
        dataMap.put("updateDate",article.getUpdateDate());
        dataMap.put("author",article.getOriginalAuthor());
        dataMap.put("articleUrl","/article/" + article.getId());
        return DataMap.success().setData(dataMap);
    }
}
