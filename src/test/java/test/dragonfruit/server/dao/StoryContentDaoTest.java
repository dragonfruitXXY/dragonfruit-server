package test.dragonfruit.server.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dragonfruit.server.Main;
import dragonfruit.server.dao.StoryContentDao;
import dragonfruit.server.entity.StoryContent;

/**
 * 
 * Created by xuyh at 2017年5月15日 下午11:12:17.
 */
public class StoryContentDaoTest {
	private StoryContentDao storyContentDao;
	private List<StoryContent> storyContentList = new ArrayList<>();

	@Before
	public void setUp() {
		Main.startUp();
		storyContentDao = (StoryContentDao) Main.getBean("storyContentDao");
		assertNotNull(storyContentDao);
	}

	@Test
	public void test() {
		StoryContent headStoryContent = new StoryContent();
		headStoryContent.setStoryId("storyId_0");
		headStoryContent.setUserId("userId001");
		headStoryContent.setLike(100L);
		storyContentList.add(headStoryContent);
		storyContentDao.save(headStoryContent);
		assertNotNull(headStoryContent.getId());

		for (int i = 0; i < 50; i++) {
			StoryContent storyContent = new StoryContent();
			storyContent.setStoryId("storyId_0");
			storyContent.setUserId("userId001");
			storyContent.setHeadContentId(headStoryContent.getId());
			storyContent.setContent("content_001" + i);
			storyContent.setLike(100L);
			storyContentList.add(storyContent);
			storyContentDao.save(storyContent);
		}

		assertNotNull(storyContentDao.getHeadContent("storyId_0"));
		assertNotNull(storyContentDao.getByUserId("userId001"));
		assertNotNull(storyContentDao.getByUserAndStory("userId001", "storyId_0"));
		assertNotNull(storyContentDao.getByHeadContentId("headContentId_001"));
		assertEquals(true, storyContentDao.increaseLike(headStoryContent.getId()));
		assertEquals(true, storyContentDao.decreaseLike(headStoryContent.getId()));

		for (StoryContent storyContent : storyContentList) {
			storyContentDao.deleteById(storyContent.getId());
		}
	}
}
