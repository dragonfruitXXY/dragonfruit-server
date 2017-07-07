package dragonfruit.server.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by xuyh at 2017/7/7 17:08.
 */
public class AppProperties {
	private static Logger logger = LoggerFactory.getLogger(AppProperties.class);

	private static Properties systemProperties;

	static {
		systemProperties = getAppProperties();
	}

	public static String getProperty(String code) {
		return String.valueOf(systemProperties.get(code));
	}

	public static Properties getSystemProperties() {
		return systemProperties;
	}

	public static void setSystemProperties(Properties systemProperties) {
		AppProperties.systemProperties = systemProperties;
	}

	private static Properties getAppProperties() {
		try {
			Properties properties = new Properties();
			InputStream in = null;
			try {
				in = new FileInputStream(System.getProperty("user.dir") + "/conf/AppConfig.properties");
			} catch (Exception e) {
				try {
					File file = new File(System.getProperty("user.dir"));
					file = file.getParentFile();
					in = new FileInputStream(file.getAbsolutePath() + "/conf/AppConfig.properties");
				} catch (Exception e4) {
					try {
						in = AppProperties.class.getResourceAsStream("/conf/AppConfig.properties");
					} catch (Exception e2) {
					}
				}
			}
			properties.load(in);
			return properties;
		} catch (IOException e) {
			logger.warn(e.getMessage());
			return null;
		}
	}
}
