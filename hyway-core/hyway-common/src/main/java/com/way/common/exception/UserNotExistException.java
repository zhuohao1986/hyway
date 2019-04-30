package com.way.common.exception;


/**
 * 用户未存在
 *
 * @author 
 */
public class UserNotExistException extends BusinessException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4530737394708555082L;

	public UserNotExistException(String message) {
        super(message);
    }

}
