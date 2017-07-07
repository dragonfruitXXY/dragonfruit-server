package dragonfruit.server.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import dragonfruit.server.service.cache.UserRegisterCache;
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
 * Created by Xuyh at 2017年3月22日 下午8:23:24.
 */
@Path("/user")
public class UserService {
	private Logger logger = LoggerFactory.getLogger(UserService.class);

	private UserLogic getUserLogic() {
		return (UserLogic) Main.getBean("userLogic");
	}

	/**
	 * 查询用户名是否已经存在
	 *
	 * @param userName
	 * @return
	 */
	@GET
	@Path("/{userName}/exists")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String userNameExists(@PathParam("userName") String userName) {
		if (userName == null || userName.equals(""))
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("Path param userName can not be null!"));
		Map<String, Object> data = new HashMap<>();
		if (!isUserNameExists(userName)) {//用户名不存在
			data.put("exist", false);
			return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(data, "Query succeeded!"));
		} else {
			data.put("exist", true);
			return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(data, "Query succeeded!"));
		}
	}

	/**
	 * 判断用户名是否存在
	 *
	 * @param userName
	 * @return
	 */
	private boolean isUserNameExists(String userName) {
		if (!UserRegisterCache.isCacheUserNameExists(userName) && getUserLogic().getByName(userName) == null)//不存在
			return false;
		return true;
	}

	/**
	 * 用户注册（未验证）
	 * <p>
	 * <pre>
	 *     验证方式可选（待完成）需要添加参数选择验证方式
	 *
	 * 	返回成功即用户注册信息保存到缓存中成功
	 *
	 * 	返回参数：{"result":true/false, "message":"", "data":}
	 *
	 * </pre>
	 *
	 * @return
	 */
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String registerUser(String json /*, String verifyBy*/) {
		User user;
		try {
			user = (User) JsonUtils.JsonToObj(json, User.class);
			if (isUserNameExists(user.getName()))
				return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("User name already exists!"));
			if (getUserLogic().register(user, UserLogic.VERIFY_BY_EMAIL)) {
				return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult("Register cache save succeeded!"));
			} else {
				return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("Register cache save failed!"));
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult(e.getMessage()));
		}
	}

	/**
	 * 用户注册验证(GET), 邮箱点击，返回HTML页面
	 * <p>
	 * <pre>
	 *     请求格式:/user/register/{userName}/verify?verificationCode=
	 *
	 *     返回参数：如果验证成功返回用户id和tocken
	 *        {"result":true/false, "message":"", "data":{"userId":"", "tocken":""}}
	 *
	 * 		之后的请求需要在cookie中设置tocken
	 * </pre>
	 *
	 * @param userName
	 * @param verificationCode
	 * @return
	 */
	@GET
	@Path("/register/{userName}/verify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML + "; charset=utf-8")
	public String verifyRegister(@PathParam("userName") String userName,
			@QueryParam("verificationCode") String verificationCode) {
		if (userName == null || userName.equals(""))
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("Path param userName can not be null!"));
		if (verificationCode == null || verificationCode.equals(""))
			return JsonUtils
					.objectToJsonStr(ResultMessage.wrapFailureResult("Query param verificationCode can not be null!"));
		String userId;
		try {
			userId = getUserLogic().registerVerify(userName, verificationCode);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult(e.getMessage()));
		}
		if (userId != null) {//验证成功
			String tocken = UserBindCache.addBoundUser(userId);
			Map<String, String> data = new HashMap<>();
			data.put("userId", userId);
			data.put("tocken", tocken);
			return String.format(
					"<html><head><title>用户注册验证成功</title></head><body><pre>你好, %s !你已验证注册成功。请重新登录。</pre></body></html>", userName);
		} else {
			return "<html><head><title>用户注册验证成功</title></head><body><pre>验证注册失败。请重新注册。</pre></body></html>";
		}
	}

	/**
	 * 用户注册验证(POST),返回前端需要的数据
	 * <p>
	 * <pre>
	 *     请求格式:{"userName":"", "verificationCode":""}
	 *
	 *     返回参数：如果验证成功返回用户id和tocken
	 *        {"result":true/false, "message":"", "data":{"userId":"", "tocken":""}}
	 *
	 * 		之后的请求需要在cookie中设置tocken
	 * </pre>
	 *
	 * @param userName
	 * @param verificationCode
	 * @return
	 */
	@POST
	@Path("/register/verify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String verifyRegisterByPost(String json) {
		if (json == null || json.equals(""))
			return JsonUtils
					.objectToJsonStr(ResultMessage.wrapFailureResult("Param can not be null!"));
		@SuppressWarnings("unchecked")
		Map<String, String> verifyInfos = JsonUtils.stringToMap(json);
		String userName = verifyInfos.get("userName");
		String verificationCode = verifyInfos.get("verificationCode");
		String userId;
		try {
			userId = getUserLogic().registerVerify(userName, verificationCode);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult(e.getMessage()));
		}
		if (userId != null) {
			String tocken = UserBindCache.addBoundUser(userId);
			Map<String, String> data = new HashMap<>();
			data.put("userId", userId);
			data.put("tocken", tocken);
			return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(data, "Verify user succeeded!"));
		} else {
			return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult("Verify user failed!"));
		}
	}

	/**
	 * 修改用户信息
	 * <p>
	 * <pre>
	 * 	请求格式:{"name":"", "password":"", "phoneNum":"", "email":"", "signature":"", "nickName":""}
	 * </pre>
	 *
	 * @param tocken
	 * @param json
	 * @return
	 */
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateUser(@CookieParam("tocken") String tocken, String json) {
		String userId = UserBindCache.getBoundUser(tocken);
		if (userId == null)
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("No user login!"));
		User user = null;
		try {
			user = (User) JsonUtils.JsonToObj(json, User.class);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult(e.getMessage()));
		}
		user.setId(userId);
		if (!getUserLogic().updateUser(user))
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("Update user failed!"));
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult("Update user succeeded!"));
	}

	/**
	 * 用户登录,成功之后返回用户详情,供客户端使用
	 * <p>
	 * <pre>
	 * 	请求参数：
	 *    {"name":"", "password":""}
	 *
	 * 	返回参数：
	 *    {"result":true/false, "message":"", "data":{"userId":"", "tocken":""}}
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
	 * <p>
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
	 * <p>
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
				user.getSignature(), user.getNickName());
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(userVO, "Get user info succeeded!"));
	}
}
