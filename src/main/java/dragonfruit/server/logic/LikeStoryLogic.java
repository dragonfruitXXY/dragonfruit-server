package dragonfruit.server.logic;

/**
 * Created by xuyh at 2017/5/15 0015 下午 18:23.
 */
public interface LikeStoryLogic {
	/**
	 * 是否已经点赞
	 *
	 * @param userId
	 * @param storyId
	 * @return
	 */
	boolean exist(String userId, String storyId);

	/**
	 * 用户点赞
	 *
	 * @param userId
	 * @param storyId
	 * @return
	 */
	boolean like(String userId, String storyId);

	/**
	 * 用户取消点赞
	 *
	 * @param userId
	 * @param storyId
	 * @return
	 */
	boolean unLike(String userId, String storyId);

	/**
	 * 获取用户点赞数
	 *
	 * @param userId
	 * @return
	 */
	Long getCountByUserId(String userId);

	/**
	 * 获取故事被点赞数
	 *
	 * @param storyId
	 * @return
	 */
	Long getCountByStoryId(String storyId);
}
