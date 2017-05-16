package dragonfruit.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 故事内容实体类
 *
 * Created by xuyh at 2017/5/15 0015 下午 13:18.
 */
@Document(collection = "storyContent")
public class StoryContent {
	public static String FIELD_CODE_ID = "id";
	public static String FIELD_CODE_USER_ID = "userId";
	public static String FIELD_CODE_STORY_ID = "storyId";
	public static String FIELD_CODE_HEAD_CONTENT_ID = "headContentId";
	public static String FIELD_CODE_CONTENT = "content";
	public static String FIELD_CODE_LIKE = "like";

	@Id
	private String id;
	@Indexed
	private String userId;//用户id
	@Indexed
	private String storyId;//所属故事id
	@Indexed
	private String headContentId = null;//上一个内容id(null表示是故事的起始内容)
	private String content;//具体内容文本
	private Long like = 0L;//内容点赞数

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
}
