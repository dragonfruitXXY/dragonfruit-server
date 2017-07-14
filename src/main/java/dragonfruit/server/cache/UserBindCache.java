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
	 * 用户登录状态缓存池，绑定之后生成随机字串用作tocken返回给客户端并存入该缓存池，用以之后接口进行比对等等
	 * 
	 * <pre>
	 * 	缓存池结构：
	 * 	[
	 * 		{"tocken":"userId"},
	 * 		{"tocken":"userId"}
	 * 	]
	 * </pre>
	 */
	public static Map<String, String> userTockenBindPool = new HashMap<>();

	/**
	 * 向缓存池添加登录用户,若已绑定，则直接返回tocken
	 * 
	 * @param userId
	 * @return tocken
	 */
	public static String addBoundUser(String userId) {
		String tocken;
		if (!isUserBound(userId)) {
			tocken = RandomUtils.getRandomString(20);
			userTockenBindPool.put(tocken, userId);
		} else {
			tocken = getBoundUserTocken(userId);
		}
		return tocken;
	}

	/**
	 * 获取tocken对应的userId
	 * 
	 * @param tocken
	 * @return
	 */
	public static String getBoundUser(String tocken) {
		return userTockenBindPool.get(tocken);
	}

	/**
	 * 用户的tocken是否存在
	 *
	 * @param tocken
	 * @return
	 */
	public static boolean isTockenExist(String tocken) {
		if (userTockenBindPool.containsKey(tocken))
			return true;
		return false;
	}

	/**
	 * 删除登录用户信息，即登出
	 * 
	 * @param tocken
	 */
	public static void removeBoundUserTocken(String tocken) {
		if (userTockenBindPool.containsKey(tocken))
			userTockenBindPool.remove(tocken);
	}

	/**
	 * 判断用户是否已经登录
	 * 
	 * @param userId
	 * @return
	 */
	private static boolean isUserBound(String userId) {
		for (String id : userTockenBindPool.values()) {
			if (id.equals(userId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 已登录的用户获取缓存池中的tocken(用value获取key)
	 * 
	 * @param userId
	 * @return
	 */
	private static String getBoundUserTocken(String userId) {
		for (String key : userTockenBindPool.keySet()) {
			if (userTockenBindPool.get(key).equals(userId)) {
				return key;
			}
		}
		return null;
	}
}
