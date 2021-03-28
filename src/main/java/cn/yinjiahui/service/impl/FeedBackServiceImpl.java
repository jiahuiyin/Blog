package cn.yinjiahui.service.impl;

import cn.yinjiahui.mapper.FeedBackMapper;
import cn.yinjiahui.pojo.FeedBack;
import cn.yinjiahui.service.FeedBackService;
import cn.yinjiahui.service.UserService;
import cn.yinjiahui.utils.DataMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    FeedBackMapper feedBackMapper;

    @Autowired
    UserService userService;

    @Override
    public void submitFeedback(FeedBack feedBack) {
        feedBack.setFeedbackDate(new Date());
        feedBackMapper.save(feedBack);
    }

    @Override
    public DataMap getAllFeedback(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FeedBack> feedBacks = feedBackMapper.getAllFeedback();
        PageInfo<FeedBack> pageInfo = new PageInfo<>(feedBacks);

        return DataMap.success().setData(pageInfo);
    }
}
