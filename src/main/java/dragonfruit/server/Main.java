package dragonfruit.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 程序入口，启动Spring对App上下文进行配置，利用Spring启动Jetty服务器
 * 
 * Created by xuyh at 2017年2月28日 下午4:02:47
 *
 */
public class Main {
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	private static boolean hasStarted = false;
	/**
	 * Spring 上下文
	 */
	private static ClassPathXmlApplicationContext applicationContext;

	public static ClassPathXmlApplicationContext getApplicationContext() {
		return Main.applicationContext;
	}

	public static void setApplicationContext(ClassPathXmlApplicationContext applicationContext) {
		Main.applicationContext = applicationContext;
	}

	/**
	 * 获取Bean
	 * 
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 * 获取Bean
	 * 
	 * @param requiredType
	 * @return
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 获取Bean
	 * 
	 * @param name
	 * @param args
	 * @return
	 */
	public static Object getBean(String name, Object... args) {
		return applicationContext.getBean(name, args);
	}

	/**
	 * 获取Bean
	 * 
	 * @param requiredType
	 * @param args
	 * @return
	 */
	public static <T> T getBean(Class<T> requiredType, Object... args) {
		return applicationContext.getBean(requiredType, args);
	}

	/**
	 * 获取Bean
	 * 
	 * @param name
	 * @param requiredType
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * 是否有Bean
	 * 
	 * @param name
	 * @return
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static void main(String[] args) {
		startUp();
	}

	public static void startUp() {
		if (!hasStarted) {
			try {
				String contextFilePath = "classpath*:app-context-*.xml";
				applicationContext = new ClassPathXmlApplicationContext(contextFilePath);
				applicationContext.registerShutdownHook();
				hasStarted = true;
			} catch (Exception e) {
				logger.warn(String.format("Spring initialize Exception! [%s]", e.getMessage()));
			}
		}
	}
}
