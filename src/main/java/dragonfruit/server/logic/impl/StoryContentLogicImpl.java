package dragonfruit.server.logic.impl;

import dragonfruit.server.dao.StoryContentDao;
import dragonfruit.server.entity.StoryContent;
import dragonfruit.server.logic.StoryContentLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xuyh at 2017/5/15 0015 下午 15:21.
 */
@Component("storyContentLogic")
public class StoryContentLogicImpl implements StoryContentLogic {
	@Autowired
	private StoryContentDao storyContentDao;

	public void setStoryContentDao(StoryContentDao storyContentDao) {
		this.storyContentDao = storyContentDao;
	}

	public String save(StoryContent storyContent) {
		if (storyContent.getHeadContentId() == null) {//保存为故事头内容
			if (getHeadContent(storyContent.getStoryId()) != null)//若已经存在头内容,则不予保存
				return null;
		}
		if (getById(storyContent.getHeadContentId()) == null)//若不存在父内容
			return null;
		storyContentDao.save(storyContent);
		return storyContent.getId();
	}

	public StoryContent getById(String id) {
		return storyContentDao.getById(id);
	}

	public boolean deleteById(String id) {
		//子节点指向其父节点
		StoryContent storyContent = getById(id);
		if (storyContent.getHeadContentId() == null)//是根节点,不删
			return false;
		List<StoryContent> childs = getByHeadContentId(id);
		String parent = storyContent.getHeadContentId();
		for (StoryContent child : childs) {
			child.setHeadContentId(parent);
			save(child);
		}
		//删除该节点
		try {
			storyContentDao.deleteById(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean deleteByStoryId(String storyId) {
		return storyContentDao.deleteByStoryId(storyId);
	}

	public StoryContent getHeadContent(String storyId) {
		return storyContentDao.getHeadContent(storyId);
	}

	public List<StoryContent> getByUserId(String userId) {
		return storyContentDao.getByUserId(userId);
	}

	public List<StoryContent> getByUserAndStory(String userId, String storyId) {
		return storyContentDao.getByUserAndStory(userId, storyId);
	}

	public List<StoryContent> getByHeadContentId(String headContentId) {
		return storyContentDao.getByHeadContentId(headContentId);
	}
}
