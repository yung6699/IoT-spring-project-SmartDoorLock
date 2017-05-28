package com.sl.web.common.context;

import java.io.FileInputStream;    
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

//


public class ContextListener extends ContextLoaderListener  {


	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/*
ContextLoaderListener 와 DispatcherServlet 은 각각 webapplicationcontext 를 생성하는데
ContextLoaderListener 가 생성한 컨텍스트가 root 컨텍스트가 되고 DispatcherServlet  생성한 인스턴스는
root 컨텍스트를 부모로 하는 자식 컨텍스트가 된다.

자식 컨텍스트들은 root 컨텍스트의 설정 빈을 사용 할 수 있다.
그러기에 ContextLoaderListener 을 이용 공통빈 설정 가능.
	*/

	//import org.springframework.web.context.ContextLoaderListener; 이녀석을 사용해야한다.
	// spring 에서는 jspServlet 객체가 잘 안돌아간다.
		
		
//
//		static Properties properties = new Properties();
//		private static String CONTEXT_PATH = null;
//		private static String PATH_RESOURCES = null;
//		private static String PATH_PLUGINS = null;
//		private static String PATH_IMAGES = null;
//		private static String PATH_CSS = null;
//		private static String PATH_JS = null;
//		private static String TITLE = null;
//
//
//		static {
//			try {
//
//				String path = ContextListener.class.getClassLoader().getResource("").getPath();
//				String fullPath = URLDecoder.decode(path, "UTF-8");
//				String pathArr[] = fullPath.split("/WEB-INF/classes/");
//				fullPath = pathArr[0];
//				fullPath = fullPath + "/WEB-INF/properties/context.properties";
//				properties.load(new FileInputStream(fullPath));
//
//				CONTEXT_PATH = properties.getProperty("CONTEXT_PATH").replace("{0}", "");
//				PATH_RESOURCES = properties.getProperty("PATH_RESOURCES").replace("{0}", CONTEXT_PATH);
//				PATH_PLUGINS = properties.getProperty("PATH_PLUGIN").replace("{0}", PATH_RESOURCES);
//				PATH_IMAGES = properties.getProperty("PATH_IMAGES").replace("{0}", PATH_RESOURCES);
//				PATH_CSS = properties.getProperty("PATH_CSS").replace("{0}", PATH_RESOURCES);
//				PATH_JS = properties.getProperty("PATH_JS").replace("{0}", PATH_RESOURCES);
//				TITLE = properties.getProperty("TITLE");
//
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
		
		@Override
		public void contextInitialized(ServletContextEvent event) {
			// TODO Auto-generated method stub
			super.contextInitialized(event);
			

			
			ServletContext app = event.getServletContext();
		
			
//			app.setAttribute("CONTEXT_PATH", CONTEXT_PATH);
//			app.setAttribute("PATH_RESOURCES", PATH_RESOURCES);
//			app.setAttribute("PATH_JS", PATH_JS);
//			app.setAttribute("PATH_CSS", PATH_CSS);
//			app.setAttribute("PATH_IMAGES", PATH_IMAGES);
//			app.setAttribute("PATH_PLUGINS", PATH_PLUGINS);
//			app.setAttribute("TITLE", TITLE);
			
			
			
			logger.info("start tomcat initialize!!");
			
			
			
			
			
			
		}
		
		@Override
		public void contextDestroyed(ServletContextEvent event) {
			// TODO Auto-generated method stub
			logger.info("tomcat destroyed!");
			super.contextDestroyed(event);
			
		}
		


		


	




	
}
