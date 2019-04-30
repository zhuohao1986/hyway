package com.way.common.exception;



/**
 * 手机号码不合法
 *
 * @author 
 */
public class IllegalMobileException extends BusinessException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 866070612794132818L;
	private static final String MESSAGE = "手机号码不合法";

    public IllegalMobileException() {
        super(MESSAGE);
    }

    public IllegalMobileException(String message) {
        super(message);
    }
}
