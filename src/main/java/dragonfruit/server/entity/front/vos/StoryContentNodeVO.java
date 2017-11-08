package dragonfruit.server.entity.front.vos;

import java.util.ArrayList;
import java.util.List;

/**
 * 树状故事内容结构
 *
 * Created by xuyh at 2017/5/15 0015 下午 19:56.
 */
public class StoryContentNodeVO {
	private String id;
	private String userId;//用户id
	private String storyId;//所属故事id
	private String headContentId = null;//上一个内容id(null表示是故事的起始内容)
	private String content;//具体内容文本
	private Long like;//内容点赞数

	private List<StoryContentNodeVO> childNodeList = new ArrayList<>();

	public StoryContentNodeVO(String id, String userId, String storyId, String headContentId, String content, Long like) {
		this.id = id;
		this.userId = userId;
		this.storyId = storyId;
		this.headContentId = headContentId;
		this.content = content;
		this.like = like;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}

	public String getHeadContentId() {
		return headContentId;
	}

	public void setHeadContentId(String headContentId) {
		this.headContentId = headContentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getLike() {
		return like;
	}

	public void setLike(Long like) {
		this.like = like;
	}

	public List<StoryContentNodeVO> getChildNodeList() {
		return childNodeList;
	}

	public void setChildNodeList(List<StoryContentNodeVO> childNodeList) {
		this.childNodeList = childNodeList;
	}

	public void addChild(StoryContentNodeVO storyContentNodeVO) {
		childNodeList.add(storyContentNodeVO);
	}

	public void removeChild(StoryContentNodeVO storyContentNodeVO) {
		if (childNodeList.contains(storyContentNodeVO))
			childNodeList.remove(storyContentNodeVO);
	}

	@Override
	public String toString() {
		return "StoryContentNodeVO [id=" + id + ", userId=" + userId + ", storyId=" + storyId + ", headContentId="
				+ headContentId + ", content=" + content + ", like=" + like + ", childNodeList=" + childNodeList + "]";
	}
}
