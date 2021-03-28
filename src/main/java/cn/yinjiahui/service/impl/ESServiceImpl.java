package cn.yinjiahui.service.impl;

import cn.yinjiahui.pojo.ArticleInfo;
import cn.yinjiahui.service.ESService;
import cn.yinjiahui.utils.ArticleUtils;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ESServiceImpl implements ESService {

    private static final String INDEX="article";

    @Autowired
    RestHighLevelClient client;

    @Override
    public boolean saveArticle(int id, String content,String title) throws IOException {
        Map<String,String> map=new HashMap<>(2);
        content= ArticleUtils.markdownToText(content);
        map.put("context",content);
        map.put("title",title);
        IndexRequest request=new IndexRequest(INDEX);
        request.id(String.valueOf(id));
        request.timeout(TimeValue.timeValueSeconds(1));
        request.source(JSON.toJSONString(map), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        if (response.getShardInfo().getSuccessful() != 1)
            return false;
        return true;
    }

    @Override
    public List<ArticleInfo> findArticle(String keyword, int pageNum, int pageSize) throws IOException {
        List<ArticleInfo> articleInfos=new LinkedList<>();


        pageNum--;
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();//构造搜索对象
        //MatchQueryBuilder matchQueryBuilder=new MatchQueryBuilder("context",keyword);

        searchSourceBuilder.size(pageSize);
        searchSourceBuilder.from(pageNum);
        MultiMatchQueryBuilder multiMatchQueryBuilder =
                new MultiMatchQueryBuilder(keyword,"title","context");



        searchSourceBuilder.query(multiMatchQueryBuilder);//设置查询条件
        //设置高亮
        String preTags = "<span style='color:red'>";
        String postTags = "</span>";
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags(preTags);//设置前缀
        highlightBuilder.postTags(postTags);//设置后缀
        highlightBuilder.field("context");//设置高亮字段
        highlightBuilder.field("title");
        searchSourceBuilder.highlighter(highlightBuilder);//设置高亮信息
        SearchRequest searchRequest = new SearchRequest(INDEX);//创建查询请求对象
        searchRequest.source(searchSourceBuilder);//设置searchSourceBuilder

        SearchResponse response = client.search(searchRequest,RequestOptions.DEFAULT);

        SearchHit[] hits = response.getHits().getHits();

        for (SearchHit hit : hits) {
            ArticleInfo articleInfo=new ArticleInfo();
            articleInfo.setId(Integer.valueOf(hit.getId()));

            Map<String, HighlightField> highlightFields=hit.getHighlightFields();

            HighlightField title = highlightFields.get("title");

            if(title!=null){
                articleInfo.setTitle(title.fragments()[0].string());
            }

            HighlightField context=highlightFields.get("context");
            if(context!=null){
                Text[] fragments = context.fragments();
                String contextString=textToString(fragments);
                articleInfo.setContext(contextString);
            }
            articleInfos.add(articleInfo);
        }

        return articleInfos;
    }

    private static String textToString(Text[] texts){
        StringBuilder string=new StringBuilder();
        for(Text text:texts){
            string.append(text.string());
            string.append("...");
            if(string.length()>250)
                break;
        }
        return string.toString();
    }


    public void deleteArticle(int id) {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX, Integer.toString(id));
        DeleteResponse response = null;
        try {
            response = client.delete(deleteRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {

            e.printStackTrace();
        }
        System.out.println(response);

    }
}
