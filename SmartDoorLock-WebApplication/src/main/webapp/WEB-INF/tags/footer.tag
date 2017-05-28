<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<footer>
	<div class="pull-right">
		<a href="/#team">SmartLock - Dashboard Web Site by 윤태영,진영균,조용진,황대건
		</a>
	</div>
	<div class="clearfix"></div>
</footer>
<c:set var="CONTEXT_PATH" value="<%=request.getContextPath() %>" />
<c:set var="PATH_RESOURCES" value="${CONTEXT_PATH }/resources" />
<c:set var="PATH_JS" value="${PATH_RESOURCES }/js" />
<c:set var="PATH_CSS" value="${PATH_RESOURCES }/css" />
<c:set var="PATH_IMAGES" value="${PATH_RESOURCES }/images" />
<c:set var="PATH_PLUGINS" value="${PATH_RESOURCES }/plugins" />
<c:set var="PATH_VENDORS" value="${PATH_RESOURCES }/vendors" />
<c:set var="PATH_VENDORS_JS" value="${PATH_RESOURCES }/vendors/js" />
<c:set var="PATH_VENDORS_CSS" value="${PATH_RESOURCES }/vendors/css" />

		<!-- Custom Theme Scripts -->
		<script src="${PATH_VENDORS_JS}/custom.min.js"></script>
	