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
							<h3>회원 가입 취소</h3>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="">
						<div style="padding-top: 2em; text-align:center;">
							<div class="jumbotron">
								<h4 style="font-family: 'GODOM';">SmartLock에 들어 오시지 않으려구요...?</h4>
								<blockquote>
									다음에 회원님을 붙잡을 수 있는 다양한 컨텐츠로 유혹하고 있을 테니 기다리세요! <br/> 
									저희 SmartLock은 회원님에게 계속된 추파를 던질거에요-!★ 이얍 이얍  &gt;  &lt;<br /> 
									방금건  <b>농담</b>이구... 정말 안가면......... 안되나요ㅠ...?<br /><br/><br/> 
									<img src="${PATH_IMAGES }/pages/crying.png" width="200" />
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
								<div style='text-align:center;'>
									<form action="/system/auth/join/email/delete" method="post">
										<input type="hidden" name="member_id"
											value="${LOGIN_MEMBER.MEMBER_ID }" /> <input type="hidden"
											name="password" value="${LOGIN_MEMBER.PASSWORD }" /> <input
											type="hidden" name="email" value="${LOGIN_MEMBER.EMAIL }" />
										<input type="submit" class="btn btn-danger" value="회원 탈퇴 하기" />
									</form>
								</div>
								<br/>
								<div class="panel-footer text-center">
									회원 가입정보에 문제가 있으시다면 다시 회원가입을 해주세요. <br /> 부득이하게 가입을 다시 못하게 될경우<a
										href="mailto:dydwls121200@gmail.com" class="a-black">dydwls121200@gmail.com</a>으로
									문의해주시기 바랍니다.
								</div>
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