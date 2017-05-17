package dragonfruit.server.logic;

import dragonfruit.server.entity.User;

/**
 * 用户有关的逻辑类
 * 
 * Created by xuyh at 2017年3月3日 下午5:57:07
 *
 */
public interface UserLogic {
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
