package cn.yinjiahui.service;


import cn.yinjiahui.pojo.Article;
import cn.yinjiahui.utils.DataMap;

public interface ArticleService {

    DataMap getArticleById(int id);

    Article getArticleTitleById(int id);

    DataMap findAllArticles(int pageNum, int pageSize);

    DataMap insertArticle(Article article)throws Exception;

    int getArticleNum();

    int getCategoryNum();

    DataMap getArticleManagement(int pageNum, int pageSize);

    DataMap deleteArticle(int id);

    DataMap searchArticle(String keyword,int pageNum, int pageSize)throws Exception;

    DataMap updateArticleById(Article article)throws Exception;
}
