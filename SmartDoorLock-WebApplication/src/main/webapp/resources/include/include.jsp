<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="slTag"%>
<%@ taglib uri="/WEB-INF/tld/slTags.tld" prefix="slTags"%>

<c:set var="CONTEXT_PATH" value="<%=request.getContextPath() %>" />
<c:set var="PATH_RESOURCES" value="${CONTEXT_PATH }/resources" />
<c:set var="PATH_JS" value="${PATH_RESOURCES }/js" />
<c:set var="PATH_CSS" value="${PATH_RESOURCES }/css" />
<c:set var="PATH_IMAGES" value="${PATH_RESOURCES }/images" />
<c:set var="PATH_PLUGINS" value="${PATH_RESOURCES }/plugins" />
<c:set var="PATH_VENDORS" value="${PATH_RESOURCES }/vendors" />
<c:set var="PATH_VENDORS_JS" value="${PATH_RESOURCES }/vendors/js" />
<c:set var="PATH_VENDORS_CSS" value="${PATH_RESOURCES }/vendors/css" />

<c:set var="TITLE" value="SmartLock 0.1v" />
<!--  
replaceAll 
../vendors 	=>		${PATH_VENDORS}	


"js 			=>		"${PATH_VENDORS_JS}
../build/js	=>		${PATH_VENDORS_JS}


"css				=>		"${PATH_VENDORS_CSS}
../build/css	=>		${PATH_VENDORS_CSS}
 -->
<!DOCTYPE html>
<html lang="en">
  <head>
 
    <title>${TITLE } </title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	
    <!-- Bootstrap -->
    <link href="${PATH_VENDORS}/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <!-- Font Awesome -->
    <link href="${PATH_VENDORS}/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
    <!-- jVectorMap -->
    <link href="${PATH_VENDORS_CSS}/maps/jquery-jvectormap-2.0.3.css" rel="stylesheet"/>
    <!-- Custom Theme Style -->
    <link href="${PATH_VENDORS_CSS}/custom.css" rel="stylesheet"/>
	<!-- Select2 -->
	<link href="${PATH_VENDORS}/select2/dist/css/select2.min.css" rel="stylesheet" />
	<link rel="shortcut icon" type="image/ico" href="${PATH_RESOURCES}/favicon.ico"/>
    
    
    
    
    
	
   	<!-- basic plugins -->
		<!-- jQuery -->
		<script src="${PATH_VENDORS}/jquery/dist/jquery.min.js"></script>
		<!-- Bootstrap -->
		<script src="${PATH_VENDORS}/bootstrap/dist/js/bootstrap.min.js"></script>
		<!-- FastClick -->
		<script src="${PATH_VENDORS}/fastclick/lib/fastclick.js"></script>
		<!-- select2 -->
		<script src="${PATH_VENDORS }/select2/dist/js/select2.full.min.js"></script>
		
	<!-- /basic plugins -->

       


	
	
    
    
    
    
    
    