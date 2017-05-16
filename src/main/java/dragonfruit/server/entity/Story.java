package dragonfruit.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 故事实体类
 *
 * Created by xuyh at 2017/5/15 0015 下午 13:09.
 */
@Document(collection = "story")
public class Story {
	public static String FIELD_CODE_ID = "id";
	public static String FIELD_CODE_USER_ID = "userId";
	public static String FIELD_CODE_USER_STORY_TYPE_ID = "storyTypeId";
	public static String FIELD_CODE_NAME = "name";
	public static String FIELD_CODE_DESCRIPTION = "description";
	public static String FIELD_CODE_LIKE = "like";

	@Id
	private String id;
	@Indexed
	private String userId;//用户id
	@Indexed
	private String storyTypeId;//故事类型id
	private String name;//故事名称
	private String description;//故事描述
	private Long like = 0l;//故事点赞数(默认为零)

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

	public String getStoryTypeId() {
		return storyTypeId;
	}

	public void setStoryTypeId(String storyTypeId) {
		this.storyTypeId = storyTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getLike() {
		return like;
	}

	public void setLike(Long like) {
		this.like = like;
	}
}
