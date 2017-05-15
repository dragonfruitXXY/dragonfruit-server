package dragonfruit.server.service;

import dragonfruit.server.Main;
import dragonfruit.server.entity.Story;
import dragonfruit.server.entity.StoryContent;
import dragonfruit.server.entity.StoryType;
import dragonfruit.server.entity.front.ResultMessage;
import dragonfruit.server.entity.front.vos.StoryContentNodeVO;
import dragonfruit.server.entity.front.vos.StoryVO;
import dragonfruit.server.logic.*;
import dragonfruit.server.service.cache.UserBindCache;
import dragonfruit.server.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
	private Logger logger = LoggerFactory.getLogger(StoryService.class);

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
	 * 新建故事
	 *
	 * <pre>
	 *     请求数据格式:{"storyTypeId":"", "name":"", ""description:""}
	 *     返回数据格式:{"result":"true/false", "message":"", "data":{"storyId":""}}
	 *     需要在Cookie中设置tocken
	 * </pre>
	 *
	 * @param tocken
	 * @param json
	 * @return
	 */
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String saveStory(@CookieParam("tocken") String tocken, String json) {
		String userId = UserBindCache.getBoundUser(tocken);
		if (userId == null)
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("No user login!"));
		Story story = null;
		try {
			story = (Story) JsonUtils.JsonToObj(json, Story.class);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult(e.getMessage()));
		}
		String storyId = null;
		if (story != null) {
			story.setUserId(userId);
			storyId = getStoryLogic().save(story);
		}
		Map<String, String> data = new HashMap<>();
		data.put("storyId", storyId);
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(data, "Save story succeeded!"));
	}

	/**
	 * 删除故事
	 *
	 * <pre>
	 *     需要在Cookie中设置tocken
	 * </pre>
	 *
	 * @param tocken
	 * @param storyId
	 * @return
	 */
	@GET
	@Path("/{storyId}/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteStory(@CookieParam("tocken") String tocken, @PathParam("storyId") String storyId) {
		String userId = UserBindCache.getBoundUser(tocken);
		if (userId == null)
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("No user login!"));
		if (storyId == null || storyId.equals(""))
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("StoryId can not be null!"));
		Story story = getStoryLogic().getById(storyId);
		if (story == null)
			return JsonUtils
					.objectToJsonStr(ResultMessage.wrapFailureResult(String.format("No story for id [%s]!", storyId)));
		if (!story.getUserId().equals(userId))
			return JsonUtils.objectToJsonStr(
					ResultMessage.wrapFailureResult(String.format("Delete story for id [%s] failed! No authority!", storyId)));
		if (!getStoryLogic().deleteById(storyId))
			return JsonUtils
					.objectToJsonStr(ResultMessage.wrapFailureResult(String.format("Delete story for id [%s] failed!", storyId)));
		return JsonUtils.objectToJsonStr(
				ResultMessage.wrapSuccessResult(String.format("Delete story for id [%s] Succeeded!", storyId)));
	}

	/**
	 * 新建内容
	 *
	 * <pre>
	 *     请求数据格式:{"storyId":"", "headContentId":"", ""content:""}
	 *     返回数据格式:{"result":"true/false", "message":"", "data":{"storyContentId":""}}
	 *     需要在Cookie中设置tocken
	 * </pre>
	 *
	 * @param tocken
	 * @param json
	 * @return
	 */
	@POST
	@Path("/content/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String saveStoryContent(@CookieParam("tocken") String tocken, String json) {
		String userId = UserBindCache.getBoundUser(tocken);
		if (userId == null)
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("No user login!"));
		StoryContent storyContent = null;
		try {
			storyContent = (StoryContent) JsonUtils.JsonToObj(json, StoryContent.class);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult(e.getMessage()));
		}
		String storyContentId = null;
		if (storyContent != null) {
			storyContent.setUserId(userId);
			getStoryContentLogic().save(storyContent);
		}
		Map<String, String> data = new HashMap<>();
		data.put("storyContentId", storyContentId);
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(data, "Save story content succeeded!"));
	}

	/**
	 * 删除故事内容
	 *
	 * <pre>
	 *     需要在Cookie中设置tocken
	 * </pre>
	 *
	 * @param tocken
	 * @param storyContentId
	 * @return
	 */
	@GET
	@Path("/content/{storyContentId}/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteStoryContent(@CookieParam("tocken") String tocken,
			@PathParam("storyContentId") String storyContentId) {
		String userId = UserBindCache.getBoundUser(tocken);
		if (userId == null)
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("No user login!"));
		if (storyContentId == null || storyContentId.equals(""))
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("StoryContentId can not be null!"));
		StoryContent storyContent = getStoryContentLogic().getById(storyContentId);
		if (storyContent == null)
			return JsonUtils.objectToJsonStr(
					ResultMessage.wrapFailureResult(String.format("No story content for id [%s]!", storyContentId)));
		if (!storyContent.getUserId().equals(userId))
			return JsonUtils.objectToJsonStr(ResultMessage
					.wrapFailureResult(String.format("Delete story content for id [%s] failed! No authority!", storyContentId)));
		if (!getStoryContentLogic().deleteById(storyContentId))
			return JsonUtils.objectToJsonStr(
					ResultMessage.wrapFailureResult(String.format("Delete story content for id [%s] failed!", storyContentId)));
		return JsonUtils.objectToJsonStr(
				ResultMessage.wrapSuccessResult(String.format("Delete story content for id [%s] Succeeded!", storyContentId)));
	}

	/**
	 * 点赞故事
	 *
	 * <pre>
	 *     需要在Cookie中设置tocken
	 * </pre>
	 *
	 * @param tocken
	 * @param storyId
	 * @return
	 */
	@GET
	@Path("/{storyId}/like")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String likeStory(@CookieParam("tocken") String tocken, @PathParam("storyId") String storyId) {
		String userId = UserBindCache.getBoundUser(tocken);
		if (userId == null)
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("No user login!"));
		if (storyId == null || storyId.equals(""))
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("StoryId can not be null!"));
		if (!getLikeStoryLogic().like(userId, storyId))
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("Like story failed!"));
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult("Like story succeeded!"));
	}

	/**
	 * 点赞故事内容
	 *
	 * <pre>
	 *     需要在Cookie中设置tocken
	 * </pre>
	 *
	 * @param tocken
	 * @param storyContentId
	 * @return
	 */
	@GET
	@Path("/content/{storyContentId}/like")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String likeStoryContent(@CookieParam("tocken") String tocken,
			@PathParam("storyContentId") String storyContentId) {
		String userId = UserBindCache.getBoundUser(tocken);
		if (userId == null)
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("No user login!"));
		if (storyContentId == null || storyContentId.equals(""))
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("StoryContentId can not be null!"));
		if (!getLikeStoryContentLogic().like(userId, storyContentId))
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("Like story content failed!"));
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult("Like story content succeeded!"));
	}

	/**
	 * 获取所有故事类型
	 *
	 * <pre>
	 *     返回数据格式:{"result":"true/false", "message":"",
	 *                      "data":[{"id":"", ""code:"", ""name:""},
	 *                      {"id":"", ""code:"", ""name:""}]}
	 * </pre>
	 *
	 * @return
	 */
	@GET
	@Path("/type/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllStoryType() {
		List<StoryType> storyTypeList = getStoryTypeLogic().getAll();
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(storyTypeList, "Query succeeded!"));
	}

	/**
	 * 获取故事总数
	 *
	 * <pre>
	 *     返回数据格式:{"result":"true/false", "message":"",
	 *                      "data":{"storyCount":""}}
	 * </pre>
	 *
	 * @return
	 */
	@GET
	@Path("/count")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getStorysCount() {
		Long count = getStoryLogic().getAllCount();
		Map<String, Object> data = new HashMap<>();
		data.put("storyCount", count);
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(data, "Query succeeded!"));
	}

	/**
	 * 分页获取故事
	 *
	 * <pre>
	 *     请求数据格式:{"page":"", "size":""}
	 *     返回数据格式:{"result":"true/false", "message":"", "data":[{}, {}]}
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
	public String getStoryByPage(String json) {
		Map<String, String> queryMap = JsonUtils.stringToMap(json);
		String pageStr = queryMap.get("page");
		String sizeStr = queryMap.get("size");
		if (pageStr == null || sizeStr == null)
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("Query param parsing error!"));
		int page;
		int size;
		try {
			page = Integer.parseInt(pageStr);
			size = Integer.parseInt(sizeStr);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult(e.getMessage()));
		}
		List<Story> storyList = getStoryLogic().getPageByPageSize(page, size);
		List<StoryVO> storyVOS = new ArrayList<>();
		for (Story story : storyList) {
			storyVOS.add(new StoryVO(story.getId(), story.getUserId(), story.getStoryTypeId(), story.getName(),
					story.getDescription(), story.getLike()));
		}
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(storyVOS, "Query succeeded!"));
	}

	/**
	 * 分页获取故事(根据点赞数量降序排序)
	 *
	 * <pre>
	 *     请求数据格式:{"page":"", "size":""}
	 *     返回数据格式:{"result":"true/false", "message":"", "data":[{}, {}]}
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
	public String getStoryByLikeDown(String json) {
		Map<String, String> queryMap = JsonUtils.stringToMap(json);
		String pageStr = queryMap.get("page");
		String sizeStr = queryMap.get("size");
		if (pageStr == null || sizeStr == null)
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("Query param parsing error!"));
		int page;
		int size;
		try {
			page = Integer.parseInt(pageStr);
			size = Integer.parseInt(sizeStr);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult(e.getMessage()));
		}
		List<Story> storyList = getStoryLogic().getPageByLikeDown(page, size);
		List<StoryVO> storyVOS = new ArrayList<>();
		for (Story story : storyList) {
			storyVOS.add(new StoryVO(story.getId(), story.getUserId(), story.getStoryTypeId(), story.getName(),
					story.getDescription(), story.getLike()));
		}
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(storyVOS, "Query succeeded!"));
	}

	/**
	 * 获取故事详情
	 *
	 * <pre>
	 *     返回数据格式:{"result":"true/false", "message":"", "data":[{}, {}]}
	 * </pre>
	 *
	 * @param storyId
	 * @return
	 */
	@GET
	@Path("/{storyId}/info")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getStoryInfo(@PathParam("storyId") String storyId) {
		if (storyId == null || storyId.equals(""))
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("Query param parsing error!"));
		Story story = getStoryLogic().getById(storyId);
		if (story == null)
			return JsonUtils
					.objectToJsonStr(ResultMessage.wrapFailureResult(String.format("No story found for id [%s]!", storyId)));
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(story, "Query succeeded!"));
	}

	/**
	 * 获取故事内容详情
	 *
	 * <pre>
	 *     返回数据格式:{"result":"true/false", "message":"", "data":[{}, {}]}
	 * </pre>
	 *
	 * @param storyContentId
	 * @return
	 */
	@GET
	@Path("/content/{storyContentId}/info")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getStoryContentInfo(@PathParam("storyContentId") String storyContentId) {
		if (storyContentId == null || storyContentId.equals(""))
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("Path param parsing error!"));
		StoryContent storyContent = getStoryContentLogic().getById(storyContentId);
		if (storyContent == null)
			return JsonUtils.objectToJsonStr(
					ResultMessage.wrapFailureResult(String.format("No storyContent found for id [%s]!", storyContentId)));
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(storyContent, "Query succeeded!"));
	}

	/**
	 * 获取故事内容树状结构
	 *
	 * <pre>
	 *     返回数据格式:{"result":"true/false", "message":"", "data":{"id":"", "userId":"", ""storyId:"", ""headContentId:"", "content":"", ""like:"",
	 *                      "childNodeList":[{"id":"", "userId":"", ""storyId:"", ""headContentId:"", "content":"", ""like:"",
	 *                      "childNodeList":[]}, {"id":"", "userId":"", ""storyId:"", ""headContentId:"", "content":"", ""like:"",
	 *                      "childNodeList":[]}]}}
	 * </pre>
	 *
	 * @param storyId
	 * @return
	 */
	@GET
	@Path("/content/{storyId}/tree")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getStoryContentTree(@PathParam("storyId") String storyId) {
		if (storyId == null || storyId.equals(""))
			return JsonUtils.objectToJsonStr(ResultMessage.wrapFailureResult("Path param parsing error!"));
		//递归编织树状结构
		StoryContent parent = getStoryContentLogic().getHeadContent(storyId);
		if (parent == null)
			return JsonUtils.objectToJsonStr(
					ResultMessage.wrapFailureResult(String.format("No head storyContent found for story id [%s]!", storyId)));
		StoryContentNodeVO parentNode = new StoryContentNodeVO(parent.getId(), parent.getUserId(), parent.getStoryId(),
				parent.getHeadContentId(), parent.getContent(), parent.getLike());
		buildStoryContentTree(parentNode);
		return JsonUtils.objectToJsonStr(ResultMessage.wrapSuccessResult(parentNode, "Query succeeded!"));
	}

	private void buildStoryContentTree(StoryContentNodeVO parentNode) {
		//XXX 递归实现树状结构编织
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
