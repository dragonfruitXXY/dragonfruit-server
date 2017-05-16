package test.dragonfruit.server.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dragonfruit.server.Main;
import dragonfruit.server.entity.User;
import dragonfruit.server.logic.UserLogic;

/**
 * 
 * Created by xuyh at 2017年5月15日 下午11:12:21.
 */
public class UserLogicTest {
	private UserLogic userLogic;

	@Before
	public void setUp() {
		Main.startUp();
		userLogic = (UserLogic) Main.getBean("userLogic");
		assertNotNull(userLogic);
	}

	@Test
	public void test() {
		User user = new User();
		user.setName("Halo");
		user.setPassword("123456");
		user.setPhoneNum("11111111");
		user.setSignature("Yeah!");
		user.setEmail("123123123@123.cn");
		String id = userLogic.save(user);
		assertNotNull(id);
		User dbUser = userLogic.getByName(user.getName());
		assertNotNull(dbUser);
		userLogic.delete(user);
	}
}
