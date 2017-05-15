package dragonfruit.server.logic;

import dragonfruit.server.entity.Story;

import java.util.List;

/**
 * Created by xuyh at 2017/5/15 0015 下午 14:53.
 */
public interface StoryLogic {
	/**
	 * 保存,修改
	 *
	 * @param story
	 * @return
	 */
	String save(Story story);

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	boolean deleteById(String id);

	/**
	 * 通过id获取
	 *
	 * @param id
	 * @return
	 */
	Story getById(String id);

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
}
