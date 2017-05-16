package test.dragonfruit.server.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dragonfruit.server.Main;
import dragonfruit.server.entity.StoryContent;
import dragonfruit.server.entity.User;
import dragonfruit.server.logic.LikeStoryContentLogic;
import dragonfruit.server.logic.StoryContentLogic;
import dragonfruit.server.logic.UserLogic;

public class LikeStoryContentLogicTest {
	private LikeStoryContentLogic likeStoryContentLogic;
	private StoryContentLogic storyContentLogic;
	private UserLogic userLogic;

	@Before
	public void setUp() {
		Main.startUp();
		likeStoryContentLogic = (LikeStoryContentLogic) Main.getBean("likeStoryContentLogic");
		storyContentLogic = (StoryContentLogic) Main.getBean("storyContentLogic");
		userLogic = (UserLogic) Main.getBean("userLogic");
		assertNotNull(likeStoryContentLogic);
		assertNotNull(storyContentLogic);
		assertNotNull(userLogic);
	}

	@Test
	public void test() {
		User user = new User();
		user.setName("user002");
		user.setPassword("pwd002");
		userLogic.save(user);

		StoryContent storyContent = new StoryContent();
		storyContent.setContent("content001");
		storyContent.setStoryId("story_kklId001");
		storyContent.setUserId(user.getId());
		storyContentLogic.save(storyContent);
		likeStoryContentLogic.like(user.getId(), storyContent.getId());

		assertNotNull(likeStoryContentLogic.getCountByStoryContentId(storyContent.getId()));
		assertNotNull(likeStoryContentLogic.getCountByUserId(user.getId()));

		likeStoryContentLogic.unLike(user.getId(), storyContent.getId());
		userLogic.delete(user);
		storyContentLogic.deleteById(storyContent.getId());
	}

}
