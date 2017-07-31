package dragonfruit.server.common;

import java.io.File;
import java.net.InetSocketAddress;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * 启动Jetty服务器类
 * 
 * Created by Xuyh at 2017年3月1日 下午11:44:06.
 */
public class JettyStartUp implements ApplicationContextAware {
	private static Logger logger = LoggerFactory.getLogger(dragonfruit.server.Main.class);
	private String host;
	private String port;
	private Server server;
	private ApplicationContext applicationContext;

	public JettyStartUp(String port, String host) {
		this.port = port;
		this.host = host;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void start() throws Exception {
		InetSocketAddress address = new InetSocketAddress(host, Integer.parseInt(port));
		server = new Server(address);
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setContextPath("/");
		File resDir = new File("./WebContent");
		webAppContext.setResourceBase(resDir.getCanonicalPath());
		webAppContext.setConfigurationDiscovered(true);
		webAppContext.setParentLoaderPriority(true);
		server.setHandler(webAppContext);
		webAppContext.setClassLoader(applicationContext.getClassLoader());
		XmlWebApplicationContext xmlWebApplicationContext = new XmlWebApplicationContext();
		xmlWebApplicationContext.setParent(applicationContext);
		xmlWebApplicationContext.setConfigLocation("");
		xmlWebApplicationContext.setServletContext(webAppContext.getServletContext());
		xmlWebApplicationContext.refresh();
		webAppContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, xmlWebApplicationContext);

		server.start();
		if (server.isStarted()) {
			logger.info(String.format("Server start succeeded at host:[%s] port:[%s].", host, port));
		} else {
			logger.info("Server start failed!");
		}
	}
}
