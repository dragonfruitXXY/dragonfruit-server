package dragonfruit.server.dao.common;

import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by Xuyh at 2017/03/03 下午 04:02.
 */
public interface MongoBaseDao<T> {
	/**
	 * 保存对象
	 *
	 * @param t
	 * @return
	 */
	void save(T t);

	/**
	 * 根据ID删除记录
	 */
	void deleteById(String id);

	/**
	 * 根据Id从Collection中查询对象。
	 *
	 * @param id 
	 * @return
	 */
	T getById(String id);

	/**
	 * 根据条件查询集合
	 *
	 * @param query
	 * @return
	 */
	List<T> getList(Query query);

	/**
	 * 通过条件查询单个实体
	 *
	 * @param query
	 * @return
	 */
	T getOne(Query query);

	/**
	 * 通过条件进行分页查询
	 *
	 * @param query
	 * @param start
	 * @param size
	 * @return
	 */
	List<T> getPage(Query query, int start, int size);

	/**
	 * 根据条件查询库中符合记录的总数
	 *
	 * @param query
	 * @return
	 */
	Long getCount(Query query);

	/**
	 * 删除对象
	 *
	 * @param t
	 */
	WriteResult delete(T t);

	/**
	 * 删除满足条件的所有对象
	 *
	 * @param query
	 */
	WriteResult delete(Query query);

	/**
	 * 更新满足条件的第一个记录
	 *
	 * @param query
	 * @param update
	 */
	WriteResult updateFirst(Query query, Update update);

	/**
	 * 更新满足条件的所有记录
	 *
	 * @param query
	 * @param update
	 */
	WriteResult updateMulti(Query query, Update update);

	/**
	 * 查找更新,如果没有找到符合的记录,则将更新的记录插入库中
	 *
	 * @param query
	 * @param update
	 */
	WriteResult updateInsert(Query query, Update update);

	/**
	 * 创建集合
	 */
	void createCollection();

	/**
	 * 清除集合和数据
	 */
	void dropCollection();

	/**
	 * 删除索引
	 *
	 * @param name
	 */
	void dropIndex(String name);

	/**
	 * 创建索引
	 *
	 * @param index
	 */
	void createIndex(Index index);
}
