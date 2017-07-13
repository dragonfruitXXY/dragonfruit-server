package dragonfruit.server.common;

import java.util.List;

import dragonfruit.server.Main;
import dragonfruit.server.logic.StoryTypeLogic;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dragonfruit.server.entity.StoryType;
import dragonfruit.server.util.XMLUtils;

/**
 * 初始化操作类
 *
 * <pre>
 *     读取XML初始配置文件内容,并写入数据库
 * </pre>
 * 
 * Created by Xuyh at 2017年4月21日 下午8:12:38.
 */
public class StartupCheck {
	private Logger logger = LoggerFactory.getLogger(StartupCheck.class);

	private static StartupCheck startupCheck;

	private StartupCheck() {
	}

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static StartupCheck getInstance() {
		if (startupCheck == null)
			startupCheck = new StartupCheck();
		return startupCheck;
	}

	/**
	 * 进行初始检查工作等
	 */
	public void initializeCheck() {
		String XMLFilePath = System.getProperty("user.dir") + "/conf/StoryType.xml";
		getStoryTypesAndSave(XMLFilePath);
	}

	/**
	 * 读取XML文件配置信息
	 * 
	 * @param storyTypeFilePathName
	 */
	@SuppressWarnings("unchecked")
	private void getStoryTypesAndSave(String storyTypeFilePathName) {
		StoryTypeLogic storyTypeLogic = (StoryTypeLogic) Main.getBean("storyTypeLogic");
		try {
			Document document = XMLUtils.readXMLFile(storyTypeFilePathName);
			Element storyTypes = document.getRootElement();
			List<Element> storyTypeList = storyTypes.elements("StoryType");
			for (Element element : storyTypeList) {
				StoryType storyType = new StoryType();
				storyType.setCode(element.attributeValue("code"));
				List<Element> nameMapElements = element.element("nameMap").elements();
				for (Element name : nameMapElements) {
					storyType.addName(name.attributeValue("language"), name.attributeValue("nameValue"));
				}
				storyTypeLogic.save(storyType);
			}
			logger.info("Read XML file data succeeded!");
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}

}
