package dragonfruit.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户实体类
 * 
 * Created by xuyh at 2017年3月3日 下午5:22:16
 *
 */
@Document(collection = "User")
public class User {
	public static String FIELD_CODE_ID = "id";
	public static String FIELD_CODE_NAME = "name";
	public static String FIELD_CODE_PASSWORD = "password";
	public static String FIELD_CODE_PHONE_NUM = "phoneNum";
	public static String FIELD_CODE_EMAIL = "email";
	public static String FIELD_CODE_SUGNATURE = "signature";
	public static String FIELD_CODE_NICK_NAME = "nickName";

	@Id
	private String id;
	@Indexed
	private String name;// 用户名,唯一值
	private String password;// 密码
	private String phoneNum;// 电话
	private String email;// 电子邮件
	private String signature;//个性签名
	private String nickName;//用户昵称

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
