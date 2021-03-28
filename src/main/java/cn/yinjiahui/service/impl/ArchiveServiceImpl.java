package cn.yinjiahui.service.impl;

import cn.yinjiahui.mapper.ArticleMapper;
import cn.yinjiahui.pojo.Archive;
import cn.yinjiahui.pojo.Article;
import cn.yinjiahui.service.ArchiveService;
import cn.yinjiahui.utils.DataMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    ArticleMapper articleMapper;



    @Override
    public DataMap getArchiveAndNum() {
        List<Archive> archives = articleMapper.getArchiveAndNum();
        if(archives.size()!=0)
            return DataMap.success().setData(archives);
        else
            return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);

    }

    @Override
    public DataMap findArticleByArchive(String archiveDate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        //Date为2021年02月格式
        StringBuilder s = new StringBuilder();
        s.append(archiveDate.substring(0,4));
        s.append("-");
        s.append(archiveDate.substring(5,7));
        List<Article> articles = articleMapper.findArticleByArchive(s.toString());
        if(articles.size()!=0){
            PageInfo pageInfo=new PageInfo(articles);
            return DataMap.success().setData(pageInfo);
        }
        else
            return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }

    @Override
    public DataMap findArticleByArchive(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Article> articles  =articleMapper.findAllArticleByArchive();
        if(articles.size()!=0){
            PageInfo pageInfo=new PageInfo(articles);
            return DataMap.success().setData(pageInfo);
        }
        else
            return DataMap.fail(DataMap.CodeType.SERVER_EXCEPTION);
    }


}
