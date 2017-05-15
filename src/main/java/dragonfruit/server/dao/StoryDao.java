package dragonfruit.server.dao;

import dragonfruit.server.dao.common.MongoBaseDao;
import dragonfruit.server.entity.Story;

import java.util.List;

/**
 * Created by xuyh at 2017/5/15 0015 下午 14:44.
 */
public interface StoryDao extends MongoBaseDao<Story> {
	/**
	 * 获取总数
	 *
	 * @return
	 */
	Long getAllCount();

	/**
	 * 分页查询,无查询条件
	 *
	 * @param page
	 * @param size
	 * @return
	 */
	List<Story> getPageByPageSize(int page, int size);

	/**
	 * 分页查询,根据点赞数量降序排序
	 *
	 * @param page
	 * @param size
	 * @return
	 */
	List<Story> getPageByLikeDown(int page, int size);

	/**
	 * 通过故事类型获取
	 *
	 * @param storyTypeId
	 * @return
	 */
	List<Story> getByStoryType(String storyTypeId);

	/**
	 * 通过用户id获取
	 *
	 * @param userId
	 * @return
	 */
	List<Story> getByUserId(String userId);

	/**
	 * 通过name获取
	 *
	 * <pre>
	 *     story允许同名
	 * </pre>
	 *
	 * @param name
	 * @return
	 */
	List<Story> getByName(String name);

	/**
	 * 故事点赞数量+1
	 *
	 * @param id
	 * @return
	 */
	boolean increaseLike(String id);

	/**
	 * 故事点赞数量-1
	 *
	 * @param id
	 * @return
	 */
	boolean decreaseLike(String id);
}
