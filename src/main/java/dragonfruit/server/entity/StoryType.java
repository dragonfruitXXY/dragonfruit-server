package dragonfruit.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by xuyh at 2017/5/15 0015 下午 13:48.
 */
@Document(collection = "storyType")
public class StoryType {
	public static String FIELD_CODE_ID = "id";
	public static String FIELD_CODE_CODE = "code";
	public static String FIELD_CODE_NAME = "name";

	@Id
	private String id;
	@Indexed
	private String code;
	private String name;

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
