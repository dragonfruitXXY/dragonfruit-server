package dragonfruit.server.dao.impl;

import dragonfruit.server.dao.LikeStoryContentDao;
import dragonfruit.server.dao.common.AbstractMongoBaseDao;
import dragonfruit.server.entity.LikeStoryContent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * Created by xuyh at 2017/5/15 0015 下午 17:53.
 */
@Component("likeStoryContentDao")
public class LikeStoryContentDaoImpl extends AbstractMongoBaseDao<LikeStoryContent> implements LikeStoryContentDao {
	public void like(String userId, String storyContentId) {
		LikeStoryContent dbLikeStoryContent = getByUserAndStoryContent(userId, storyContentId);
		if (dbLikeStoryContent != null)
			return;
		LikeStoryContent likeStoryContent = new LikeStoryContent();
		likeStoryContent.setUserId(userId);
		likeStoryContent.setStoryContentId(storyContentId);
		save(likeStoryContent);
	}

	public void unLike(String userId, String storyContentId) {
		LikeStoryContent dbLikeStoryContent = getByUserAndStoryContent(userId, storyContentId);
		if (dbLikeStoryContent == null)
			return;
		deleteById(dbLikeStoryContent.getId());
	}

	public LikeStoryContent getByUserAndStoryContent(String userId, String storyContentId) {
		Query query = new Query();
		Criteria criteria = Criteria.where(LikeStoryContent.FIELD_CODE_USER_ID).is(userId)
				.and(LikeStoryContent.FIELD_CODE_STORY_CONTENT_ID).is(storyContentId);
		query.addCriteria(criteria);
		return getOne(query);
	}

	public Long getCountByUserId(String userId) {
		Query query = new Query();
		Criteria criteria = Criteria.where(LikeStoryContent.FIELD_CODE_USER_ID).is(userId);
		query.addCriteria(criteria);
		return getCount(query);
	}

	public Long getCountByStoryContentId(String storyContentId) {
		Query query = new Query();
		Criteria criteria = Criteria.where(LikeStoryContent.FIELD_CODE_STORY_CONTENT_ID).is(storyContentId);
		query.addCriteria(criteria);
		return getCount(query);
	}

	protected Class<LikeStoryContent> getEntityClass() {
		return LikeStoryContent.class;
	}
}
