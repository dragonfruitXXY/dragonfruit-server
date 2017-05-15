package dragonfruit.server.entity.front.vos;

/**
 * Created by xuyh at 2017/5/15 0015 下午 19:53.
 */
public class StoryTypeVO {
	private String id;
	private String code;
	private String name;

	public StoryTypeVO(String id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
