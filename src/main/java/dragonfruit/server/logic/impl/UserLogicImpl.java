package dragonfruit.server.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import dragonfruit.server.dao.UserDao;
import dragonfruit.server.entity.User;
import dragonfruit.server.logic.UserLogic;

@Component("userLogic")
public class UserLogicImpl implements UserLogic {
	@Autowired
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
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
