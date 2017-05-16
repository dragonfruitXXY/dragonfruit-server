package test.dragonfruit.server.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dragonfruit.server.Main;
import dragonfruit.server.dao.StoryDao;
import dragonfruit.server.entity.Story;

/**
 * 
 * Created by xuyh at 2017年5月15日 下午11:12:13.
 */
public class StoryDaoTest {
	private StoryDao storyDao;
	private List<Story> storyList = new ArrayList<>();

	@Before
	public void setUp() {
		Main.startUp();
		storyDao = (StoryDao) Main.getBean("storyDao");
		assertNotNull(storyDao);
	}

	@Test
	public void test() {
		for (int i = 0; i < 20; i++) {
			Story story = new Story();
			story.setName("story_" + i);
			story.setDescription("story_description_" + i);
			story.setStoryTypeId("type_001");
			story.setUserId("user001");
			story.setLike((long) i);
			storyList.add(story);
			storyDao.save(story);
		}
		assertNotNull(storyDao.getAllCount());
		assertNotNull(storyDao.getPageByPageSize(0, 10));
		assertNotNull(storyDao.getPageByLikeDown(0, 10));
		assertNotNull(storyDao.getByStoryType("type_001"));
		assertNotNull(storyDao.getByUserId("user001"));
		assertNotNull(storyDao.getByName(storyList.get(0).getName()));
		assertEquals(true, storyDao.increaseLike(storyList.get(0).getId()));
		assertEquals(true, storyDao.decreaseLike(storyList.get(0).getId()));
		for (int j = 0; j < 20; j++) {
			storyDao.deleteById(storyList.get(j).getId());
		}
	}
}
