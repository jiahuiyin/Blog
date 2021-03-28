package cn.yinjiahui.service;

import cn.yinjiahui.pojo.ArticleInfo;

import java.io.IOException;
import java.util.List;

public interface ESService {

    void deleteArticle(int id);
    boolean saveArticle(int id,String content,String title) throws IOException;

    List<ArticleInfo> findArticle(String keyword, int pageNum, int pageSize) throws IOException;
}
