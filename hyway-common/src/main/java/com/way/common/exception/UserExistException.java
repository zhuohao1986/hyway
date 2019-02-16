package com.way.common.exception;


/**
 * 用户已存在
 *
 * @author 
 */
public class UserExistException extends BusinessException {

    public UserExistException(String message) {
        super(message);
    }

}
