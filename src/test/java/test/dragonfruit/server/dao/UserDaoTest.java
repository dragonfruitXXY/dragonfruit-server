package test.dragonfruit.server.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dragonfruit.server.Main;
import dragonfruit.server.dao.UserDao;
import dragonfruit.server.entity.User;

/**
 * 
 * Created by xuyh at 2017年5月15日 下午11:12:08.
 */
public class UserDaoTest {
	private UserDao userDao;

	@Before
	public void setup() {
		Main.startUp();
		userDao = (UserDao) Main.getBean("userDao");
		assertNotNull(userDao);
	}

	@Test
	public void test() {
		User user = new User();
		user.setName("Halo");
		user.setPassword("123456");
		user.setPhoneNum("11111111");
		user.setSignature("Yeah!");
		user.setEmail("123123123@123.cn");
		userDao.save(user);
		assertNotNull(userDao.getByName(user.getName()));
		userDao.deleteById(user.getId());
	}
}
