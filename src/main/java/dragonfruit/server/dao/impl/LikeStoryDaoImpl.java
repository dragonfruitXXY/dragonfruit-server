package dragonfruit.server.dao.impl;

import dragonfruit.server.dao.LikeStoryDao;
import dragonfruit.server.dao.common.AbstractMongoBaseDao;
import dragonfruit.server.entity.LikeStory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * Created by xuyh at 2017/5/15 0015 下午 17:52.
 */
@Component("likeStoryDao")
public class LikeStoryDaoImpl extends AbstractMongoBaseDao<LikeStory> implements LikeStoryDao {
	public void like(String userId, String storyId) {
		LikeStory dbLikeStory = getByUserAndStory(userId, storyId);
		if (dbLikeStory != null)
			return;
		LikeStory likeStory = new LikeStory();
		likeStory.setUserId(userId);
		likeStory.setStoryId(storyId);
		save(likeStory);
	}

	public void unLike(String userId, String storyId) {
		LikeStory dbLikeStory = getByUserAndStory(userId, storyId);
		if (dbLikeStory == null)
			return;
		deleteById(dbLikeStory.getId());
	}

	public LikeStory getByUserAndStory(String userId, String storyId) {
		Query query = new Query();
		Criteria criteria = Criteria.where(LikeStory.FIELD_CODE_USER_ID).is(userId).and(LikeStory.FIELD_CODE_STORY_ID)
				.is(storyId);
		query.addCriteria(criteria);
		return getOne(query);
	}

	public Long getCountByUserId(String userId) {
		Query query = new Query();
		Criteria criteria = Criteria.where(LikeStory.FIELD_CODE_USER_ID).is(userId);
		query.addCriteria(criteria);
		return getCount(query);
	}

	public Long getCountByStoryId(String storyId) {
		Query query = new Query();
		Criteria criteria = Criteria.where(LikeStory.FIELD_CODE_STORY_ID).is(storyId);
		query.addCriteria(criteria);
		return getCount(query);
	}

	protected Class<LikeStory> getEntityClass() {
		return LikeStory.class;
	}
}
