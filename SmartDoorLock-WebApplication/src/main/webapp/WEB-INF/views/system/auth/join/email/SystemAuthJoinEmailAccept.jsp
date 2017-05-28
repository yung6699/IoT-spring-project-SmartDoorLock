<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/resources/include/include.jsp"%>

</head>
<body class="nav-md">

	<div class="container body">
		<div class="main_container">
			<!-- 네비게이션 바  -->
			<slTag:nav-bar />
			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="page-title">
						<div class="title_left">
							<h3>회원 가입 완료</h3>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="">
						<div style="padding-top:2em;">
							<h4 style="font-family: 'GODOM';">SmartLock의 회원이 되신것을 축하드립니다.</h4>
							<div class="jumbotron">
								<blockquote>
									SmartLock은 자신의 도어락을 등록하거나 다른 사람에게 권한을 부여받을 경우에 이용이 가능합니다. <br />
									도어락의 구매는 <a href="mailto:dydwls121200@gmail.com" class="a-black">dydwls121200@gmail.com</a>으로
									문의해주시기 바랍니다.<br />
								</blockquote>
							</div>								
						</div>
						
						<div class="x_panel">
							<div class="x_title">
								<h2>- 회원 정보 -</h2>
								<ul class="nav navbar-right panel_toolbox">
								</ul>
								<div class="clearfix"></div>
							</div>
							<div class="x_content text-center">
								<ul class="list-group">
									<li class="list-group-item">&nbsp;
										<p class="list-attr">이메일</p>
										<p class="list-value">${LOGIN_MEMBER.EMAIL }</p>
									</li>
									<li class="list-group-item">&nbsp;
										<p class="list-attr">이름</p>
										<p class="list-value">${LOGIN_MEMBER.NAME }</p>
									</li>
									<li class="list-group-item">&nbsp;
										<p class="list-attr">전화번호</p>
										<p class="list-value">${LOGIN_MEMBER.PHONE_NUM}</p>
									</li>
									<li class="list-group-item">&nbsp;
										<p class="list-attr">가입일</p>
										<p class="list-value">${LOGIN_MEMBER.CRT_DT}</p>
									</li>
									<li class="list-group-item">&nbsp;
										<p class="list-attr">회원 상태</p>
										<p class="list-value">${LOGIN_MEMBER.STATE_NAME}</p>
									</li>
								</ul>
							</div>
							<div class="panel-footer text-center">
								회원 가입정보에 문제가 있으시다면 다시 회원가입을 해주세요. <br /> 부득이하게 가입을 다시 못하게 될경우<a
									href="mailto:dydwls121200@gmail.com" class="a-black">dydwls121200@gmail.com</a>으로
								문의해주시기 바랍니다.
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /page content -->
		</div>
	</div>
	<slTag:footer />
</body>
</html>