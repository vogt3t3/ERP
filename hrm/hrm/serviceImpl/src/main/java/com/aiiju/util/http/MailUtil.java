package com.aiiju.util.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;

import com.aiiju.util.common.PropertiesUtil;
import com.aiiju.util.exception.BizException;

/**
 * 
 * @ClassName: MailUtil 
 * @Description: 邮件工具类
 * @author 哪吒 
 * @date 2016年11月21日 下午2:29:04 
 *
 */

public class MailUtil {

	private static final String emailAddressPattern = "\\b(^['_A-Za-z0-9-]+(\\.['_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";

	private static String HOST = PropertiesUtil.getPropertyByKey("mail.smtp.host"); // 邮箱的smtp服务器
	private static String FROM = PropertiesUtil.getPropertyByKey("mail.stmp.user"); // 邮件发送者
	private static String PORT = PropertiesUtil.getPropertyByKey("mail.smtp.port"); // 邮件端口
	private static String TIMEOUT = PropertiesUtil.getPropertyByKey("mail.stmp.timeout"); // 邮件超时
	private static String MAIL_NAME = PropertiesUtil.getPropertyByKey("mail.stmp.username"); // 邮件发送者名称
	private static String PASSWORD = PropertiesUtil.getPropertyByKey("mail.stmp.pwd"); // 密码
	private static String AUTH = PropertiesUtil.getPropertyByKey("mail.smtp.auth"); // 是否校验
	private static String DEBUG = PropertiesUtil.getPropertyByKey("mail.stmp.debug");// 是否debug
	private static final int FAILED_RESEND_TIMES = 3; // 重试次数
	private static final int THREAD_SLEEP = 3000; // 休眠时间 threadsleep
	
	// TEST
	/*private static String HOST = "smtp.exmail.qq.com"; // 邮箱的smtp服务器
	private static String FROM = "aijuHR@iyenei.com"; // 邮件发送者
	private static int PORT = 25; // 邮件端口
	private static int TIMEOUT = 2000; // 邮件超时
	private static String MAIL_NAME = "爱聚HR"; // 邮件主题
	private static String PASSWORD = "Iyenei123123"; // 密码
	private static boolean AUTH = true; // 是否校验
	private static boolean DEBUG = false;// 是否debug
*/
	
	private static Message message = initMessage();
	
	/**
	 * 初始化信息
	 * @return
	 */
	private static final Message initMessage() {
		Properties props = new Properties();
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.auth", Boolean.valueOf(AUTH));
		props.put("mail.smtp.port", Integer.valueOf(PORT));
		props.put("mail.stmp.timeout", Integer.valueOf(TIMEOUT));
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM, PASSWORD);
			}
		});
		session.setDebug(Boolean.valueOf(DEBUG));
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(MimeUtility.encodeText(MAIL_NAME) + "<" + FROM + ">"));
		} catch (AddressException e) {
			throw new BizException("发送邮件失败");
		} catch (UnsupportedEncodingException e) {
			throw new BizException("发送邮件失败");
		} catch (MessagingException e) {
			throw new BizException("发送邮件失败");
		}
		return message;
	}

	/**
	 * 发送邮件
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @param to 接收人
	 * @throws MessagingException 
	 */
	private static final synchronized void sendMail(String subject, String content, String... to)
			throws MessagingException {
		to = validMails(to);
		
		if (null == to || to.length == 0) {
			return;
		}
		message.setSubject(subject);
		message.setContent(content, "text/html;charset=UTF-8");
		List<Address> list = new ArrayList<Address>();
		for (String mail : to) {
			list.add(new InternetAddress(mail));
		}
		Transport.send(message, list.toArray(new Address[0]));
	}

	/**
	 * 校验邮件地址
	 * @param mails 邮箱地址
	 * @return
	 */
	public static String[] validMails(String... mails) {
		if (null == mails || mails.length == 0) {
			return null;
		}
		Pattern pattern = Pattern.compile(emailAddressPattern);
		List<String> mailList = new ArrayList<String>();
		for (String tmp : mails) {
			if (StringUtils.isEmpty(tmp)) {
				continue;
			}
			Matcher matcher = pattern.matcher(tmp);
			if (matcher.matches()) {
				mailList.add(tmp);
			}
		}
		if (mailList.size() != mails.length) {
			if (mailList.size() == 0) {
				return null;
			}
			return mailList.toArray(new String[0]);
		}
		return mails;
	}

	/**
	 * 发送邮件
	 */
	static final class MailSendThend extends Thread {
		private String subject;
		private String content;
		private String[] mails;

		public MailSendThend(String subject, String content, String[] mails) {
			this.subject = subject;
			this.content = content;
			this.mails = mails;
		}

		@Override
		public void run() {
			int sendTimes = 0; // 重试次数
			boolean isTryAgain = false, isFirst = true;
			do {
				try {
					while (isFirst || isTryAgain) {
						sendMail(subject, content, mails);
						isTryAgain = isFirst = false;
						Thread.sleep(THREAD_SLEEP);
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						Thread.sleep(THREAD_SLEEP);
					} catch (Exception e1) {
						throw new BizException("发送邮件失败");
					}
					isTryAgain = true;
				}
			} while (sendTimes++ < FAILED_RESEND_TIMES);
		}
	}

	/**
	 * 发送邮件
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @param to 接收人
	 */
	public static boolean syncSend(String subject, String content, String... to) {
		int sendTimes = 0; // 重试次数
		boolean isTryAgain = false, isFirst = true, result = false;
		do {
			try {
				while (isFirst || isTryAgain) {
					sendMail(subject, content, to);
					isTryAgain = isFirst = false;
					Thread.sleep(THREAD_SLEEP);
				}
				
				result = true;
			} catch (Exception e) {
				try {
					Thread.sleep(THREAD_SLEEP);
				} catch (Exception e1) {
					throw new BizException("发送邮件失败");
				}
				isTryAgain = true;
			}
		} while (sendTimes++ < FAILED_RESEND_TIMES);
		
		return result;
	}
	
	/**
	 * 发送邮件
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @param to 接收人
	 */
	public static boolean syncSendWithImg(String subject, String content, String bgImg, String titleImg, String... to) {
		int sendTimes = 0; // 重试次数
		boolean isTryAgain = false, isFirst = true, result = false;
		do {
			try {
				while (isFirst || isTryAgain) {
					sendMailWithImg(subject, content, bgImg, titleImg, to);
					isTryAgain = isFirst = false;
					Thread.sleep(THREAD_SLEEP);
				}
				
				result = true;
			} catch (Exception e) {
				try {
					Thread.sleep(THREAD_SLEEP);
				} catch (Exception e1) {
					throw new BizException("发送邮件失败");
				}
				isTryAgain = true;
			}
		} while (sendTimes++ < FAILED_RESEND_TIMES);
		
		return result;
	}
	
	/**
	 * 发送邮件 带背景图片的
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @param to 接收人
	 * @throws MessagingException 
	 */
	private static final synchronized void sendMailWithImg(String subject, String content, String bgImg, String titleImg, String... to)
			throws MessagingException {
		to = validMails(to);
		
		if (null == to || to.length == 0) {
			return;
		}
		
		List<Address> list = new ArrayList<Address>();
		for (String mail : to) {
			list.add(new InternetAddress(mail));
		}
		
		message.setSubject(subject);
		message.setSentDate(new Date());
		message.setRecipient(Message.RecipientType.TO, list.get(0));
//		message.setContent(content, "text/html;charset=UTF-8");
		
		
		Multipart multipart = new MimeMultipart("related");
		BodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(content, "text/html;charset=UTF-8");
		multipart.addBodyPart(htmlPart);
		BodyPart titleImgPart = new MimeBodyPart();
		DataSource titleDS = new FileDataSource(titleImg);
		titleImgPart.setDataHandler(new DataHandler(titleDS));
		titleImgPart.setHeader("Content-ID", "<titleimg>");
		BodyPart bgImgPart = new MimeBodyPart();
		DataSource bgDS = new FileDataSource(bgImg);
		bgImgPart.setDataHandler(new DataHandler(bgDS));
		bgImgPart.setHeader("Content-ID", "<bgimg>");
		multipart.addBodyPart(titleImgPart);
		multipart.addBodyPart(bgImgPart);
		message.setContent(multipart);
		
		Transport.send(message, list.toArray(new Address[0]));
	}

	/**
	 * 发送邮件
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @param to 接收人
	 */
	public static void asynSend(String subject, String content, String... to) {
		new MailSendThend(subject, content, to).start();
	}
	
	public static void main(String[] args) {
		boolean result = syncSend("测试邮件", "这是一封测试邮件", "henuyuyuan@163.com");
		System.out.println(result);
	}
}
