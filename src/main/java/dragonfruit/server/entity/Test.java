package dragonfruit.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 数据库测试实体类
 *
 * Created by xuyh at 2017/4/16 11:15.
 */
@Document(collection = "test")
public class Test {
	@Id
	private String id;
	private String value;

	public Test() {
	}

	public Test(String id, String value) {
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
