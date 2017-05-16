package test.dragonfruit.server.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dragonfruit.server.Main;
import dragonfruit.server.entity.Story;
import dragonfruit.server.entity.User;
import dragonfruit.server.logic.LikeStoryLogic;
import dragonfruit.server.logic.StoryLogic;
import dragonfruit.server.logic.UserLogic;

public class LikeStoryLogicTest {
	private LikeStoryLogic likeStoryLogic;
	private StoryLogic storyLogic;
	private UserLogic userLogic;

	@Before
	public void setUp() {
		Main.startUp();
		likeStoryLogic = (LikeStoryLogic) Main.getBean("likeStoryLogic");
		storyLogic = (StoryLogic) Main.getBean("storyLogic");
		userLogic = (UserLogic) Main.getBean("userLogic");
		assertNotNull(likeStoryLogic);
		assertNotNull(storyLogic);
		assertNotNull(userLogic);
	}

	@Test
	public void test() {
		User user = new User();
		user.setName("user002");
		user.setPassword("pwd002");
		userLogic.save(user);

		Story story = new Story();
		story.setName("name001");
		story.setStoryTypeId("storyTypeId001");
		story.setDescription("description001");
		story.setUserId(user.getId());
		storyLogic.save(story);
		likeStoryLogic.like(user.getId(), story.getId());

		assertNotNull(likeStoryLogic.getCountByStoryId(story.getId()));
		assertNotNull(likeStoryLogic.getCountByUserId(user.getId()));

		likeStoryLogic.unLike(user.getId(), story.getId());
		userLogic.delete(user);
		storyLogic.deleteById(story.getId());
	}
}
