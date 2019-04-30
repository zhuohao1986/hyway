package com.way.common.exception;

/**
 * 系统业务异常.
 *
 * @author 
 */
public class SystemException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1908490653209532101L;

	public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}