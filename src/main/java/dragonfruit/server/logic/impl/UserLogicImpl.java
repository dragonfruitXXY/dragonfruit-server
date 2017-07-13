package dragonfruit.server.logic.impl;

import dragonfruit.server.common.AppProperties;
import dragonfruit.server.common.i18n.I18nConstances;
import dragonfruit.server.entity.UserRegister;
import dragonfruit.server.service.cache.UserRegisterCache;
import dragonfruit.server.util.DateUtils;
import dragonfruit.server.util.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import dragonfruit.server.dao.UserDao;
import dragonfruit.server.entity.User;
import dragonfruit.server.logic.UserLogic;
import xuyihao.email.sender.entity.EmailCredential;
import xuyihao.email.sender.entity.EmailTextMessage;
import xuyihao.email.sender.entity.dict.EmailProtocol;
import xuyihao.email.sender.util.EmailUtils;
import xuyihao.i18n.I18nContext;
import xuyihao.i18n.I18nUtils;

import javax.mail.MessagingException;

@Component("userLogic")
public class UserLogicImpl implements UserLogic {
	private Logger logger = LoggerFactory.getLogger(UserLogicImpl.class);

	@Autowired
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public boolean register(User user, String verifyBy) {
		if (verifyBy.equals(UserLogic.VERIFY_BY_EMAIL))
			return registerByEmailVerification(user);
		if (verifyBy.equals(UserLogic.VERIFY_BY_TEXT_MESSAGE))
			return registerByTextMessageVerification(user);
		return false;
	}

	public boolean registerByEmailVerification(User user) {
		if (user == null)
			return false;
		if (user.getName() == null)
			return false;
		if (user.getEmail() == null)
			return false;
		UserRegister userRegister = new UserRegister();
		userRegister.setName(user.getName());
		userRegister.setNickName(user.getNickName());
		userRegister.setEmail(user.getEmail());
		userRegister.setPassword(user.getPassword());
		userRegister.setPhoneNum(user.getPhoneNum());
		userRegister.setSignature(user.getSignature());
		userRegister.setSubmitTime(DateUtils.currentDateTimeForDate());//设置提交时间
		userRegister.setVerificationCode(RandomUtils.getRandomVerificationString(6));//设置6位验证码
		//发送验证邮件
		EmailProtocol protocol;
		switch (AppProperties.getProperty("dragonfruit.mail.protocol")) {
		case "smtp":
			protocol = EmailProtocol.SMTP;
			break;
		case "pop3":
			protocol = EmailProtocol.POP3;
			break;
		case "imap":
			protocol = EmailProtocol.IMAP;
		default:
			protocol = EmailProtocol.SMTP;
		}
		EmailCredential emailCredential = new EmailCredential(AppProperties.getProperty("dragonfruit.mail.host"),
				Integer.valueOf(AppProperties.getProperty("dragonfruit.mail.port")), protocol,
				AppProperties.getProperty("dragonfruit.mail.user"),
				AppProperties.getProperty("dragonfruit.mail.password"));
		EmailTextMessage message = new EmailTextMessage(AppProperties.getProperty("dragonfruit.mail.user"),
				new String[] { userRegister.getEmail() },
				I18nUtils.getMessage(I18nContext.getLanguage(), I18nConstances.USER_VERIFY_EMAIL_SUBJECT),
				I18nUtils.getMessage(I18nContext.getLanguage(), I18nConstances.USER_VERIFY_EMAIL_CONTENT,
						userRegister.getVerificationCode(),
						String.format(AppProperties.getProperty("dragonfruit.mail.verify.url"),
								AppProperties.getProperty("dragonfruit.server.host"),
								AppProperties.getProperty("dragonfruit.server.port"), userRegister.getName(),
								userRegister.getVerificationCode())));
		try {
			EmailUtils.sendTextEmail(emailCredential, message);
			// TODO: 2017/7/7 这里逻辑还需要斟酌一下 ，是否需要邮件验证添加一个接口
			//邮件发送成功的话添加注册待验证缓存
			UserRegisterCache.addUserRegister(userRegister);
		} catch (MessagingException e) {
			logger.warn(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean registerByTextMessageVerification(User user) {
		// TODO: 2017/7/7 短信验证以后再说 
		return false;
	}

	public String registerVerify(String userName, String verificationCode) {
		if (UserRegisterCache.verify(userName, verificationCode)) {//验证成功,用户存库，返回数据库ID
			UserRegister userRegister = UserRegisterCache.getUserRegisterCache(userName);
			if (userRegister == null)
				return null;
			User user = new User();
			user.setName(userRegister.getName());
			user.setNickName(userRegister.getNickName());
			user.setEmail(userRegister.getEmail());
			user.setPassword(userRegister.getPassword());
			user.setPhoneNum(userRegister.getPhoneNum());
			user.setSignature(userRegister.getSignature());
			return save(user);
		}
		return null;
	}

	public String save(User user) {
		if (user == null)
			return null;
		if (user.getName() == null || user.getPassword() == null)
			return null;
		User dbUser = getByName(user.getName());
		if (dbUser != null && dbUser.getId() != null)// 已经存在这个用户,作更新
			user.setId(dbUser.getId());
		userDao.save(user);
		return user.getId();
	}

	public void delete(User user) {
		userDao.deleteById(user.getId());
	}

	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	public User getById(String id) {
		User user = userDao.getById(id);
		return user;
	}

	public User getByName(String name) {
		User user = userDao.getByName(name);
		return user;
	}

	public boolean updateUser(User user) {
		if (user == null)
			return false;
		if (user.getId() == null || user.getId().equals(""))
			return false;
		userDao.save(user);
		return true;
	}
}
