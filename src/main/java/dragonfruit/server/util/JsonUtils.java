package dragonfruit.server.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * JSON-实体类转换工具
 *
 * Created by xuyh at 2017/3/3 21:03.
 */
public class JsonUtils {
	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	/**
	 * 将json字串转化为实体POJO
	 * 
	 * @param jsonStr
	 * @param tClass
	 * @return
	 */
	public static <T> Object JsonToObj(String jsonStr, Class<T> tClass) throws Exception {
		T t = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			t = objectMapper.readValue(jsonStr, tClass);
		} catch (Exception e) {
			logger.warn(String.format("Exception when parse json string, [%s]", e.getMessage()));
			throw new Exception(String.format("Exception when parse json string, [%s]", e.getMessage()), e);
		}
		return t;
	}

	/**
	 * 将实体POJO转化为JSON Object
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> JSONObject objectToJson(T obj) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr;
		try {
			jsonStr = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.warn(String.format("Exception when parse object to jsonString, [%s]", e.getMessage()));
			return null;
		}
		return new JSONObject(jsonStr);
	}

	/**
	 * 将实体POJO转化为JSON格式字串
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> String objectToJsonStr(T obj) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr;
		try {
			jsonStr = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.warn(String.format("Exception when parse object to jsonString, [%s]", e.getMessage()));
			return null;
		}
		return jsonStr;
	}

	/**
	 * 将jsonString转换成java list
	 *
	 * @param jsonString
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings({ "rawtypes" })
	public static List stringToList(String jsonString) {
		try {
			if (jsonString == null || jsonString.equals(""))
				return null;
			JSONArray array = new JSONArray(jsonString);
			return jsonArrayToList(array);
		} catch (Exception e) {
			logger.warn(String.format("Parse JsonObject Exception: [%s]", e.getMessage()));
			return null;
		}
	}

	/**
	 * 将jsonString转换成java map
	 *
	 * @param jsonString
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Map stringToMap(String jsonString) {
		try {
			if (jsonString == null || jsonString.equals(""))
				return null;
			JSONObject object = new JSONObject(jsonString);
			return objectToMap(object);
		} catch (Exception e) {
			logger.warn(String.format("Parse JsonObject Exception: [%s]", e.getMessage()));
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List jsonArrayToList(JSONArray array) {
		try {
			List list = new ArrayList();
			for (int i = 0; i < array.length(); i++) {
				Object obj = array.get(i);
				if (obj instanceof JSONObject) {
					list.add(objectToMap((JSONObject) obj));
				} else if (obj instanceof JSONArray) {
					list.add(jsonArrayToList((JSONArray) obj));
				} else {
					list.add(obj);
				}
			}
			return list;
		} catch (Exception e) {
			logger.warn(String.format("Parse JsonObject Exception: [%s]", e.getMessage()));
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map objectToMap(JSONObject object) {
		try {
			Map map = new HashMap();
			Iterator iterable = object.keys();
			while (iterable.hasNext()) {
				Object key = iterable.next();
				Object obj = object.get(key.toString());
				if (obj instanceof JSONObject) {
					map.put(key, objectToMap((JSONObject) obj));
				} else if (obj instanceof JSONArray) {
					map.put(key, jsonArrayToList((JSONArray) obj));
				} else {
					map.put(key, obj);
				}
			}
			return map;
		} catch (Exception e) {
			logger.warn(String.format("Parse JsonObject Exception: [%s]", e.getMessage()));
			return null;
		}
	}
}
