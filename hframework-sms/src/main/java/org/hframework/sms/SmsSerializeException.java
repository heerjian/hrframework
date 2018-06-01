package org.hframework.sms;


/**
 * 解析短信发送结果异常。
 * 
 * @author Future
 * @date 2017-03-13
 */
public class SmsSerializeException extends SmsException {
	private static final long serialVersionUID = 1L;

	public SmsSerializeException(String message) {
		super(message);
	}

	public SmsSerializeException(Throwable cause) {
		super(cause);
	}

}
