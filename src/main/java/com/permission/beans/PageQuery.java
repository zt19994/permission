package com.permission.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * @author zt1994 2018/8/9 20:26
 */
public class PageQuery {

    @Getter
    @Setter
    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1;

    @Getter
    @Setter
    @Min(value = 1, message = "每页条数不合法")
    private int pageSize = 10;

    @Setter
    private int offset;

    public int getOffset(){
        return (pageNo - 1) * pageSize;
    }
}
