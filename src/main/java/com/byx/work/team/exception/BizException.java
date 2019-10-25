package com.byx.work.team.exception;

/**
 * @author liuxiaokun
 * @version 0.0.1
 * @since 2019/10/25
 */
public class BizException extends Exception {

    public BizException(){
    }

    public BizException(String message){
        super(message);
    }
}
