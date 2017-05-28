<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/resources/include/include.jsp"%>
<link rel="stylesheet"  href="${PATH_CSS }/web/myInfo/myInfo.css"/>
<script type="text/javascript" src="${PATH_JS}/web/myInfo/myInfo.js"></script>

</head>
<body class="nav-md">

	<div class="container body">
		<div class="main_container">
			<!-- 네비게이션 바  -->
			<slTag:nav-bar />
			<!-- page content -->
			<div class="right_col" role="main">
				<div class="page-title">
					<div class="title_left">
						<h3>
							&nbsp;
						</h3>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="row">
					<div class="col-lg-offset-3 col-md-offset-2 col-lg-6 col-md-8 col-sm-12 col-xs-12">
						<div class="x_panel">
							<div class="x_title">
								<h2>
									회원정보 수정
								</h2>
								<div class="clearfix"></div>
							</div>
							<div class="x_content">
								<br />
								<div id="submitForm" data-parsley-validate
									class="form-horizontal form-label-left" >
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-3 col-xs-12"
											for="first-name">이메일 : <span class="required">*</span>
										</label>
										<div class="col-md-8 col-sm-8 col-xs-12">
											<input type="text" class="form-control" name="member_email"
												value="${LOGIN_MEMBER.EMAIL }" disabled> <span
												class="fa fa-phone form-control-feedback right"
												aria-hidden="true"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-3 col-xs-12"
											for="first-name">비밀번호 : <span class="required">*</span>
										</label>
										<div class="col-md-8 col-sm-8 col-xs-12">
											<input type="password" class="form-control" name="member_password" value=""
												placeholder="비밀번호를 입력해주세요."> <span
												class="fa fa-phone form-control-feedback right" aria-hidden="true"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-3 col-xs-12"
											for="first-name">비밀번호 확인 : <span class="required">*</span>
										</label>
										<div class="col-md-8 col-sm-8 col-xs-12">
											<input type="password" class="form-control" name="member_password_re" value=""
												placeholder="비밀번호를 다시 한번 입력해주세요.">
											<span class="fa fa-phone form-control-feedback right"
												aria-hidden="true"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-3 col-xs-12"
											for="first-name">이름 : <span class="required">*</span>
										</label>
										<div class="col-md-8 col-sm-8 col-xs-12">
											<input type="text" class="form-control" name="member_name"
												placeholder="이름" value="${LOGIN_MEMBER.NAME }"> 
											<span class="fa fa-phone form-control-feedback right"
												aria-hidden="true"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-3 col-xs-12"
											for="first-name">전화번호 : <span class="required">*</span>
										</label>
										<div class="col-md-8 col-sm-8 col-xs-12">
											<input type="text" class="form-control" name="member_phone_num"
												placeholder="Phone" value="${LOGIN_MEMBER.PHONE_NUM }">
											<span class="fa fa-phone form-control-feedback right"
												aria-hidden="true"></span>
										</div>
									</div>
									<input type="hidden" name="member_member_id" value="${LOGIN_MEMBER.MEMBER_ID }">
									<div class="ln_solid"></div>
									<div class="form-group">
										<div class="col-md-6 col-sm-12 col-xs-12 col-md-offset-3">
											<div class="row">
												<div class="col-xs-6">
													<a href="javascript:updateDelete()" id="updateDelete" class="form-control btn btn-primary">회원탈퇴</a>
												</div>
												<div class="col-xs-6">
													<input type="submit" onclick="updateSubmit()" class="form-control btn btn-success" value="회원수정" />
												</div>
											</div>
										</div>
									</div>
								</form>
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