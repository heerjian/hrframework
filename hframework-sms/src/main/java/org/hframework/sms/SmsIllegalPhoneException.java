package org.hframework.sms;


import java.util.regex.Pattern;

/**
 * 手机号格式异常。
 * 
 * @author Future
 * @date 2017-03-13
 */
public class SmsIllegalPhoneException extends SmsException {
	private static final long serialVersionUID = 1L;

	private static final Pattern PHONE_REG = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");

	public SmsIllegalPhoneException(String message) {
		super(message);
	}

	public SmsIllegalPhoneException(Throwable cause) {
		super(cause);
	}

	/**
	 * 判断手机号是否正确。
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		return PHONE_REG.matcher(phone).matches();
	}

	/**
	 * 检查手机号格式，如果不正确抛出异常。
	 * 
	 * @param phone
	 * @throws SmsIllegalPhoneException
	 */
	public static void checkPhone(String phone) throws SmsIllegalPhoneException {
		if (!isPhone(phone))
			throw new SmsIllegalPhoneException("手机号码不正确");
	}

}
