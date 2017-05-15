package dragonfruit.server.logic.impl;

import dragonfruit.server.dao.StoryContentDao;
import dragonfruit.server.dao.StoryDao;
import dragonfruit.server.entity.Story;
import dragonfruit.server.logic.StoryLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xuyh at 2017/5/15 0015 下午 14:59.
 */
@Component("storyLogic")
public class StoryLogicImpl implements StoryLogic {
	@Autowired
	private StoryDao storyDao;

	@Autowired
	private StoryContentDao storyContentDao;

	public void setStoryDao(StoryDao storyDao) {
		this.storyDao = storyDao;
	}

	public void setStoryContentDao(StoryContentDao storyContentDao) {
		this.storyContentDao = storyContentDao;
	}

	public String save(Story story) {
		storyDao.save(story);
		return story.getId();
	}

	public Story getById(String id) {
		return storyDao.getById(id);
	}

	public boolean deleteById(String id) {
		try {
			storyDao.deleteById(id);
			//删除关联内容
			storyContentDao.deleteByStoryId(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Long getAllCount() {
		return storyDao.getAllCount();
	}

	public List<Story> getPageByPageSize(int page, int size) {
		return storyDao.getPageByPageSize(page, size);
	}

	public List<Story> getPageByLikeDown(int page, int size) {
		return storyDao.getPageByLikeDown(page, size);
	}

	public List<Story> getByStoryType(String storyTypeId) {
		return storyDao.getByStoryType(storyTypeId);
	}

	public List<Story> getByUserId(String userId) {
		return storyDao.getByUserId(userId);
	}

	public List<Story> getByName(String name) {
		return storyDao.getByName(name);
	}
}
