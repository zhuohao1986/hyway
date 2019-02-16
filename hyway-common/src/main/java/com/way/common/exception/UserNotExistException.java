package com.way.common.exception;


/**
 * 用户未存在
 *
 * @author 
 */
public class UserNotExistException extends BusinessException {

    public UserNotExistException(String message) {
        super(message);
    }

}
