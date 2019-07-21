package com.jiehang.util;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName StringUtil
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-18 18:51
 **/

public class StringUtil {
    //1,2,3,4 - > list
    /**
     * String to List
     * @param str
     * @return
     */
    public static List<Integer> splitToListInt(String str) {
        List<String> strList = Splitter.on(",").trimResults()
                .omitEmptyStrings()
                .splitToList(str);
        return strList.stream().map(strItem -> Integer.parseInt(strItem)).collect(Collectors.toList());
    }

}
