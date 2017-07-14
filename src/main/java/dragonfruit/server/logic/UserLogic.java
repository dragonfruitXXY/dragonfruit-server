package dragonfruit.server.logic;

import dragonfruit.server.entity.User;

/**
 * 用户有关的逻辑类
 * 
 * Created by xuyh at 2017年3月3日 下午5:57:07
 *
 */
public interface UserLogic {
	String VERIFY_BY_EMAIL = "register.email";
	String VERIFY_BY_TEXT_MESSAGE = "register.text.message";

	/**
	 * 用户注册(添加缓存)
	 *
	 * <pre>
	 * 		用户注册将信息添加至注册缓存中，待验证成功后存库(异步)    
	 * </pre>
	 * 
	 * @param user
	 * @return
	 */
	boolean register(User user, String verifyBy);

	/**
	 * 用户重新验证(可更换email，phoneNumber)
	 * 
	 * @param userName
	 * @param email
	 * @param phoneNumber
	 * @return
	 */
	boolean updateVerificationCode(String userName, String email, String phoneNumber, String verifyBy);

	/**
	 * 用户注册验证
	 * 
	 * <pre>
	 *     验证成功则添加用户到数据库
	 * </pre>
	 * 
	 * @param userName 用户名
	 * @param verificationCode 验证码
	 * @return 验证成功返回用户ID(数据库ID)
	 */
	String registerVerify(String userName, String verificationCode);

	/**
	 * 保存用户
	 * 
	 * @param user
	 */
	String save(User user);

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @return
	 */
	boolean updateUser(User user);

	/**
	 * 删除用户
	 * 
	 * @param user
	 */
	void delete(User user);

	/**
	 * 通过id删除用户
	 * 
	 * @param id
	 */
	void deleteById(String id);

	/**
	 * 通过id获取用户
	 * 
	 * @param id
	 * @return
	 */
	User getById(String id);

	/**
	 * 通过name获取用户
	 * 
	 * @param name
	 * @return
	 */
	User getByName(String name);
}
