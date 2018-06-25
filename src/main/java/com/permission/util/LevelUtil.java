package com.permission.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zt1994 2018/6/25 20:01
 */
public class LevelUtil {

    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    public static String calculateLevel(String parentLevel, int parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }
}
