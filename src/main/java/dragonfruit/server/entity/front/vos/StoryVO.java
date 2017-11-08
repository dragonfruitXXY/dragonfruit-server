package dragonfruit.server.entity.front.vos;

/**
 * Created by xuyh at 2017/5/15 0015 下午 19:53.
 */
public class StoryVO {
	private String id;
	private String userId;
	private String storyTypeId;
	private String name;
	private String description;
	private Long like;

	public StoryVO(String id, String userId, String storyTypeId, String name, String description, Long like) {
		this.id = id;
		this.userId = userId;
		this.storyTypeId = storyTypeId;
		this.name = name;
		this.description = description;
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

	@Override
	public String toString() {
		return "StoryVO [id=" + id + ", userId=" + userId + ", storyTypeId=" + storyTypeId + ", name=" + name
				+ ", description=" + description + ", like=" + like + "]";
	}
}
