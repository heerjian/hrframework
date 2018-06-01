package org.hframework.sms.alidayu;

import java.util.Map;

import org.hframework.sms.SmsException;
import org.hframework.sms.SmsSender;

/**
 * 阿里大于短信发送器。
 * 
 * @author Future
 * @date 2017-03-13
 */
public interface AlidayuSmsSender extends SmsSender {

	/**
	 * 发送信息。
	 * 
	 * @param tplCode
	 *            短信模板ID。
	 * @param param
	 *            短信模板参数。
	 * @param signName
	 *            短信签名，传入的短信签名必须是在阿里大于“管理中心-短信签名管理”中的可用签名。
	 * @param extend
	 *            公共回传参数，在“消息返回”中会透传回该参数。(主要用于异步短信)
	 * @param phone
	 *            手机号。
	 * @throws SmsException
	 */
	void send(String tplCode, Map<String, String> param, String signName, String extend, String phone)
			throws SmsException;

	/**
	 * 发送信息。
	 * 
	 * @param tplCode
	 *            短信模板ID。
	 * @param param
	 *            短信模板参数。
	 * @param signName
	 *            短信签名，传入的短信签名必须是在阿里大于“管理中心-短信签名管理”中的可用签名。
	 * @param extend
	 *            公共回传参数，在“消息返回”中会透传回该参数。(主要用于异步短信)
	 * @param phones
	 *            手机号。
	 * @throws SmsException
	 */
	void send(String tplCode, Map<String, String> param, String signName, String extend, String... phones)
			throws SmsException;
}
