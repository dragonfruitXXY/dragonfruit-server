package test.dragonfruit.server.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dragonfruit.server.Main;
import dragonfruit.server.dao.LikeStoryContentDao;

public class LikeStoryContentDaoTest {
	private LikeStoryContentDao likeStoryContentDao;

	@Before
	public void setUp() {
		Main.startUp();
		likeStoryContentDao = (LikeStoryContentDao) Main.getBean("likeStoryContentDao");
		assertNotNull(likeStoryContentDao);
	}

	@Test
	public void test() {
		likeStoryContentDao.like("user001", "storyContentId001");

		assertNotNull(likeStoryContentDao.getByUserAndStoryContent("user001", "storyContentId001"));
		assertNotNull(likeStoryContentDao.getCountByUserId("user001"));
		assertNotNull(likeStoryContentDao.getCountByStoryContentId("storyContentId001"));

		likeStoryContentDao.unLike("user001", "storyContentId001");
	}

}
