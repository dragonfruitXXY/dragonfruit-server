package dragonfruit.server.logic;

import dragonfruit.server.entity.StoryType;

import java.util.List;

/**
 * Created by xuyh at 2017/5/15 0015 下午 14:24.
 */
public interface StoryTypeLogic {
	/**
	 * 保存
	 *
	 * <pre>
	 *     code 是唯一的
	 * </pre>
	 *
	 * @param storyType
	 * @return id
	 */
	String save(StoryType storyType);

	/**
	 * 通过code获取
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
	List<StoryType> getAll();
}
