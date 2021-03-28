package cn.yinjiahui.service;

import cn.yinjiahui.utils.DataMap;

public interface ArchiveService {
    DataMap getArchiveAndNum();

    DataMap findArticleByArchive(String archiveDate, int pageNum, int pageSize);

    DataMap findArticleByArchive(int pageNum, int pageSize);


}
