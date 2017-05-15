package dragonfruit.server.dao.impl;

import dragonfruit.server.dao.StoryTypeDao;
import dragonfruit.server.dao.common.AbstractMongoBaseDao;
import dragonfruit.server.entity.StoryType;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xuyh at 2017/5/15 0015 下午 14:20.
 */
@Component("storyTypeDao")
public class StoryTypeDaoImpl extends AbstractMongoBaseDao<StoryType> implements StoryTypeDao {
	public StoryType getByCode(String code) {
		Query query = new Query();
		Criteria criteria = Criteria.where(StoryType.FIELD_CODE_CODE).is(code);
		query.addCriteria(criteria);
		return this.getOne(query);
	}

	public List<StoryType> getAllTypes() {
		Query query = new Query();
		return this.getList(query);
	}

	protected Class<StoryType> getEntityClass() {
		return StoryType.class;
	}
}
