package test.dragonfruit.server.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dragonfruit.server.Main;
import dragonfruit.server.entity.StoryContent;
import dragonfruit.server.logic.StoryContentLogic;

/**
 * 
 * Created by xuyh at 2017年5月15日 下午11:12:29.
 */
public class StoryContentLogicTest {
	private StoryContentLogic storyContentLogic;
	private List<StoryContent> storyContentList = new ArrayList<>();

	@Before
	public void setUp() {
		Main.startUp();
		storyContentLogic = (StoryContentLogic) Main.getBean("storyContentLogic");
		assertNotNull(storyContentLogic);
	}

	@Test
	public void test() {
		StoryContent headStoryContent = new StoryContent();
		headStoryContent.setStoryId("storyId_0");
		headStoryContent.setUserId("userId001");
		headStoryContent.setLike(100L);
		storyContentList.add(headStoryContent);
		storyContentLogic.save(headStoryContent);
		assertNotNull(headStoryContent.getId());

		for (int i = 0; i < 50; i++) {
			StoryContent storyContent = new StoryContent();
			storyContent.setStoryId("storyId_0");
			storyContent.setUserId("userId001");
			storyContent.setHeadContentId(headStoryContent.getId());
			storyContent.setContent("content_001" + i);
			storyContent.setLike(100L);
			storyContentList.add(storyContent);
			storyContentLogic.save(storyContent);
		}

		assertNotNull(storyContentLogic.getHeadContent("storyId_0"));
		assertNotNull(storyContentLogic.getByUserId("userId001"));
		assertNotNull(storyContentLogic.getByUserAndStory("userId001", "storyId_0"));
		assertNotNull(storyContentLogic.getByHeadContentId("headContentId_001"));

		for (StoryContent storyContent : storyContentList) {
			storyContentLogic.deleteById(storyContent.getId());
		}
	}
}
