package com.permission.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zt1994 2018/6/25 20:01
 */
public class LevelUtil {

    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";


    /**
     * 计算层级关系
     * 0 0.1 0.1.2 0.1.3 0.4
     *
     * @param parentLevel
     * @param parentId
     * @return
     */
    public static String calculateLevel(String parentLevel, int parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }
}
