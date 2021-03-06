package dragonfruit.server.service;

import dragonfruit.server.Main;
import dragonfruit.server.common.ext.DragonfruitException;
import dragonfruit.server.common.i18n.I18nConstances;
import dragonfruit.server.entity.Story;
import dragonfruit.server.entity.StoryContent;
import dragonfruit.server.entity.StoryType;
import dragonfruit.server.entity.front.vos.StoryContentNodeVO;
import dragonfruit.server.entity.front.vos.StoryContentVO;
import dragonfruit.server.entity.front.vos.StoryTypeVO;
import dragonfruit.server.entity.front.vos.StoryVO;
import dragonfruit.server.logic.*;
import dragonfruit.server.cache.UserBindCache;
import dragonfruit.server.util.JsonUtils;
import xuyihao.i18n.I18nContext;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Story REST服务接口类
 *
 * Created by xuyh at 2017/5/15 0015 下午 18:41.
 */
@Path("/story")
public class StoryService {
	private StoryTypeLogic getStoryTypeLogic() {
		return (StoryTypeLogic) Main.getBean("storyTypeLogic");
	}

	private StoryLogic getStoryLogic() {
		return (StoryLogic) Main.getBean("storyLogic");
	}

	private StoryContentLogic getStoryContentLogic() {
		return (StoryContentLogic) Main.getBean("storyContentLogic");
	}

	private LikeStoryLogic getLikeStoryLogic() {
		return (LikeStoryLogic) Main.getBean("likeStoryLogic");
	}

	private LikeStoryContentLogic getLikeStoryContentLogic() {
		return (LikeStoryContentLogic) Main.getBean("likeStoryContentLogic");
	}

	/**
	 * 跨域请求发送options请求时候进行处理
	 * 
	 * @param requestMethods
	 * @param requestHeaders
	 * @return
	 * @throws Exception
	 */
	@OPTIONS
	@Path("/{path:.*}")
	public Response handleCORSRequest(@HeaderParam("Access-Control-Request-Method") final String requestMethods,
			@HeaderParam("Access-Control-Request-Headers") final String requestHeaders) throws Exception {
		Response.ResponseBuilder builder = Response.ok();
		if (requestHeaders != null)
			builder.header("Access-Control-Allow-Headers", requestHeaders);
		if (requestMethods != null)
			builder.header("Access-Control-Allow-Methods", requestMethods);
		return builder.build();
	}

	/**
	 * 新建故事
	 *
	 * <pre>
	 *     请求数据格式:{"storyTypeId":"", "name":"", ""description:""}
	 *     需要在Cookie中设置token
	 * </pre>
	 *
	 * @param token
	 * @param json
	 * @return
	 */
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> saveStory(@CookieParam("token") String token, String json) throws Exception {
		String userId = UserBindCache.getBoundUser(token);
		if (userId == null)
			throw new DragonfruitException(I18nConstances.USER_NOT_LOGIN);
		Story story = (Story) JsonUtils.JsonToObj(json, Story.class);
		String storyId = null;
		if (story != null) {
			story.setUserId(userId);
			storyId = getStoryLogic().save(story);
		}
		Map<String, Object> data = new HashMap<>();
		data.put("storyId", storyId);
		return data;
	}

	/**
	 * 删除故事
	 *
	 * <pre>
	 *     需要在Cookie中设置token
	 * </pre>
	 *
	 * @param token
	 * @param storyId
	 * @return
	 */
	@GET
	@Path("/{storyId}/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteStory(@CookieParam("token") String token, @PathParam("storyId") String storyId)
			throws Exception {
		String userId = UserBindCache.getBoundUser(token);
		if (userId == null)
			throw new DragonfruitException(I18nConstances.USER_NOT_LOGIN);
		if (storyId == null || storyId.equals(""))
			throw new DragonfruitException(I18nConstances.REQUEST_PARAM_EMPTY);
		Story story = getStoryLogic().getById(storyId);
		if (story == null)
			throw new DragonfruitException(I18nConstances.STORY_NOT_FOUND, storyId);
		if (!story.getUserId().equals(userId))
			throw new DragonfruitException(I18nConstances.STORY_NO_AUTHORITY, storyId);
		return getStoryLogic().deleteById(storyId);
	}

	/**
	 * 新建内容
	 *
	 * <pre>
	 *     请求数据格式:{"storyId":"", "headContentId":"", "content":""}
	 *     需要在Cookie中设置token
	 * </pre>
	 *
	 * @param token
	 * @param json
	 * @return
	 */
	@POST
	@Path("/content/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> saveStoryContent(@CookieParam("token") String token, String json) throws Exception {
		String userId = UserBindCache.getBoundUser(token);
		if (userId == null)
			throw new DragonfruitException(I18nConstances.USER_NOT_LOGIN);
		StoryContent storyContent = (StoryContent) JsonUtils.JsonToObj(json, StoryContent.class);
		String storyContentId = null;
		if (storyContent != null) {
			storyContent.setUserId(userId);
			storyContentId = getStoryContentLogic().save(storyContent);
		}
		Map<String, Object> data = new HashMap<>();
		data.put("storyContentId", storyContentId);
		return data;
	}

	/**
	 * 删除故事内容
	 *
	 * <pre>
	 *     需要在Cookie中设置token
	 * </pre>
	 *
	 * @param token
	 * @param storyContentId
	 * @return
	 */
	@GET
	@Path("/content/{storyContentId}/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteStoryContent(@CookieParam("token") String token,
			@PathParam("storyContentId") String storyContentId) throws Exception {
		String userId = UserBindCache.getBoundUser(token);
		if (userId == null)
			throw new DragonfruitException(I18nConstances.USER_NOT_LOGIN);
		if (storyContentId == null || storyContentId.equals(""))
			throw new DragonfruitException(I18nConstances.REQUEST_PARAM_EMPTY);
		StoryContent storyContent = getStoryContentLogic().getById(storyContentId);
		if (storyContent == null)
			throw new DragonfruitException(I18nConstances.STORY_CONTENT_NOT_FOUND, storyContentId);
		if (!storyContent.getUserId().equals(userId))
			throw new DragonfruitException(I18nConstances.STORY_CONTENT_NO_AUTHORITY, storyContentId);
		return getStoryContentLogic().deleteById(storyContentId);
	}

	/**
	 * 点赞故事
	 *
	 * <pre>
	 *     需要在Cookie中设置token
	 * </pre>
	 *
	 * @param token
	 * @param storyId
	 * @return
	 */
	@GET
	@Path("/{storyId}/like")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean likeStory(@CookieParam("token") String token, @PathParam("storyId") String storyId)
			throws Exception {
		String userId = UserBindCache.getBoundUser(token);
		if (userId == null)
			throw new DragonfruitException(I18nConstances.USER_NOT_LOGIN);
		if (storyId == null || storyId.equals(""))
			throw new DragonfruitException(I18nConstances.REQUEST_PARAM_EMPTY);
		return getLikeStoryLogic().like(userId, storyId);
	}

	/**
	 * 点赞故事内容
	 *
	 * <pre>
	 *     需要在Cookie中设置token
	 * </pre>
	 *
	 * @param token
	 * @param storyContentId
	 * @return
	 */
	@GET
	@Path("/content/{storyContentId}/like")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean likeStoryContent(@CookieParam("token") String token,
			@PathParam("storyContentId") String storyContentId) throws Exception {
		String userId = UserBindCache.getBoundUser(token);
		if (userId == null)
			throw new DragonfruitException(I18nConstances.USER_NOT_LOGIN);
		if (storyContentId == null || storyContentId.equals(""))
			throw new DragonfruitException(I18nConstances.REQUEST_PARAM_EMPTY);
		return getLikeStoryContentLogic().like(userId, storyContentId);
	}

	/**
	 * 获取所有故事类型
	 *
	 * @return
	 */
	@GET
	@Path("/type/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<StoryTypeVO> getAllStoryType() throws Exception {
		List<StoryTypeVO> storyTypeVOs = new ArrayList<>();
		for (StoryType storyType : getStoryTypeLogic().getAll()) {
			storyTypeVOs
					.add(new StoryTypeVO(storyType.getId(), storyType.getCode(), storyType.getName(I18nContext.getLanguage())));
		}
		return storyTypeVOs;
	}

	/**
	 * 获取故事总数
	 *
	 * @return
	 */
	@GET
	@Path("/count")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getStorysCount() throws Exception {
		Long count = getStoryLogic().getAllCount();
		Map<String, Object> data = new HashMap<>();
		data.put("storyCount", count);
		return data;
	}

	/**
	 * 获取用户建立的所有故事
	 * 
	 * @param token
	 * @return
	 */
	@GET
	@Path("/getStories")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<StoryVO> getUserStories(@CookieParam("token") String token) throws Exception {
		String userId = UserBindCache.getBoundUser(token);
		if (userId == null)
			throw new DragonfruitException(I18nConstances.USER_NOT_LOGIN);
		List<Story> storyList = getStoryLogic().getByUserId(userId);
		List<StoryVO> storyVOs = new ArrayList<>();
		for (Story story : storyList) {
			storyVOs.add(new StoryVO(story.getId(), story.getUserId(), story.getStoryTypeId(), story.getName(),
					story.getDescription(), story.getLike()));
		}
		return storyVOs;
	}

	/**
	 * 分页获取故事
	 *
	 * <pre>
	 *     请求数据格式:{"page":"", "size":""}
	 *     page 从 0 开始
	 * </pre>
	 *
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getPage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<StoryVO> getStoryByPage(String json) throws Exception {
		Map<String, String> queryMap = JsonUtils.stringToMap(json);
		String pageStr = queryMap.get("page");
		String sizeStr = queryMap.get("size");
		if (pageStr == null || sizeStr == null)
			throw new DragonfruitException(I18nConstances.REQUEST_PARAM_ERROR, "page, size");
		int page = Integer.parseInt(pageStr);
		int size = Integer.parseInt(sizeStr);
		List<Story> storyList = getStoryLogic().getPageByPageSize(page, size);
		List<StoryVO> storyVOS = new ArrayList<>();
		for (Story story : storyList) {
			storyVOS.add(new StoryVO(story.getId(), story.getUserId(), story.getStoryTypeId(), story.getName(),
					story.getDescription(), story.getLike()));
		}
		return storyVOS;
	}

	/**
	 * 分页获取故事(根据点赞数量降序排序)
	 *
	 * <pre>
	 *     请求数据格式:{"page":"", "size":""}
	 *     page 从 0 开始
	 * </pre>
	 *
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getLikeDownPage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<StoryVO> getStoryByLikeDown(String json) throws Exception {
		Map<String, String> queryMap = JsonUtils.stringToMap(json);
		String pageStr = queryMap.get("page");
		String sizeStr = queryMap.get("size");
		if (pageStr == null || sizeStr == null)
			throw new DragonfruitException(I18nConstances.REQUEST_PARAM_ERROR, "page, size");
		int page = Integer.parseInt(pageStr);
		int size = Integer.parseInt(sizeStr);
		List<Story> storyList = getStoryLogic().getPageByLikeDown(page, size);
		List<StoryVO> storyVOS = new ArrayList<>();
		for (Story story : storyList) {
			storyVOS.add(new StoryVO(story.getId(), story.getUserId(), story.getStoryTypeId(), story.getName(),
					story.getDescription(), story.getLike()));
		}
		return storyVOS;
	}

	/**
	 * 获取故事详情
	 *
	 * @param storyId
	 * @return
	 */
	@GET
	@Path("/{storyId}/info")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StoryVO getStoryInfo(@PathParam("storyId") String storyId) throws Exception {
		if (storyId == null || storyId.equals(""))
			throw new DragonfruitException(I18nConstances.REQUEST_PARAM_ERROR, storyId);
		Story story = getStoryLogic().getById(storyId);
		return story == null ? null
				: new StoryVO(story.getId(), story.getUserId(), story.getStoryTypeId(), story.getName(),
						story.getDescription(), story.getLike());
	}

	/**
	 * 获取故事内容详情
	 *
	 * @param storyContentId
	 * @return
	 */
	@GET
	@Path("/content/{storyContentId}/info")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StoryContentVO getStoryContentInfo(@PathParam("storyContentId") String storyContentId) throws Exception {
		if (storyContentId == null || storyContentId.equals(""))
			throw new DragonfruitException(I18nConstances.REQUEST_PARAM_ERROR, storyContentId);
		StoryContent storyContent = getStoryContentLogic().getById(storyContentId);
		return storyContent == null ? null
				: new StoryContentVO(storyContent.getId(), storyContent.getUserId(), storyContent.getStoryId(),
						storyContent.getHeadContentId(), storyContent.getContent(), storyContent.getLike());
	}

	/**
	 * 获取故事内容树状结构
	 *
	 * <pre>
	 *     返回数据格式:{"id":"", "userId":"", ""storyId:"", ""headContentId:"", "content":"", ""like:"",
	 *                      "childNodeList":[{"id":"", "userId":"", ""storyId:"", ""headContentId:"", "content":"", ""like:"",
	 *                      "childNodeList":[]}, {"id":"", "userId":"", ""storyId:"", ""headContentId:"", "content":"", ""like:"",
	 *                      "childNodeList":[]}]}
	 * </pre>
	 *
	 * @param storyId
	 * @return
	 */
	@GET
	@Path("/{storyId}/content/tree")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StoryContentNodeVO getStoryContentTree(@PathParam("storyId") String storyId) throws Exception {
		if (storyId == null || storyId.equals(""))
			throw new DragonfruitException(I18nConstances.REQUEST_PARAM_ERROR, storyId);
		//递归编织树状结构
		StoryContent parent = getStoryContentLogic().getHeadContent(storyId);
		if (parent == null)
			return null;
		StoryContentNodeVO parentNode = new StoryContentNodeVO(parent.getId(), parent.getUserId(), parent.getStoryId(),
				parent.getHeadContentId(), parent.getContent(), parent.getLike());
		buildStoryContentTree(parentNode);
		return parentNode;
	}

	private void buildStoryContentTree(StoryContentNodeVO parentNode) {
		// 递归实现树状结构编织
		List<StoryContent> storyContentList = getStoryContentLogic().getByHeadContentId(parentNode.getId());
		for (StoryContent storyContent : storyContentList) {
			StoryContentNodeVO storyContentNodeVO = new StoryContentNodeVO(storyContent.getId(), storyContent.getUserId(),
					storyContent.getStoryId(), storyContent.getHeadContentId(), storyContent.getContent(),
					storyContent.getLike());
			buildStoryContentTree(storyContentNodeVO);
			parentNode.addChild(storyContentNodeVO);
		}
	}
}
