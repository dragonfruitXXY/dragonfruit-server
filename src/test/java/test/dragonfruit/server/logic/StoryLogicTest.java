package test.dragonfruit.server.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dragonfruit.server.Main;
import dragonfruit.server.entity.Story;
import dragonfruit.server.logic.StoryLogic;

/**
 * 
 * Created by xuyh at 2017年5月15日 下午11:12:25.
 */
public class StoryLogicTest {
	private StoryLogic storyLogic;
	private List<Story> storyList = new ArrayList<>();

	@Before
	public void setUp() {
		Main.startUp();
		storyLogic = (StoryLogic) Main.getBean("storyLogic");
		assertNotNull(storyLogic);
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
			storyLogic.save(story);
		}
		assertNotNull(storyLogic.getAllCount());
		assertNotNull(storyLogic.getPageByPageSize(0, 10));
		assertNotNull(storyLogic.getPageByLikeDown(0, 10));
		assertNotNull(storyLogic.getByStoryType("type_001"));
		assertNotNull(storyLogic.getByUserId("user001"));
		assertNotNull(storyLogic.getByName(storyList.get(0).getName()));
		for (int j = 0; j < 20; j++) {
			storyLogic.deleteById(storyList.get(j).getId());
		}
	}

}
