package cn.yinjiahui.controller;

import cn.yinjiahui.service.ArchiveService;
import cn.yinjiahui.service.CategoryService;
import cn.yinjiahui.utils.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/category")
public class CategoriesControl {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ArchiveService archiveService;

    @GetMapping(value = "/name_articleNum")
    public DataMap findCategoriesNameAndArticleNum(){
        return categoryService.findCategoriesNameAndArticleNum();
    }

    /**
     * 分页获得该分类下的文章
     * @return
     */
    @GetMapping(value = "/article")
    public DataMap getCategoryArticle(@RequestParam("category") String category,
                                     @RequestParam("rows") int pageSize,
                                     @RequestParam("pageNum") int pageNum){
        if("".equals(category)) {
            return categoryService.findArticleByCategory(pageNum,pageSize);
        } else {
            return categoryService.findArticleByCategory(category,pageNum,pageSize);
        }

    }
}
