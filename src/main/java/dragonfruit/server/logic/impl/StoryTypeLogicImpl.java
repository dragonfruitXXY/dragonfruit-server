package dragonfruit.server.logic.impl;

import dragonfruit.server.dao.StoryTypeDao;
import dragonfruit.server.entity.StoryType;
import dragonfruit.server.logic.StoryTypeLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xuyh at 2017/5/15 0015 下午 14:26.
 */
@Component("storyTypeLogic")
public class StoryTypeLogicImpl implements StoryTypeLogic {
	@Autowired
	private StoryTypeDao storyTypeDao;

	public void setStoryTypeDao(StoryTypeDao storyTypeDao) {
		this.storyTypeDao = storyTypeDao;
	}

	public String save(StoryType storyType) {
		if (storyType == null)
			return null;
		StoryType dbStorytype = storyTypeDao.getByCode(storyType.getCode());
		if (dbStorytype != null)
			storyType.setId(dbStorytype.getId());
		storyTypeDao.save(storyType);
		return storyType.getId();
	}

	public StoryType getByCode(String code) {
		return storyTypeDao.getByCode(code);
	}

	public List<StoryType> getAll() {
		return storyTypeDao.getAllTypes();
	}
}
