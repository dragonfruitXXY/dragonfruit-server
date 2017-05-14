package dragonfruit.server.dao;

import dragonfruit.server.dao.common.MongoBaseDao;
import dragonfruit.server.entity.Test;

import java.util.List;

/**
 * Created by xuyh at 2017/4/16 11:25.
 */
public interface TestDao extends MongoBaseDao<Test> {
	/**
	 * 获取所有
	 * 
	 * @return
	 */
	List<Test> getAll();

	/**
	 * 
	 * @param value
	 * @return
	 */
	Test getByValue(String value);
}
