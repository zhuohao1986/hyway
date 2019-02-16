package com.way.common.exception;


/**
 * 短信发送太频繁
 *
 * @author 
 */
public class SmsTooMuchException extends BusinessException {

    public SmsTooMuchException(String message) {
        super(message);
    }

}
