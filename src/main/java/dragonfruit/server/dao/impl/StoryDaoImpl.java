package dragonfruit.server.dao.impl;

import dragonfruit.server.dao.StoryDao;
import dragonfruit.server.dao.common.AbstractMongoBaseDao;
import dragonfruit.server.entity.Story;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xuyh at 2017/5/15 0015 下午 14:50.
 */
@Component("storyDao")
public class StoryDaoImpl extends AbstractMongoBaseDao<Story> implements StoryDao {
	public Long getAllCount() {
		Query query = new Query();
		return getCount(query);
	}

	public List<Story> getPageByPageSize(int page, int size) {
		Query query = new Query();
		int start = page * size + 1;
		return getPage(query, start, size);
	}

	public List<Story> getPageByLikeDown(int page, int size) {
		Query query = new Query();
		Sort sort = new Sort(Sort.Direction.DESC, Story.FIELD_CODE_LIKE);
		query.with(sort);
		int start = page * size + 1;
		return getPage(query, start, size);
	}

	public List<Story> getByStoryType(String storyTypeId) {
		Query query = new Query();
		Criteria criteria = Criteria.where(Story.FIELD_CODE_USER_STORY_TYPE_ID).is(storyTypeId);
		query.addCriteria(criteria);
		return this.getList(query);
	}

	public List<Story> getByUserId(String userId) {
		Query query = new Query();
		Criteria criteria = Criteria.where(Story.FIELD_CODE_USER_ID).is(userId);
		query.addCriteria(criteria);
		return this.getList(query);
	}

	public List<Story> getByName(String name) {
		Query query = new Query();
		Criteria criteria = Criteria.where(Story.FIELD_CODE_NAME).is(name);
		query.addCriteria(criteria);
		return this.getList(query);
	}

	public boolean increaseLike(String id) {
		Story dbStory = getById(id);
		if (dbStory == null)
			return false;
		dbStory.setLike(dbStory.getLike() + 1);
		save(dbStory);
		return true;
	}

	public boolean decreaseLike(String id) {
		Story dbStory = getById(id);
		if (dbStory == null)
			return false;
		if (dbStory.getLike() == 0L)
			return true;
		dbStory.setLike(dbStory.getLike() - 1);
		save(dbStory);
		return true;
	}

	protected Class<Story> getEntityClass() {
		return Story.class;
	}
}
