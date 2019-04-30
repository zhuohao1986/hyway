package com.way.common.exception;


/**
 * 短信发送太频繁
 *
 * @author 
 */
public class SmsTooMuchException extends BusinessException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2133826209156924171L;

	public SmsTooMuchException(String message) {
        super(message);
    }

}
