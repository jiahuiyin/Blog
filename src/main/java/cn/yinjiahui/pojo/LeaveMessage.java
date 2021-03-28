package cn.yinjiahui.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class LeaveMessage implements Serializable {

    private Integer id;


    private String pageName;

    private Integer pId=0;

    private Integer answererId;

    private Date leaveMessageDate;

    private String leaveMessageContent;

    private String answererUsername;

    private String avatarImgUrl;

    private List<LeaveMessage> replies;

    public String getAvatarImgUrl() {
        if(avatarImgUrl==null)
            return null;
        return avatarImgUrl;
    }
}
