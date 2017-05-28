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
						<div style="padding-top: 2em;">
							<div
								style="text-align: center; padding-top: 6em; margin-bottom: 3em;">
								<h1 style="color: #A31515">!!잘못된 접근 알림!!</h1>
								<h2>상태 :405</h2>
								<h3>
									정상적인 서비스 이용이 아닙니다.<br />
									다시한번 이전의 접근 페이지로 넘어가 정상적인 서비스 접근을
									부탁드립니다.<br />
								</h3>
								<img src="${PATH_IMAGES }/pages/alert.png" width="200">
								<h3>${STATUS }</h3>
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



