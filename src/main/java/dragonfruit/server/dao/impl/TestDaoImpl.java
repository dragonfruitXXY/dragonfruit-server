package dragonfruit.server.dao.impl;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import dragonfruit.server.dao.TestDao;
import dragonfruit.server.dao.common.AbstractMongoBaseDao;
import dragonfruit.server.entity.Test;

import java.util.List;

/**
 * Created by xuyh at 2017/4/16 11:26.
 */
@Component("testDao")
public class TestDaoImpl extends AbstractMongoBaseDao<Test> implements TestDao {
	@Override
	public Test getByValue(String value) {
		Criteria criteria = Criteria.where("value").is(value);
		Query query = new Query();
		query.addCriteria(criteria);
		return this.getOne(query);
	}

	@Override
	protected Class<Test> getEntityClass() {
		return Test.class;
	}

	@Override
	public List<Test> getAll() {
		Query query = new Query();
		return getList(query);
	}
}
