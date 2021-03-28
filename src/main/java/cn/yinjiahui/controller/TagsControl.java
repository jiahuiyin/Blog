package cn.yinjiahui.controller;


import cn.yinjiahui.service.TagService;
import cn.yinjiahui.utils.DataMap;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/tag")
public class TagsControl {

    @Autowired
    TagService tagService;

    @PostMapping(value = "/article")
    public DataMap getTagArticle(@RequestParam String tag,
                                 @RequestParam("rows") int pageSize,
                                 @RequestParam("pageNum") int pageNum) {
        if (StringUtil.isEmpty(tag)) {
            return tagService.findTags();
        } else {
            return tagService.findArticleByTag(tag, pageNum, pageSize);
        }

    }
}
