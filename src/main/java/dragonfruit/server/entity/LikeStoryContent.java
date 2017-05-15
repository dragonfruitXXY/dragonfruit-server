package dragonfruit.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户-故事内容点赞关联对象
 *
 * Created by xuyh at 2017/5/15 0015 下午 17:25.
 */
@Document(collection = "likeStoryContent")
public class LikeStoryContent {
	public static String FIELD_CODE_ID = "id";
	public static String FIELD_CODE_USER_ID = "userId";
	public static String FIELD_CODE_STORY_CONTENT_ID = "storyContentId";

	@Id
	private String id;
	@Indexed
	private String userId;
	@Indexed
	private String storyContentId;

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

	public String getStoryContentId() {
		return storyContentId;
	}

	public void setStoryContentId(String storyContentId) {
		this.storyContentId = storyContentId;
	}
}
