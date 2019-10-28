package com.byx.work.team.utils;

import org.springframework.beans.BeanUtils;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/10/28
 */
public class BeanUtil {

    public static <T> T copyProperties(Object source, T target) {

        BeanUtils.copyProperties(source, target);
        return target;
    }
}