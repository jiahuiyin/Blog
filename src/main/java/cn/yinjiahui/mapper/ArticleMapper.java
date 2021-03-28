package cn.yinjiahui.mapper;

import cn.yinjiahui.pojo.Archive;
import cn.yinjiahui.pojo.Article;
import cn.yinjiahui.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ArticleMapper {


    @Select("select * from article where id=#{id}")
    Article getArticleById(@Param("id") int id);

    @Select("select articleTitle,articleTabloid from article where id=#{id}")
    Article getArticleTitleById(@Param("id") int id);

    @Select("SELECT DATE_FORMAT(publishDate, '%Y年%m月')AS `date` ,COUNT(*) FROM article " +
            "GROUP BY `date` ORDER BY `date` DESC")
    @Results({
            @Result(column = "date",property = "archiveDate"),
            @Result(column = "count(*)",property = "number")})
    List<Archive> getArchiveAndNum();


    @Select("select id,originalAuthor,articleTitle,articleTags,articleType,articleCategories,publishDate from article ORDER BY publishDate DESC")
    List<Article> findAllArticleByArchive();

    @Select("SELECT id,originalAuthor,articleTitle,articleTags,articleType,articleCategories,publishDate FROM article WHERE publishDate LIKE '${archive}%' ORDER BY publishDate DESC")
    List<Article> findArticleByArchive(@Param("archive") String archive);

    @Select("select id,originalAuthor,articleTags,articleTitle,articleType,publishDate,articleCategories,articleTabloid,likes from article order by publishDate desc")
    List<Article> findAllArticles();

    @Select("SELECT articleCategories ,COUNT(*) FROM article GROUP BY articleCategories")
    @Results({
            @Result(column = "articleCategories",property = "categoryName"),
            @Result(column = "count(*)",property = "categoryNum")})
    List<Category> findCategoryNameAndArticleNum();

    @Select("SELECT id,originalAuthor,articleTitle,articleTags,articleType,articleCategories,publishDate FROM article WHERE articleCategories=#{category}")
    List<Article> findArticleByCategory(@Param("category") String category);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO article(author,originalAuthor,articleTitle,articleContent,articleTags,articleType,articleCategories,publishDate,updateDate,articleUrl,articleTabloid,likes) " +
            "VALUES(#{author},#{originalAuthor},#{articleTitle},#{articleContent},#{articleTags},#{articleType},#{articleCategories},#{publishDate},#{updateDate},#{articleUrl},#{articleTabloid},#{likes})")
    void insertArticle(Article article);


    @Select("select id,originalAuthor,articleTitle,articleTags,articleType,articleCategories,publishDate from article where articleTags like '%${tag}%' ")
    List<Article> findArticleByTag(@Param("tag") String tag);


    @Select("select count(*) from article")
    Integer getArticleNum();

    @Select("select count(*) from (SELECT articleCategories  FROM article GROUP BY articleCategories)as t")
    Integer getCategoryNum();

    @Select("select id,originalAuthor,articleTitle,articleCategories,publishDate from article")
    List<Article> getArticleManagement();


    @Delete("delete from article where id=#{articleId}")
    void deleteByArticleId(int articleId);

    @Select("select originalAuthor,articleTitle,articleTags,articleType,publishDate,articleCategories,articleTabloid from article where id=#{articleId}")
    Article getArticleInfo(int articleId);

    @Select("select id,articleTitle,articleContent from article ")
    List<Article> getAllArticle();

    @Update("update article set originalAuthor=#{originalAuthor},articleTitle=#{articleTitle},updateDate=#{updateDate},articleContent=#{articleContent},articleTags=#{articleTags},articleType=#{articleType},articleCategories=#{articleCategories},articleUrl=#{articleUrl},articleTabloid=#{articleTabloid} where id=#{id}")
    void updateArticleById(Article article);


}
