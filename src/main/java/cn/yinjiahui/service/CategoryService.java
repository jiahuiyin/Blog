package cn.yinjiahui.service;

import cn.yinjiahui.utils.DataMap;


public interface CategoryService {


    DataMap findCategoriesNameAndArticleNum();

    DataMap findArticleByCategory(String category,int pageNum, int pageSize);

    DataMap findArticleByCategory(int pageNum, int pageSize);
}
