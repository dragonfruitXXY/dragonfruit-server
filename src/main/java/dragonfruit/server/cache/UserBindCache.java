package dragonfruit.server.cache;

import java.util.HashMap;
import java.util.Map;

import dragonfruit.server.util.RandomUtils;

/**
 * 缓存用户信息等类
 * 
 * Created by Xuyh at 2017年3月22日 下午10:53:17.
 */
public class UserBindCache {
	/**
	 * 用户登录状态缓存池，绑定之后生成随机字串用作token返回给客户端并存入该缓存池，用以之后接口进行比对等等
	 * 
	 * <pre>
	 * 	缓存池结构：
	 * 	[
	 * 		{"token":"userId"},
	 * 		{"token":"userId"}
	 * 	]
	 * </pre>
	 */
	public static Map<String, String> userTokenBindPool = new HashMap<>();

	/**
	 * 向缓存池添加登录用户,若已绑定，则直接返回token
	 * 
	 * @param userId
	 * @return token
	 */
	public static String addBoundUser(String userId) {
		String token;
		if (!isUserBound(userId)) {
			token = RandomUtils.getRandomString(20);
			userTokenBindPool.put(token, userId);
		} else {
			token = getBoundUserToken(userId);
		}
		return token;
	}

	/**
	 * 获取token对应的userId
	 * 
	 * @param token
	 * @return
	 */
	public static String getBoundUser(String token) {
		return userTokenBindPool.get(token);
	}

	/**
	 * 用户的token是否存在
	 *
	 * @param token
	 * @return
	 */
	public static boolean isTokenExist(String token) {
		if (userTokenBindPool.containsKey(token))
			return true;
		return false;
	}

	/**
	 * 删除登录用户信息，即登出
	 * 
	 * @param token
	 */
	public static void removeBoundUserToken(String token) {
		if (userTokenBindPool.containsKey(token))
			userTokenBindPool.remove(token);
	}

	/**
	 * 判断用户是否已经登录
	 * 
	 * @param userId
	 * @return
	 */
	private static boolean isUserBound(String userId) {
		for (String id : userTokenBindPool.values()) {
			if (id.equals(userId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 已登录的用户获取缓存池中的token(用value获取key)
	 * 
	 * @param userId
	 * @return
	 */
	private static String getBoundUserToken(String userId) {
		for (Map.Entry<String, String> entry : userTokenBindPool.entrySet()) {
			if (entry.getValue().equals(userId)) {
				return entry.getKey();
			}
		}
		return null;
	}
}
