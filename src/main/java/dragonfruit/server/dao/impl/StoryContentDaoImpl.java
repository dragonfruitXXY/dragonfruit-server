package dragonfruit.server.dao.impl;

import com.mongodb.WriteResult;
import dragonfruit.server.dao.StoryContentDao;
import dragonfruit.server.dao.common.AbstractMongoBaseDao;
import dragonfruit.server.entity.StoryContent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xuyh at 2017/5/15 0015 下午 15:16.
 */
@Component("storyContentDao")
public class StoryContentDaoImpl extends AbstractMongoBaseDao<StoryContent> implements StoryContentDao {
	public boolean deleteByStoryId(String storyId) {
		Query query = new Query();
		Criteria criteria = Criteria.where(StoryContent.FIELD_CODE_STORY_ID).is(storyId);
		query.addCriteria(criteria);
		WriteResult writeResult = delete(query);
		return writeResult.getN() >= 0;
	}

	public StoryContent getHeadContent(String storyId) {
		Query query = new Query();
		Criteria criteria = Criteria.where(StoryContent.FIELD_CODE_STORY_ID).is(storyId)
				.and(StoryContent.FIELD_CODE_HEAD_CONTENT_ID).is(null);
		query.addCriteria(criteria);
		return getOne(query);
	}

	public List<StoryContent> getByUserId(String userId) {
		Query query = new Query();
		Criteria criteria = Criteria.where(StoryContent.FIELD_CODE_USER_ID).is(userId);
		query.addCriteria(criteria);
		return getList(query);
	}

	public List<StoryContent> getByUserAndStory(String userId, String storyId) {
		Query query = new Query();
		Criteria criteria = Criteria.where(StoryContent.FIELD_CODE_USER_ID).is(userId).and(StoryContent.FIELD_CODE_STORY_ID)
				.is(storyId);
		query.addCriteria(criteria);
		return getList(query);
	}

	public List<StoryContent> getByHeadContentId(String headContentId) {
		Query query = new Query();
		Criteria criteria = Criteria.where(StoryContent.FIELD_CODE_HEAD_CONTENT_ID).is(headContentId);
		query.addCriteria(criteria);
		return getList(query);
	}

	public boolean increaseLike(String id) {
		StoryContent dbStoryContent = getById(id);
		if (dbStoryContent == null)
			return false;
		dbStoryContent.setLike(dbStoryContent.getLike() + 1);
		save(dbStoryContent);
		return true;
	}

	public boolean decreaseLike(String id) {
		StoryContent dbStoryContent = getById(id);
		if (dbStoryContent == null)
			return false;
		dbStoryContent.setLike(dbStoryContent.getLike() - 1);
		save(dbStoryContent);
		return true;
	}

	protected Class<StoryContent> getEntityClass() {
		return StoryContent.class;
	}
}
