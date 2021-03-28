package cn.yinjiahui.service.impl;


import cn.yinjiahui.service.VisitorService;
import cn.yinjiahui.utils.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class VisitorServiceImpl implements VisitorService {


    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public static final String VISITORNUM="visitorNum";

    @Override
    public int getVisitorNum(){
        Integer num = (Integer) redisTemplate.opsForValue().get(VISITORNUM);

        return num;
    }
    @Override
    public DataMap getVisitorNum(boolean addVisit) {
        long visitorNum;

        Integer num = (Integer) redisTemplate.opsForValue().get(VISITORNUM);

        if (addVisit == true)
            visitorNum = redisTemplate.opsForValue().increment(VISITORNUM);
        else
            visitorNum = num.longValue();


        return DataMap.success().setData(visitorNum);
    }
}
