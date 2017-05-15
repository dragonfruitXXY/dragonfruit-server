package dragonfruit.server.dao.common;

import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * mongoDB DAO 层抽象实现
 *
 * Created by Xuyh at 2017/03/03 下午 04:02.
 */
public abstract class AbstractMongoBaseDao<T> implements MongoBaseDao<T> {
	public String FIELD_ID = "_id";

	@Autowired
	protected MongoTemplate mongoTemplate;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public void save(T t) {
		this.mongoTemplate.save(t);
	}

	public T getById(String id) {
		Query query = new Query();
		Criteria criteria = Criteria.where(FIELD_ID).is(new ObjectId(id));
		query.addCriteria(criteria);
		return this.mongoTemplate.findOne(query, getEntityClass());
	}

	public List<T> getList(Query query) {
		return this.mongoTemplate.find(query, getEntityClass());
	}

	public T getOne(Query query) {
		return this.mongoTemplate.findOne(query, getEntityClass());
	}

	public List<T> getPage(Query query, int start, int size) {
		query.skip(start);
		query.limit(size);
		return this.mongoTemplate.find(query, getEntityClass());
	}

	public Long getCount(Query query) {
		return this.mongoTemplate.count(query, getEntityClass());
	}

	public void deleteById(String id) {
		Query query = new Query();
		Criteria criteria = Criteria.where(FIELD_ID).is(new ObjectId(id));
		query.addCriteria(criteria);
		T t = this.getOne(query);
		if (t != null) {
			this.mongoTemplate.remove(t);
		}
	}

	public WriteResult delete(T t) {
		return this.mongoTemplate.remove(t);
	}

	public WriteResult delete(Query query) {
		return this.mongoTemplate.remove(query, getEntityClass());
	}

	public WriteResult updateFirst(Query query, Update update) {
		return this.mongoTemplate.updateFirst(query, update, getEntityClass());
	}

	public WriteResult updateMulti(Query query, Update update) {
		return this.mongoTemplate.updateMulti(query, update, getEntityClass());
	}

	public WriteResult updateInsert(Query query, Update update) {
		return this.mongoTemplate.upsert(query, update, getEntityClass());
	}

	public void createCollection() {
		this.mongoTemplate.createCollection(getEntityClass());
	}

	public void dropCollection() {
		this.mongoTemplate.dropCollection(getEntityClass());
	}

	public void dropIndex(String name) {
		IndexOperations operations = this.mongoTemplate.indexOps(getEntityClass());
		operations.dropIndex(name);
	}

	public void createIndex(Index index) {
		IndexOperations operations = this.mongoTemplate.indexOps(getEntityClass());
		operations.ensureIndex(index);
	}

	/**
	 * 
	 * <pre>
	 * protected Class<Student> getEntityClass() { 
	 *   return Student.class; 
	 * }
	 * 
	 * <pre/>
	 *
	 * @return 业务对象类
	 */
	protected abstract Class<T> getEntityClass();
}
