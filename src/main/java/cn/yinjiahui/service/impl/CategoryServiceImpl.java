package cn.yinjiahui.service.impl;


import cn.yinjiahui.mapper.ArticleMapper;
import cn.yinjiahui.pojo.Article;
import cn.yinjiahui.pojo.Category;
import cn.yinjiahui.service.CategoryService;
import cn.yinjiahui.utils.DataMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    ArticleMapper articleMapper;


    @Override
    public DataMap findCategoriesNameAndArticleNum() {
        List<Category> categories = articleMapper.findCategoryNameAndArticleNum();
        return DataMap.success().setData(categories);
    }

    @Override
    public DataMap findArticleByCategory(String category, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Article> articles=articleMapper.findArticleByCategory(category);
        if(articles.size()!=0){
            PageInfo pageInfo=new PageInfo(articles);
            Map<String ,Object> map=new HashMap<>();

            map.put("category",category);
            map.put("pageInfo",pageInfo);
            return DataMap.success().setData(map);
        }
        else
            return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }

    @Override
    public DataMap findArticleByCategory(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Article> articles  =articleMapper.findAllArticleByArchive();
        if(articles.size()!=0){
            PageInfo pageInfo=new PageInfo(articles);
            Map<String ,Object> map=new HashMap<>();
            map.put("category","全部分类");
            map.put("pageInfo",pageInfo);
            return DataMap.success().setData(map);
        }
        else
            return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }
}
