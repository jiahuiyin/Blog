package cn.yinjiahui.service;


import cn.yinjiahui.pojo.FeedBack;
import cn.yinjiahui.utils.DataMap;


public interface FeedBackService {


    void submitFeedback(FeedBack feedBack);


    DataMap getAllFeedback(int pageNum, int pageSize);

}
