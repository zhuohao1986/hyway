package com.way.common.exception;


/**
 * 用户已存在
 *
 * @author 
 */
public class UserExistException extends BusinessException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8130168290714393909L;

	public UserExistException(String message) {
        super(message);
    }

}
