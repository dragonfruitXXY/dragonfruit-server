package dragonfruit.server.logic;

import dragonfruit.server.entity.StoryContent;

import java.util.List;

/**
 * Created by xuyh at 2017/5/15 0015 下午 15:21.
 */
public interface StoryContentLogic {
	/**
	 * 保存
	 *
	 * @param storyContent
	 * @return
	 */
	String save(StoryContent storyContent);

	/**
	 * 通过id获取内容
	 *
	 * @param id
	 * @return
	 */
	StoryContent getById(String id);

	/**
	 * 通过id删除
	 *
	 * XXX 注意删除内容节点需要将所有指向此节点的内容节点headContent指向其父节点
	 *
	 * @param id
	 * @return
	 */
	boolean deleteById(String id);

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
}
