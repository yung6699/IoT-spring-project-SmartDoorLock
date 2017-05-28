<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-md-3 left_col">
	<div class="left_col scroll-view">
		<div class="navbar nav_title" style="border: 0;">
			<a href="/${LOGIN_MEMBER.EMAIL}" class="site_title"><i class="fa fa-lg fa-lock"></i>
				<span>SmartLock</span></a>
		</div>
		<div class="clearfix"></div>
<c:choose>
	<c:when test="${LOGIN_MEMBER eq null || STATE eq 'DELETE' || STATE eq 'CANCEL' }">
		<div class="profile">
		</div>
		<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
		</div>
		<div class="sidebar-footer hidden-small">
		</div>
	</c:when>
	<c:otherwise>
		<div class="profile">
			<div class="profile_pic">
				<img src="/resources/images/user.png" alt="프로필 이미지" class="img-circle profile_img">
			</div>
			<div class="profile_info">
				<span>환영합니다</span>
				<h2>${LOGIN_MEMBER.EMAIL }</h2>
				<h2>${LOGIN_MEMBER.NAME }</h2>
				<h2>${LOGIN_MEMBER.PHONE_NUM }</h2>
				
			</div>
		</div>
		<!-- /menu profile quick info -->

		<!-- sidebar menu -->
		<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
			<div class="menu_section">
				<h3>General</h3>
				<ul class="nav side-menu">
					<li>
						<a href="/${LOGIN_MEMBER.EMAIL }/myInfo">
							<i class="fa fa-user"></i> 내정보 관리
						</a>
					</li>
					<li>
						<a href="/${LOGIN_MEMBER.EMAIL }/mykey">
							<i class="fa fa-key"></i> 내 열쇠
						</a>
					</li>
					<li>
						<a href="/${LOGIN_MEMBER.EMAIL }/doorlock" >
						<i class="fa fa-cogs"></i> 도어락 관리
						</a>
					</li>
					<li>
						<a href="/${LOGIN_MEMBER.EMAIL }/category">
							<i class="fa fa-list-alt"></i> 카테고리 관리
						</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- /sidebar menu -->

		<!-- /menu footer buttons -->
		<div class="sidebar-footer hidden-small">
			<a data-toggle="tooltip" data-placement="top" title="Settings"> <span
				class="glyphicon glyphicon-cog" aria-hidden="true"></span>
			</a> <a data-toggle="tooltip" data-placement="top" title="FullScreen">
				<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
			</a> <a data-toggle="tooltip" data-placement="top" title="Lock"> <span
				class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
			</a> <a href="${LOGIN_MEMBER.EMAIL }/logout" data-toggle="tooltip" data-placement="top" title="Logout"> <span
				class="glyphicon glyphicon-off" aria-hidden="true"></span>
			</a>
		</div>
		<!-- /menu footer buttons -->
	</c:otherwise>
</c:choose>
		<!-- menu profile quick info -->
		
	</div>
</div>

<!-- top navigation -->
<div class="top_nav">
	<div class="nav_menu">
		<nav>
<c:choose>
	<c:when test="${LOGIN_MEMBER eq null || STATE eq 'DELETE' || STATE eq 'CANCEL' }">
			<div class="nav toggle">
			</div>
			<ul class="nav navbar-nav navbar-right" style="height:57px;">
			</ul>
	</c:when>
	<c:otherwise>
			<div class="nav toggle">
				<a id="menu_toggle"><i class="fa fa-bars"></i></a>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<li class="">
					<a href="javascript:;" class="user-profile dropdown-toggle" 
					data-toggle="dropdown" aria-expanded="false"> 
						<img src="/resources/images/user.png" alt="">
						${LOGIN_MEMBER.NAME} <span class=" fa fa-angle-down"></span>
					</a>
					<!-- side-nav-bar-footer -->
					<ul class="dropdown-menu dropdown-usermenu pull-right">
						<li>
							<a href="/${LOGIN_MEMBER.EMAIL}/myInfo">
								<i class="fa fa-user pull-right"></i>내 정보 관리
							</a>
						</li>
						<li>
							<a href="/${LOGIN_MEMBER.EMAIL }/logout">
								<i class="fa fa-sign-out pull-right"></i>로그아웃
							</a>
						</li>
					</ul>
				</li>
				<!-- 새로운 소식을 보여줍니다. -->
				<li role="presentation">
					<a href="" class="info-number">
						<i class="fa fa-envelope-o"></i> 
					</a>
				</li>
			</ul>
	</c:otherwise>
</c:choose>
			
		</nav>
	</div>
</div>
<!-- /top navigation -->
