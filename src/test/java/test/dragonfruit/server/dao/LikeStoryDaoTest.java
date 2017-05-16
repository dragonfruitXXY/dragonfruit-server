package test.dragonfruit.server.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dragonfruit.server.Main;
import dragonfruit.server.dao.LikeStoryDao;

public class LikeStoryDaoTest {
	private LikeStoryDao likeStoryDao;

	@Before
	public void setUp() {
		Main.startUp();
		likeStoryDao = (LikeStoryDao) Main.getBean("likeStoryDao");
		assertNotNull(likeStoryDao);
	}

	@Test
	public void test() {
		likeStoryDao.like("user001", "storyId001");

		assertNotNull(likeStoryDao.getByUserAndStory("user001", "storyId001"));
		assertNotNull(likeStoryDao.getCountByUserId("user001"));
		assertNotNull(likeStoryDao.getCountByStoryId("storyId001"));

		likeStoryDao.unLike("user001", "storyId001");
	}
}
