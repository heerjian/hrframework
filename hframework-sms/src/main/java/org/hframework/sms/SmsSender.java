package org.hframework.sms;
import java.util.Map;

/**
 * 短信发送接口。
 * 
 * @author Future
 * @date 2017-03-10
 */
public interface SmsSender {

	/**
	 * 
	 * 发送一条短信。
	 * 
	 * @param tpl
	 *            短信模板。
	 * @param param
	 *            模板参数。
	 * @param phone
	 *            手机号。
	 * @throws SmsException
	 */
	void send(String tpl, Map<String, String> param, String phone) throws SmsException;

	/**
	 * 批量发送短信。
	 * 
	 * @param tpl
	 *            短信模板。
	 * @param param
	 *            模板参数。
	 * @param phones
	 *            手机号列表。
	 * @throws SmsException
	 */
	void send(String tpl, Map<String, String> param, String... phones) throws SmsException;
}
