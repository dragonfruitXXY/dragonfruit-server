package dragonfruit.server.service.cache;

import dragonfruit.server.entity.UserRegister;
import dragonfruit.server.util.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户注册待验证缓存
 *
 * <pre>
 *     缓存未经过验证的注册信息
 *
 *     验证方式：邮箱， 短信(暂时不实现)
 *
 *     缓存清理逻辑
 *     (1)缓存超时时间设定为 30min， 之后缓存会清除该注册信息
 *     (2)注册验证成功同样会清除注册信息(定时任务执行)
 *
 *     清理方式：
 *     定时任务，一分钟执行一次，判断(1)时间是否超出30min(2)是否注册验证成功
 * </pre>
 * 
 * Created by xuyh at 2017/7/7 14:18.
 */
@Component("userRegisterCache")
public class UserRegisterCache {
	/**
	 * 30分钟
	 */
	private static long thirtyMinutes = 30 * 60 * 1000;
	/**
	 * 注册信息缓存池
	   *
	   * 结构：
	   * userName-UserRegister
	 */
	private static Map<String, UserRegister> userRegisterCache = new ConcurrentHashMap<>();

	/**
	 * 判断未验证的用户注册缓存中是否存在userName
	 *
	 * @param userName
	 * @return
	 */
	public static boolean isCacheUserNameExists(String userName) {
		if (userRegisterCache.keySet().contains(userName))
			return true;
		return false;
	}

	/**
	 * 缓存添加用户注册信息
	 * 
	 * @param userRegister
	 */
	public static void addUserRegister(UserRegister userRegister) {
		userRegisterCache.put(userRegister.getName(), userRegister);
	}

	/**
	 * 获取缓存中的注册用户信息
	 *
	 * @param userName
	 * @return
	 */
	public static UserRegister getUserRegisterCache(String userName) {
		return userRegisterCache.get(userName);
	}

	/**
	 * 注册验证
	 *
	 * @param userName
	 * @param verificationCode
	 * @return
	 */
	public static boolean verify(String userName, String verificationCode) {
		UserRegister userRegister = userRegisterCache.get(userName);
		if (userRegister == null)
			return false;
		return userRegister.verify(userName, verificationCode);
	}

	/**
	 * 清理缓存
	   * 
	   * <pre>
	   *     扫描缓存中的数据判断是否超过30min,超过执行清理,注册验证成功的一并清理
	   * </pre>
	 */
	@Scheduled(cron = "* 0/1 * * * *")
	public void clearCache() {
		// TODO: 2017/7/7  测试
		for (String userName : userRegisterCache.keySet()) {
			UserRegister userRegister = userRegisterCache.get(userName);
			//经过验证
			if (userRegister.isVerified()) {
				userRegisterCache.remove(userName);
				continue;
			}
			//超时
			long submitTimeStamp = userRegister.getSubmitTime().getTime();
			long currentTimeStamp = DateUtils.getTimeStamp(0);
			if (currentTimeStamp - submitTimeStamp >= thirtyMinutes) {
				userRegisterCache.remove(userName);
				continue;
			}
		}
	}
}
