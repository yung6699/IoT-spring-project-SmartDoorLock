package com.sl.web.common.tag.body;

import javax.servlet.jsp.tagext.TagSupport;

public class CommonTagBody extends TagSupport{

	/**
	 *  
	 *  
	 *   보류  네비게이션은 전부 기능이 다 만들어 지고 난다면 그 때 다시 작업하는 걸로.
	 *  
	 */
	
	
	private static final long serialVersionUID = -7072171833027095860L;
//
//	static Properties properties = new Properties();
//	private static String AJAX_CONNECTION_ERROR = null;
//	private static String CONTEXT_PATH = null;
//	private static String PATH_RESOURCES = null;
//	private static String PATH_PLUGIN = null;
//	private static String PATH_JQUERY = null;
//	private static String PATH_BOOTSTRAP = null;
//	private static String PATH_IMAGES = null;
//	private static String PATH_CSS = null;
//	private static String PATH_JS = null;
//	private static String TITLE = null;
//
//	private static StringBuffer sb = null;
//	private String title = "";
//
//	static {
//		try {
//
//			String path = HtmlTag.class.getClassLoader().getResource("").getPath();
//			String fullPath = URLDecoder.decode(path, "UTF-8");
//			String pathArr[] = fullPath.split("/WEB-INF/classes/");
//			fullPath = pathArr[0];
//			fullPath = fullPath + "/WEB-INF/properties/enviroment_ko_KR.properties";
//			properties.load(new FileInputStream(fullPath));
//
//			CONTEXT_PATH = properties.getProperty("CONTEXT_PATH").replace("{0}", "");
//			PATH_RESOURCES = properties.getProperty("PATH_RESOURCES").replace("{0}", CONTEXT_PATH);
//			PATH_PLUGIN = properties.getProperty("PATH_PLUGIN").replace("{0}", PATH_RESOURCES);
//			PATH_JQUERY = properties.getProperty("PATH_JQUERY").replace("{0}", PATH_PLUGIN);
//			PATH_BOOTSTRAP = properties.getProperty("PATH_BOOTSTRAP").replace("{0}", PATH_PLUGIN);
//			PATH_IMAGES = properties.getProperty("PATH_IMAGES").replace("{0}", PATH_RESOURCES);
//			PATH_CSS = properties.getProperty("PATH_CSS").replace("{0}", PATH_RESOURCES);
//			PATH_JS = properties.getProperty("PATH_JS").replace("{0}", PATH_RESOURCES);
//			TITLE = properties.getProperty("TITLE");
//			AJAX_CONNECTION_ERROR = properties.getProperty("AJAX_CONNECTION_ERROR");
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public int doStartTag() throws JspException {
//		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
//		String contextPath = request.getContextPath();
//		ServletContext app = request.getSession().getServletContext();
//		
//
//		try {
//			JspWriter out = pageContext.getOut();
//
//			sb = new StringBuffer();
//
//			appendln("");
//			appendln("");
//			appendln("");
//
//			out.print(sb.toString());
//
//		} catch (IOException ioe) {
//			throw new JspException("Error: " + ioe.getMessage());
//		}
//		return EVAL_BODY_INCLUDE;
//	}
//
//	@Override
//	public int doEndTag() throws JspException {
//		try {
//			JspWriter out = pageContext.getOut();
//
//			sb = new StringBuffer();
//			appendln("</html>");
//
//			out.print(sb.toString());
//		} catch (IOException ex) {
//			throw new JspException(ex);
//		}
//		return SKIP_PAGE;
//	}
//
//	public void setContentKey(ServletContext app) {
//
//		app.setAttribute("CONTEXT_PATH", CONTEXT_PATH);
//		app.setAttribute("PATH_RESOURCES", PATH_RESOURCES);
//		app.setAttribute("PATH_PLUGIN", PATH_PLUGIN);
//		app.setAttribute("PATH_JQUERY", PATH_JQUERY);
//		app.setAttribute("PATH_BOOTSTRAP", PATH_BOOTSTRAP);
//		app.setAttribute("PATH_IMAGES", PATH_IMAGES);
//		app.setAttribute("PATH_CSS", PATH_CSS);
//		app.setAttribute("PATH_JS", PATH_JS);
//		app.setAttribute("CONTEXT_PATH", CONTEXT_PATH);
//		app.setAttribute("TITLE", TITLE);
//		app.setAttribute("AJAX_CONNECTION_ERROR", AJAX_CONNECTION_ERROR);
//
//	}
//
//	void appendln(String str) {
//		sb.append(str).append("\n");
//	}

	
}
