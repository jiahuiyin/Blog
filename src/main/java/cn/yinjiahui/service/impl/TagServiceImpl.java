package cn.yinjiahui.service.impl;


import cn.yinjiahui.mapper.ArticleMapper;
import cn.yinjiahui.mapper.TagMapper;
import cn.yinjiahui.pojo.Article;
import cn.yinjiahui.service.TagService;
import cn.yinjiahui.utils.DataMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;


    @Autowired
    ArticleMapper articleMapper;
    @Override
    public void insertTags(String[] tags) {
        for(String tag : tags){
            Integer tagSize = tagMapper.findIsExistByTagName(tag);
            if(tagSize==null){
                tagMapper.insertTag(tag);
            }
        }
    }

    @Override
    public DataMap findTags() {
        List<String> tags = tagMapper.findTags();
        Map<String, Object> dataMap = new HashMap<>(2);
        dataMap.put("result",tags);
        dataMap.put("tagsNum",tags.size());
        return DataMap.success(DataMap.CodeType.FIND_TAGS_CLOUD).setData(dataMap);
    }

    @Override
    public DataMap findArticleByTag(String tag,int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Article> articles=articleMapper.findArticleByTag(tag);
        if(articles!=null&&articles.size()!=0){
            PageInfo pageInfo=new PageInfo(articles);
            Map<String ,Object> map=new HashMap<>();
            map.put("articles",articles);
            map.put("tag",tag);
            map.put("pageInfo",pageInfo);
            return DataMap.success(DataMap.CodeType.FIND_ARTICLE_BY_TAG).setData(map);
        }
        else
            return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);

    }

    @Override
    public int getTagNum() {
        return tagMapper.getTagNum();
    }
}
