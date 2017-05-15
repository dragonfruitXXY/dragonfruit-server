package dragonfruit.server.dao;

import dragonfruit.server.dao.common.MongoBaseDao;
import dragonfruit.server.entity.LikeStoryContent;

/**
 * Created by xuyh at 2017/5/15 0015 下午 17:51.
 */
public interface LikeStoryContentDao extends MongoBaseDao<LikeStoryContent> {
	/**
	 * 点赞
	 *
	 * @param userId
	 * @param storyContentId
	 */
	void like(String userId, String storyContentId);

	/**
	 * 取消点赞
	 *
	 * @param userId
	 * @param storyContentId
	 */
	void unLike(String userId, String storyContentId);

	/**
	 * 获取一个
	 *
	 * @param userId
	 * @param storyContentId
	 * @return
	 */
	LikeStoryContent getByUserAndStoryContent(String userId, String storyContentId);

	/**
	 * 通过用户id获取数量(用户点赞故事内容数量)
	 *
	 * @param userId
	 * @return
	 */
	Long getCountByUserId(String userId);

	/**
	 * 通过故事内容id获取数量(故事内容被点赞数量)
	 *
	 * @param storyContentId
	 * @return
	 */
	Long getCountByStoryContentId(String storyContentId);
}
