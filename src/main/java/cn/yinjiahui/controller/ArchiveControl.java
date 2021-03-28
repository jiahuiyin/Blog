package cn.yinjiahui.controller;


import cn.yinjiahui.service.ArchiveService;
import cn.yinjiahui.utils.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/archive")
public class ArchiveControl {

    @Autowired
    ArchiveService archiveService;




    @GetMapping(value = "/num")
    public DataMap findArchiveAndNum(){
        return archiveService.getArchiveAndNum();
    }



    @GetMapping(value = "/article")
    public DataMap getArchiveArticle(@RequestParam("archive") String archive,
                                        @RequestParam("rows") int pageSize,
                                        @RequestParam("pageNum") int pageNum){
        if("".equals(archive))
            return archiveService.findArticleByArchive(pageNum,pageSize);
        else
            return archiveService.findArticleByArchive(archive,pageNum,pageSize);
    }
}
