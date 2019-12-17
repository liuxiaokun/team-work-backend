package com.byx.work.team.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/3/26
 */
public class SaltUtil {

    private static String[] shuffle = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "d", "c", "e", "f", "g", "h", "i", "j",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z"};

    public static String genSalt(int length) {
        List<String> list = Arrays.asList(shuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();

        int i = 0;
        for (String temp : list) {

            if (i < length) {
                sb.append(temp);
                i++;
            } else {
                break;
            }
        }
        return sb.toString();
    }
}