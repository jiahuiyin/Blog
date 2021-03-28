package cn.yinjiahui.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Article implements Serializable {

    private Integer id;

    private String author;

    private String originalAuthor;

    private String articleTitle;

    private Date publishDate;

    private Date updateDate;

    private String articleContent;

    private String articleTags;

    private String articleType;

    private String articleCategories;

    private String articleUrl;

    private String articleTabloid;

    private Integer likes;

    public String[] getArticleTagArray() {
        if(articleTags!=null)
            return articleTags.split(",");
        return null;
    }

    public void setArticleTagArray(String[] tags) {

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : tags) {
            if (stringBuilder.length() == 0) {
                stringBuilder.append(s.trim());
            } else {
                stringBuilder.append(",").append(s.trim());
            }
        }
        this.articleTags=stringBuilder.toString();
    }
}
