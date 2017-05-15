package dragonfruit.server.dao;

import dragonfruit.server.dao.common.MongoBaseDao;
import dragonfruit.server.entity.StoryContent;

import java.util.List;

/**
 * Created by xuyh at 2017/5/15 0015 下午 15:04.
 */
public interface StoryContentDao extends MongoBaseDao<StoryContent> {
	/**
	 * 通过故事id删除
	 *
	 * @param storyId
	 * @return
	 */
	boolean deleteByStoryId(String storyId);

	/**
	 * 获取故事的第一个内容节点
	 *
	 * @param storyId
	 * @return
	 */
	StoryContent getHeadContent(String storyId);

	/**
	 * 通过用户id获取
	 *
	 * @param userId
	 * @return
	 */
	List<StoryContent> getByUserId(String userId);

	/**
	 * 通过用户id,故事id获取
	 *
	 * @param userId
	 * @param storyId
	 * @return
	 */
	List<StoryContent> getByUserAndStory(String userId, String storyId);

	/**
	 * 通过父内容节点获取子内容节点
	 *
	 * @param headContentId
	 * @return
	 */
	List<StoryContent> getByHeadContentId(String headContentId);

	/**
	 * 内容点赞数量+1
	 *
	 * @param id
	 * @return
	 */
	boolean increaseLike(String id);

	/**
	 * 故事内容点赞数量-1
	 *
	 * @param id
	 * @return
	 */
	boolean decreaseLike(String id);
}
