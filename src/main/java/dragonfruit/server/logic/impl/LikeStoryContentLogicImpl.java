package dragonfruit.server.logic.impl;

import dragonfruit.server.dao.LikeStoryContentDao;
import dragonfruit.server.dao.StoryContentDao;
import dragonfruit.server.logic.LikeStoryContentLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xuyh at 2017/5/15 0015 下午 18:24.
 */
@Component("likeStoryContentLogic")
public class LikeStoryContentLogicImpl implements LikeStoryContentLogic {
	@Autowired
	private LikeStoryContentDao likeStoryContentDao;

	@Autowired
	private StoryContentDao storyContentDao;

	public void setLikeStoryContentDao(LikeStoryContentDao likeStoryContentDao) {
		this.likeStoryContentDao = likeStoryContentDao;
	}

	public void setStoryContentDao(StoryContentDao storyContentDao) {
		this.storyContentDao = storyContentDao;
	}

	public boolean exist(String userId, String storyContentId) {
		return likeStoryContentDao.getByUserAndStoryContent(userId, storyContentId) != null;
	}

	public boolean like(String userId, String storyContentId) {
		if (likeStoryContentDao.getByUserAndStoryContent(userId, storyContentId) != null)
			return false;
		likeStoryContentDao.like(userId, storyContentId);
		storyContentDao.increaseLike(storyContentId);
		return true;
	}

	public boolean unLike(String userId, String storyContentId) {
		if (likeStoryContentDao.getByUserAndStoryContent(userId, storyContentId) == null)
			return false;
		likeStoryContentDao.unLike(userId, storyContentId);
		storyContentDao.decreaseLike(storyContentId);
		return true;
	}

	public Long getCountByUserId(String userId) {
		return likeStoryContentDao.getCountByUserId(userId);
	}

	public Long getCountByStoryContentId(String storyContentId) {
		return likeStoryContentDao.getCountByStoryContentId(storyContentId);
	}
}
