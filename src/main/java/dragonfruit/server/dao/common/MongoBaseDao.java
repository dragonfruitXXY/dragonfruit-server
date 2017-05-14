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
	 * <p>
	 * 需要说明的是，Mongdo自身没有主键自增机制，解决方法：
	 * <ol>
	 * <li>实体入库的时候，程序中为实体赋主键值.
	 * <li>实体入库的时候，在mongoDB中自定义函数实现主键自增机制，定义方法同js代码类似
	 * </ol>
	 * </p>
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
	 * @param id 实体对象的Id,对应Collection中记录的_id字段.
	 *          <p>
	 *          需要说明的是，Mongdo自身没有主键自增机制，解决方法：
	 *          <ol>
	 *          <li>实体入库的时候，程序中为实体赋主键值.
	 *          <li>实体入库的时候，在mongoDB中自定义函数实现主键自增机制，定义方法同js代码类似
	 *          </ol>
	 *          </p>
	 * @return 实体对象
	 */
	T getById(String id);

	/**
	 * 根据条件查询集合
	 *
	 * @param query 查询条件
	 * @return 满足条件的集合
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
	 * @param query 查询条件
	 * @param start 查询起始值 <strong> 类似mysql查询中的 limit start, size 中的 start</strong>
	 * @param size 查询大小 <strong> 类似mysql查询中的 limit start, size 中的 size</strong>
	 * @return 满足条件的集合
	 */
	List<T> getPage(Query query, int start, int size);

	/**
	 * 根据条件查询库中符合记录的总数,为分页查询服务
	 *
	 * @param query 查询条件
	 * @return 满足条件的记录总数
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
