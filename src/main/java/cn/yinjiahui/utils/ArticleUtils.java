package cn.yinjiahui.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.markdownj.MarkdownProcessor;

public class ArticleUtils {

    public static String buildTabloid(String htmlArticle) {
        Document document = Jsoup.parse(htmlArticle);
        String text = document.text();
        if (text.length() > 197)
            return text.substring(0, 197);
        return text;
    }


    public static String htmlToText(String htmlArticle) {
        Document document = Jsoup.parse(htmlArticle);
        return document.text();
    }

    public static String markdownToText(String markdown) {
        MarkdownProcessor markdownProcessor = new MarkdownProcessor();

        String html = markdownProcessor.markdown(markdown);
        return ArticleUtils.htmlToText(html);

    }
}

