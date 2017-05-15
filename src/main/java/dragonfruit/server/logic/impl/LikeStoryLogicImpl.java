package dragonfruit.server.logic.impl;

import dragonfruit.server.dao.LikeStoryDao;
import dragonfruit.server.dao.StoryDao;
import dragonfruit.server.logic.LikeStoryLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xuyh at 2017/5/15 0015 下午 18:23.
 */
@Component("likeStoryLogic")
public class LikeStoryLogicImpl implements LikeStoryLogic {
	@Autowired
	private LikeStoryDao likeStoryDao;

	@Autowired
	private StoryDao storyDao;

	public void setLikeStoryDao(LikeStoryDao likeStoryDao) {
		this.likeStoryDao = likeStoryDao;
	}

	public void setStoryDao(StoryDao storyDao) {
		this.storyDao = storyDao;
	}

	public boolean exist(String userId, String storyId) {
		return likeStoryDao.getByUserAndStory(userId, storyId) != null;
	}

	public boolean like(String userId, String storyId) {
		if (likeStoryDao.getByUserAndStory(userId, storyId) != null)
			return false;
		likeStoryDao.like(userId, storyId);
		storyDao.increaseLike(storyId);
		return true;
	}

	public boolean unLike(String userId, String storyId) {
		if (likeStoryDao.getByUserAndStory(userId, storyId) == null)
			return false;
		likeStoryDao.unLike(userId, storyId);
		storyDao.decreaseLike(storyId);
		return true;
	}

	public Long getCountByUserId(String userId) {
		return likeStoryDao.getCountByUserId(userId);
	}

	public Long getCountByStoryId(String storyId) {
		return likeStoryDao.getCountByStoryId(storyId);
	}
}
