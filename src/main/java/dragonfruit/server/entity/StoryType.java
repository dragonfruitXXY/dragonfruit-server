package dragonfruit.server.entity;

import java.util.HashMap;
import java.util.Map;

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
	/**
	 * <pre>
	 * 	国际化
	 * </pre>
	 * 
	 * 语言及语言对应的名称
	 * languge=name
	 */
	private Map<String, String> nameMap;

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

	public void addName(String language, String name) {
		if (nameMap == null)
			nameMap = new HashMap<>();
		nameMap.put(language, name);
	}

	public String getName(String language) {
		return nameMap.get(language);
	}

	public Map<String, String> getNameMap() {
		return nameMap;
	}

	public void setNameMap(Map<String, String> nameMap) {
		this.nameMap = nameMap;
	}
}
