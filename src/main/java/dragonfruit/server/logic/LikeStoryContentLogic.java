package dragonfruit.server.logic;

/**
 * Created by xuyh at 2017/5/15 0015 下午 18:23.
 */
public interface LikeStoryContentLogic {
	/**
	 * 是否已经点赞
	 *
	 * @param userId
	 * @param storyContentId
	 * @return
	 */
	boolean exist(String userId, String storyContentId);

	/**
	 * 用户点赞
	 *
	 * @param userId
	 * @param storyContentId
	 * @return
	 */
	boolean like(String userId, String storyContentId);

	/**
	 * 用户取消点赞
	 *
	 * @param userId
	 * @param storyContentId
	 * @return
	 */
	boolean unLike(String userId, String storyContentId);

	/**
	 * 获取用户点赞数
	 *
	 * @param userId
	 * @return
	 */
	Long getCountByUserId(String userId);

	/**
	 * 获取故事内容被点赞数
	 *
	 * @param storyContentId
	 * @return
	 */
	Long getCountByStoryContentId(String storyContentId);
}
