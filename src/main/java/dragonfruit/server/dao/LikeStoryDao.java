package dragonfruit.server.dao;

import dragonfruit.server.dao.common.MongoBaseDao;
import dragonfruit.server.entity.LikeStory;

/**
 * Created by xuyh at 2017/5/15 0015 下午 17:51.
 */
public interface LikeStoryDao extends MongoBaseDao<LikeStory> {
	/**
	 * 点赞
	 *
	 * @param userId
	 * @param storyId
	 */
	void like(String userId, String storyId);

	/**
	 * 取消点赞
	 *
	 * @param userId
	 * @param storyId
	 */
	void unLike(String userId, String storyId);

	/**
	 * 获取一个
	 *
	 * @param userId
	 * @param storyId
	 * @return
	 */
	LikeStory getByUserAndStory(String userId, String storyId);

	/**
	 * 通过用户id获取数量(用户点赞故事数量)
	 *
	 * @param userId
	 * @return
	 */
	Long getCountByUserId(String userId);

	/**
	 * 通过故事id获取数量(故事被点赞数量)
	 *
	 * @param storyId
	 * @return
	 */
	Long getCountByStoryId(String storyId);
}
