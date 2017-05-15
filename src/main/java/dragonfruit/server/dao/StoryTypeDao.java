package dragonfruit.server.dao;

import dragonfruit.server.dao.common.MongoBaseDao;
import dragonfruit.server.entity.StoryType;

import java.util.List;

/**
 * Created by xuyh at 2017/5/15 0015 下午 14:18.
 */
public interface StoryTypeDao extends MongoBaseDao<StoryType> {
	/**
	 * 根据code获取
	 *
	 * @param code
	 * @return
	 */
	StoryType getByCode(String code);

	/**
	 * 获取所有
	 *
	 * @return
	 */
	List<StoryType> getAllTypes();
}
