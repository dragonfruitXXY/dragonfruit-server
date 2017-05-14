package dragonfruit.server.dao.impl;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Component;
import dragonfruit.server.dao.UserDao;
import dragonfruit.server.dao.common.AbstractMongoBaseDao;
import dragonfruit.server.entity.User;

@Component("userDao")
public class UserDaoImpl extends AbstractMongoBaseDao<User> implements UserDao {
	public User getByName(String name) {
		Query query = new Query();
		Criteria criteria = Criteria.where(User.FIELD_CODE_NAME).is(name);
		query.addCriteria(criteria);
		return this.getOne(query);
	}

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}
}
