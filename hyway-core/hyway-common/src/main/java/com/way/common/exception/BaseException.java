package com.way.common.exception;

/**
 * 基本异常.
 *
 * @author 
 */
public class BaseException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -417206525414814482L;

	private int code;
	
	private String message;
	

    public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	BaseException() {
        super();
    }

    BaseException(String message) {
        super(message);
    }

    BaseException(Throwable cause) {
        super(cause);
    }

    BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}