package com.permission.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author zt1994 2018/11/28 20:38
 */
@Getter
@Setter
@ToString
public class SearchLogDto {

    private Integer type;

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private Date fromTime; //yyyy-MM-dd HH:mm:ss

    private Date toTime; //yyyy-MM-dd HH:mm:ss

}
