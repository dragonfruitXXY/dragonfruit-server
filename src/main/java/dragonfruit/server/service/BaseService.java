package dragonfruit.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dragonfruit.server.Main;
import dragonfruit.server.entity.Test;
import dragonfruit.server.entity.front.ResultMessage;
import dragonfruit.server.logic.TestLogic;
import dragonfruit.server.util.JsonUtils;
import dragonfruit.server.util.RandomUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Created by Xuyh at 2017年2月28日 下午10:31:32.
 */
@Path("/base")
public class BaseService {
	private Logger logger = LoggerFactory.getLogger(BaseService.class);

	private TestLogic getTestLogic() {
		return (TestLogic) Main.getBean("testLogic");
	}

	/**
	 * 测试方法
	 *
	 * @return
	 */
	@GET
	@Path("/test")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String test() {
		return "RestFul service succeeded!";
	}

	/**
	 * 插入测试数据
	 * 
	 * @return
	 */
	@GET
	@Path("/test/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String testInsertValue() {
		Test test = new Test();
		test.setValue(RandomUtils.getRandomString(20));
		String id;
		try {
			id = getTestLogic().save(test);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("operation failed!"));
		}
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("id", id);
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(dataMap, "Operation succeeded!"));
	}

	/**
	 * 获取测试数据
	 * 
	 * @return
	 */
	@GET
	@Path("test/getValues")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getTestValues() {
		List<Test> testList = null;
		try {
			testList = getTestLogic().getAll();
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("operation failed!"));
		}
		if (testList == null || testList.isEmpty())
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("operation failed!"));
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(testList, "Operation succeeded!"));
	}
}
