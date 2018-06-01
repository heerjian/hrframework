package org.hframework.sms;


/**
 * 短信发送异常。
 * 
 * @author Future
 * @date 2017-03-13
 */
public class SmsResultException extends SmsException {
	private static final long serialVersionUID = 1L;

	public SmsResultException(String message) {
		super(message);
	}

	public SmsResultException(Throwable cause) {
		super(cause);
	}

}
