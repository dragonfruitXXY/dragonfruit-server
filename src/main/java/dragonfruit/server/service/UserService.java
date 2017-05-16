package dragonfruit.server.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dragonfruit.server.Main;
import dragonfruit.server.entity.*;
import dragonfruit.server.entity.front.ResultMessage;
import dragonfruit.server.entity.front.vos.UserVO;
import dragonfruit.server.logic.*;
import dragonfruit.server.service.cache.UserBindCache;
import dragonfruit.server.util.JsonUtils;

/**
 *
 * 
 * Created by Xuyh at 2017年3月22日 下午8:23:24.
 */
@Path("/user")
public class UserService {
	private Logger logger = LoggerFactory.getLogger(UserService.class);

	private UserLogic getUserLogic() {
		return (UserLogic) Main.getBean("userLogic");
	}

	/**
	 *
	 * 添加,修改用户信息
	 * 
	 * <pre>
	 * 	如果添加成功即认为用户登录成功，将用户信息返回给客户端
	 * 
	 * 	返回参数：
	 * 	{"result":true/false, "message":"", "data":{"userId":"", "tocken":""}}
	 * 	
	 * 	之后的请求需要在cookie中设置tocken
	 * </pre>
	 *
	 * @return
	 */
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addUser(String json) {
		User user = null;
		String userId = "";
		try {
			user = (User) JsonUtils.JsonToObj(json, User.class);
			userId = getUserLogic().save(user);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult(e.getMessage()));
		}
		if (userId != null) {
			String tocken = UserBindCache.addBoundUser(userId);
			Map<String, String> data = new HashMap<>();
			data.put("userId", userId);
			data.put("tocken", tocken);
			return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(data, "Save user succeeded!"));
		} else {
			return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult("Save user failed!"));
		}
	}

	/**
	 * 用户登录,成功之后返回用户详情,供客户端使用
	 * 
	 * <pre>
	 * 	请求参数：
	 * 	{"name":"", "password":""}
	 * 
	 * 	返回参数：
	 * 	{"result":true/false, "message":"", "data":{"userId":"", "tocken":""}}
	 * 
	 * 	之后的请求需要在cookie中设置tocken
	 * </pre>
	 * 
	 * @param json
	 * @return
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String bindUser(String json) {
		@SuppressWarnings("unchecked")
		Map<String, String> bindInfos = JsonUtils.stringToMap(json);
		String userName = bindInfos.get("name");
		String userPwd = bindInfos.get("password");
		User user = null;
		try {
			user = getUserLogic().getByName(userName);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult(e.getMessage()));
		}
		if (user == null || user.getId() == null)
			return JsonUtils
					.objectToJsonStr(ResultMessage.wrapFailureResult(String.format("User [%s] does not exists!", userName)));
		if (!user.getPassword().equals(userPwd))
			return JsonUtils.objectToJsonStr(ResultMessage
					.wrapFailureResult(String.format("Bind failed! Password for user [%s] does not match!", userName)));
		String userId = user.getId();
		String tocken = UserBindCache.addBoundUser(userId);
		Map<String, String> data = new HashMap<>();
		data.put("userId", userId);
		data.put("tocken", tocken);
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(data, null));
	}

	/**
	 * 用户登出
	 * 
	 * <pre>
	 * 需要在Cookie中设置tocken
	 * </pre>
	 * 
	 * @param tocken
	 * @return
	 */
	@GET
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String unbindUser(@CookieParam("tocken") String tocken) {
		String userId = UserBindCache.getBoundUser(tocken);
		if (userId != null) {
			UserBindCache.removeBoundUserTocken(tocken);
			return JsonUtils
					.objectToJsonStr(ResultMessage.wrapSuccessResult(String.format("User [%s] logout succeeded!", userId)));
		} else {
			return JsonUtils
					.objectToJsonStr(ResultMessage.wrapFailureResult(String.format("User [%s] logout failed!", userId)));
		}
	}

	/**
	 * 注销(删除)用户
	 * 
	 * @param tocken
	 * @return
	 */
	@GET
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteUser(@CookieParam("tocken") String tocken) {
		String userId = UserBindCache.getBoundUser(tocken);
		if (userId != null) {
			UserBindCache.removeBoundUserTocken(tocken);
			try {
				getUserLogic().deleteById(userId);
			} catch (Exception e) {
				logger.warn(e.getMessage());
				return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult(e.getMessage()));
			}
			return JsonUtils
					.objectToJsonStr(ResultMessage.wrapSuccessResult(String.format("Delete user [%s] succeeded!", userId)));
		} else {
			return JsonUtils
					.objectToJsonStr(ResultMessage.wrapFailureResult(String.format("Delete user [%s] failed!", userId)));
		}
	}

	/**
	 * 获取用户详情
	 * 
	 * <pre>
	 * 需要在Cookie中设置tocken
	 * </pre>
	 * 
	 * @param tocken
	 * @return
	 */
	@GET
	@Path("/info")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserInfo(@CookieParam("tocken") String tocken) {
		String userId = UserBindCache.getBoundUser(tocken);
		if (userId == null)
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("No user login!"));
		User user = null;
		try {
			user = getUserLogic().getById(userId);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult(e.getMessage()));
		}
		if (user == null)
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("Not user match for tocken!"));
		UserVO userVO = new UserVO(user.getId(), user.getName(), user.getPassword(), user.getPhoneNum(), user.getEmail(),
				user.getSignature());
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(userVO, "Get user info succeeded!"));
	}
}
