package dragonfruit.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户-故事点赞关联对象
 *
 * Created by xuyh at 2017/5/15 0015 下午 17:23.
 */
@Document(collection = "likeStory")
public class LikeStory {
	public static String FIELD_CODE_ID = "id";
	public static String FIELD_CODE_USER_ID = "userId";
	public static String FIELD_CODE_STORY_ID = "storyId";

	@Id
	private String id;
	@Indexed
	private String userId;
	@Indexed
	private String storyId;

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
}
