package cn.yinjiahui.pojo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class FeedBack implements Serializable {

    private Integer id;

    /**
     * 反馈内容
     */
    private String feedbackContent;


    private String contactInfo;


    private Integer personId;


    private Date feedbackDate;

}
