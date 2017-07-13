package dragonfruit.server.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import dragonfruit.server.service.cache.UserRegisterCache;
import dragonfruit.server.Main;
import dragonfruit.server.common.ext.DragonfruitException;
import dragonfruit.server.common.i18n.I18nConstances;
import dragonfruit.server.entity.*;
import dragonfruit.server.entity.front.vos.UserVO;
import dragonfruit.server.logic.*;
import dragonfruit.server.service.cache.UserBindCache;
import dragonfruit.server.util.JsonUtils;
import xuyihao.i18n.I18nContext;
import xuyihao.i18n.I18nUtils;

/**
 * Created by Xuyh at 2017年3月22日 下午8:23:24.
 */
@Path("/user")
public class UserService {
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
	public Map<String, Object> userNameExists(@PathParam("userName") String userName) throws Exception {
		if (userName == null || userName.equals(""))
			throw new DragonfruitException(I18nConstances.REQUEST_PARAM_ERROR, "userName");
		Map<String, Object> data = new HashMap<>();
		if (!isUserNameExists(userName)) {//用户名不存在
			data.put("exist", false);
		} else {
			data.put("exist", true);
		}
		return data;
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
	 * </pre>
	 *
	 * @return
	 */
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean registerUser(String json /*, String verifyBy*/) throws Exception {
		User user = (User) JsonUtils.JsonToObj(json, User.class);
		if (isUserNameExists(user.getName()))
			throw new DragonfruitException(I18nConstances.USER_NAME_EXISTS, user.getName());
		if (getUserLogic().register(user, UserLogic.VERIFY_BY_EMAIL)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 用户注册验证(GET), 邮箱点击，返回HTML页面
	 * <p>
	 * <pre>
	 *     请求格式:/user/register/{userName}/verify?verificationCode=
	 *
	 *     返回参数：如果验证成功返回用户id和tocken
	 *
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
			@QueryParam("verificationCode") String verificationCode) throws Exception {
		if (userName == null || userName.equals(""))
			throw new DragonfruitException(I18nConstances.REQUEST_PARAM_ERROR, "userName");
		if (verificationCode == null || verificationCode.equals(""))
			throw new DragonfruitException(I18nConstances.REQUEST_PARAM_ERROR, "verificationCode");
		String userId;
		userId = getUserLogic().registerVerify(userName, verificationCode);
		if (userId != null) {//验证成功
			String tocken = UserBindCache.addBoundUser(userId);
			Map<String, String> data = new HashMap<>();
			data.put("userId", userId);
			data.put("tocken", tocken);
			return I18nUtils.getMessage(I18nContext.getLanguage(), I18nConstances.USER_VERIFY_SUCCESS_HTML_STRING, userName);
		} else {
			return I18nUtils.getMessage(I18nContext.getLanguage(), I18nConstances.USER_VERIFY_FAIL_HTML_STRING);
		}
	}

	/**
	 * 用户注册验证(POST),返回前端需要的数据
	 * <p>
	 * <pre>
	 *     请求格式:{"userName":"", "verificationCode":""}
	 *
	 *     返回参数：如果验证成功返回用户id和tocken
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
	public Map<String, String> verifyRegisterByPost(String json) throws Exception {
		if (json == null || json.equals(""))
			throw new DragonfruitException(I18nConstances.REQUEST_PARAM_EMPTY);
		@SuppressWarnings("unchecked")
		Map<String, String> verifyInfos = JsonUtils.stringToMap(json);
		String userName = verifyInfos.get("userName");
		String verificationCode = verifyInfos.get("verificationCode");
		String userId;
		userId = getUserLogic().registerVerify(userName, verificationCode);
		if (userId != null) {
			String tocken = UserBindCache.addBoundUser(userId);
			Map<String, String> data = new HashMap<>();
			data.put("userId", userId);
			data.put("tocken", tocken);
			return data;
		} else {
			throw new DragonfruitException(I18nConstances.USER_REGISTER_VERIFY_FAILED);
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
	public boolean updateUser(@CookieParam("tocken") String tocken, String json) throws Exception {
		String userId = UserBindCache.getBoundUser(tocken);
		if (userId == null)
			throw new DragonfruitException(I18nConstances.USER_NOT_LOGIN);
		User user = (User) JsonUtils.JsonToObj(json, User.class);
		user.setId(userId);
		return getUserLogic().updateUser(user);
	}

	/**
	 * 用户登录,成功之后返回用户ID,供客户端使用
	 * <p>
	 * <pre>
	 * 	请求参数：
	 *    {"name":"", "password":""}
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
	public Map<String, String> bindUser(String json) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, String> bindInfos = JsonUtils.stringToMap(json);
		String userName = bindInfos.get("name");
		String userPwd = bindInfos.get("password");
		User user = getUserLogic().getByName(userName);
		if (user == null || user.getId() == null)
			throw new DragonfruitException(I18nConstances.USER_NOT_EXISTS, userName);
		if (!user.getPassword().equals(userPwd))
			throw new DragonfruitException(I18nConstances.USER_LOGIN_FAILED, userName);
		String userId = user.getId();
		String tocken = UserBindCache.addBoundUser(userId);
		Map<String, String> data = new HashMap<>();
		data.put("userId", userId);
		data.put("tocken", tocken);
		return data;
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
	public boolean unbindUser(@CookieParam("tocken") String tocken) throws Exception {
		String userId = UserBindCache.getBoundUser(tocken);
		if (userId != null) {
			UserBindCache.removeBoundUserTocken(tocken);
			return true;
		} else {
			return false;
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
	public boolean deleteUser(@CookieParam("tocken") String tocken) throws Exception {
		String userId = UserBindCache.getBoundUser(tocken);
		if (userId != null) {
			UserBindCache.removeBoundUserTocken(tocken);
			getUserLogic().deleteById(userId);
			return true;
		} else {
			return false;
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
	public UserVO getUserInfo(@CookieParam("tocken") String tocken) throws Exception {
		String userId = UserBindCache.getBoundUser(tocken);
		if (userId == null)
			throw new DragonfruitException(I18nConstances.USER_NOT_LOGIN);
		User user = getUserLogic().getById(userId);
		if (user == null)
			throw new DragonfruitException(I18nConstances.USER_NOT_LOGIN);
		UserVO userVO = new UserVO(user.getId(), user.getName(), user.getPassword(), user.getPhoneNum(), user.getEmail(),
				user.getSignature(), user.getNickName());
		return userVO;
	}
}
