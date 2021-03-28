package cn.yinjiahui.service;


import cn.yinjiahui.utils.DataMap;


public interface TagService {


    void insertTags(String[] tags);


    DataMap findTags();

    DataMap findArticleByTag(String tag,int pageNum, int pageSize);

    int getTagNum();
}
