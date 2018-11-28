package com.permission.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 日志搜索类
 *
 * @author zt1994 2018/11/28 20:33
 */
@Getter
@Setter
@ToString
public class SearchLogParam {

    private Integer type;

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private String fromTime; //yyyy-MM-dd HH:mm:ss

    private String toTime; //yyyy-MM-dd HH:mm:ss

}
