package com.jiehang.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName LevelUtil
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-11 00:39
 **/

public class LevelUtil {
    /**
     * separator symbol
     */
    public final static String SEPARATOR = ".";
    /**
     * the root level value is O
     */
    public final static String ROOT = "0";

    /**
     * calculate department level
     * 0
     * 0.1
     * 0.1.1
     * @param parentLevel
     * @param parentId
     * @return
     */
    public static String calculateLevel(String parentLevel,int parentId ) {
        if(StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel,SEPARATOR,parentId);
        }
    }
}
