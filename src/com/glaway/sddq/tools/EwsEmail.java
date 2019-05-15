package com.glaway.sddq.tools;

import java.net.URI;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.cache.SysPropCache;

/**
 * 
 * <功能描述> 邮件发送功能
 * 
 * @author abc
 * @version [版本号, 2018年7月30日]
 * @since [产品/模块版本]
 */
public class EwsEmail {
	/**
	 * 
	 * <功能描述> 邮件发送功能(单人)
	 * 
	 * 
	 * @param Title
	 *            主题
	 * @param Main
	 *            内容
	 * @param Sendee
	 *            接收人
	 * @param CC
	 *            抄送人 [参数说明]
	 * @throws Exception
	 * 
	 */
	public static void send(String Title, String Main, String Sendee, String CC)
			throws Exception {
		ExchangeService service = new ExchangeService(
				ExchangeVersion.Exchange2010_SP1); // 新建server版本
		MroServer mroServer = MroServer.getMroServer();
		SysPropCache sysPropCache = mroServer.getSysPropCache();
		String Url = sysPropCache.getPropValue("mro.email.service");
		String User = sysPropCache.getPropValue("mro.email.user");

		String Password = sysPropCache.getPropValue("mro.email.password");
		ExchangeCredentials credentials = new WebCredentials(User, Password); // 自己的用户名和密码
		service.setCredentials(credentials);
		service.setUrl(new URI(Url)); //

		EmailMessage msg = new EmailMessage(service);

		msg.setSubject(Title); // 主题

		msg.setBody(MessageBody.getMessageBodyFromText(Main)); // 内容

		msg.getToRecipients().add(email(Sendee)); // 收件人

		// msg.getToRecipients().add("public2174@teg.cn");
		if (!CC.isEmpty()) {

			msg.getCcRecipients().add(email(CC)); // 抄送人
		}


		msg.send(); // 发送

	}

	/**
	 * 
	 * <功能描述>邮件发送功能(群体)
	 * 
	 * 
	 * @param Title
	 *            主题
	 * @param Main
	 *            内容
	 * @param Sendee
	 *            接收人
	 * @param CC
	 *            抄送人 [参数说明]
	 * @throws Exception
	 * 
	 */
	public static void sends(String Title, String Main, String[] Sendee,
			String[] CC) throws Exception {
		ExchangeService service = new ExchangeService(
				ExchangeVersion.Exchange2010_SP1); // 新建server版本

		MroServer mroServer = MroServer.getMroServer();
		SysPropCache sysPropCache = mroServer.getSysPropCache();
		String Url = sysPropCache.getPropValue("mro.email.service");
		String User = sysPropCache.getPropValue("mro.email.user");

		String Password = sysPropCache.getPropValue("mro.email.password");
		ExchangeCredentials credentials = new WebCredentials(User, Password); // 自己的用户名和密码
		service.setCredentials(credentials);
		service.setUrl(new URI(Url)); //
										//
										// 改为自己的邮箱

		EmailMessage msg = new EmailMessage(service);

		msg.setSubject(Title); // 主题

		msg.setBody(MessageBody.getMessageBodyFromText(Main)); // 内容

		if (Sendee.length > 0) {
			for (int i = 0; i < Sendee.length; i++) {
				msg.getToRecipients().add(email(Sendee[i])); // 收件人
			}
		}
		if (CC != null || (CC != null && CC.length == 0)) {
			if (CC.length > 0) {
				for (int j = 0; j < CC.length; j++) {
					msg.getCcRecipients().add(email(CC[j])); // 抄送人

				}
			}
		}

		msg.send(); // 发送
	}

	// 群发功能-zzx add test 0903
	public static void sendsTest(String Title, String Main, String[] Sendee,
			String[] CC) throws Exception {

		ExchangeService service = new ExchangeService(
				ExchangeVersion.Exchange2010_SP1); // 新建server版本

		MroServer mroServer = MroServer.getMroServer();
		SysPropCache sysPropCache = mroServer.getSysPropCache();
		String Url = sysPropCache.getPropValue("mro.email.service");
		String User = sysPropCache.getPropValue("mro.email.user");

		String Password = sysPropCache.getPropValue("mro.email.password");
		ExchangeCredentials credentials = new WebCredentials(User, Password); // 自己的用户名和密码
		service.setCredentials(credentials);
		service.setUrl(new URI(Url)); //

		EmailMessage msg = new EmailMessage(service);

		msg.setSubject(Title); // 主题
		msg.setBody(MessageBody.getMessageBodyFromText(Main)); // 内容

		msg.getToRecipients().add("public2173@teg.cn"); // 收件人
		msg.getToRecipients().add("public2174@teg.cn"); // 收件人2

		if (CC != null || (CC != null && CC.length == 0)) {
			if (CC.length > 0) {
				for (int j = 0; j < CC.length; j++) {
					msg.getCcRecipients().add(email(CC[j])); // 抄送人

				}
			}
		}
		msg.send(); // 发送
	}

	// zzx add end 0903

	private static String email(String personid) throws MroException {
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("SYS_PERSON");
		jposet.setQueryWhere("personid = '" + personid + "'");
		String emails = jposet.getJpo(0).getString("PRIMARYEMAIL");
		return emails;
	}

	public static void main(String[] args) throws Exception {

		ExchangeService service = new ExchangeService(
				ExchangeVersion.Exchange2010_SP1); // 新建server版本

		ExchangeCredentials credentials = new WebCredentials("public2173",
				"abc.126214"); // 自己的用户名和密码

		service.setCredentials(credentials);

		service.setUrl(new URI("https://mail.teg.cn/EWS/Exchange.asmx")); //
																			// 改为自己的邮箱

		EmailMessage msg = new EmailMessage(service);

		msg.setSubject("Hello world!"); // 主题

		msg.setBody(MessageBody
				.getMessageBodyFromText("Sent using the EWS Managed API.")); // 内容

		msg.getToRecipients().add("public2173@teg.cn"); // 收件人

		// msg.getCcRecipients().add("victorluo@com"); // 抄送人

		msg.send(); // 发送
		System.out.println("fjsdiofj");
	}
}
