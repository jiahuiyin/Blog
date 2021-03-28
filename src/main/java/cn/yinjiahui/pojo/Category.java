package cn.yinjiahui.pojo;

import lombok.Data;

import java.io.Serializable;


@Data
public class Category implements Serializable {

    private String categoryName;

    private Integer categoryNum;

}
