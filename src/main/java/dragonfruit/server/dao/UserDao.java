package dragonfruit.server.dao;

import dragonfruit.server.dao.common.MongoBaseDao;
import dragonfruit.server.entity.User;

/**
 * 
 * Created by xuyh at 2017年3月3日 下午5:40:58
 *
 */
public interface UserDao extends MongoBaseDao<User> {
	/**
	 * 根据name获取User
	 * 
	 * @param name
	 * @return
	 */
	User getByName(String name);
}
