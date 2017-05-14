package dragonfruit.server.entity.front.vos;

/**
 * 返回客户端的User数据结构
 * 
 * Created by Xuyh at 2017年3月23日 上午12:04:58.
 */
public class UserVO {
	private String id;
	private String name;// 用户名
	private String password;// 密码
	private String phoneNum;// 电话
	private String email;

	public UserVO(String id, String name, String password, String phoneNum, String email) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.phoneNum = phoneNum;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}
