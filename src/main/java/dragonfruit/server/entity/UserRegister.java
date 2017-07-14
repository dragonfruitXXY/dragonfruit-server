package dragonfruit.server.entity;

import dragonfruit.server.util.DateUtils;

import java.util.Date;

/**
 * 用户注册实体类，定义注册验证等字段
 * 
 * Created by xuyh at 2017/7/7 14:22.
 */
public class UserRegister {
	private Date submitTime;//注册提交时间
	private String name;// 用户名,唯一值
	private String password;// 密码
	private String phoneNum;// 电话
	private String email;// 电子邮件
	private String signature;//个性签名
	private String nickName;//用户昵称
	private String verificationCode;//验证码
	private boolean verified = false;//是否经过验证

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public boolean verify(String userName, String verificationCode) {
		if (getName().equals(userName) && getVerificationCode().equalsIgnoreCase(verificationCode)) {
			setVerified(true);
			return true;
		} else {
			return false;
		}
	}

	public boolean changeVerificationCode(String userName, String verificationCode) {
		if (!getName().equals(userName))
			return false;
		//更新验证码并更新提交时间
		setVerificationCode(verificationCode);
		setSubmitTime(DateUtils.currentDateTimeForDate());
		return true;
	}
}
