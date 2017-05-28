<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/resources/include/include.jsp"%>
<!--  해당 페이지 기본 js	 -->
<script src="${PATH_JS }/web/mykey/mykey.js"></script>
</head>
<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<!-- 네비게이션 바  -->
			<slTag:nav-bar />
			<!-- page content -->
			<div class="right_col" role="main">

		
					<div class="clearfix"></div>

					<div class="col-md-5 col-xs-12">

						<!-- 검색 기능과 키 상세검색시 쓸려고 일부러 만들어 놨다. -->
						<input type="hidden" id="login_email" value="${LOGIN_MEMBER.EMAIL}" />

						<!-- 왼쪽 패널   : 키 리스트  -->
						<div class="x_panel">
							<div class="x_title">
								<h2>내 열쇠 목록</h2>
								<div
									class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search"
									style="height: 30px; margin-bottom: 5px; border-bottom-width: 5px;">
									<div class="input-group">
										<input type="text" class="form-control"
											placeholder="Search for..." id="search_value"> <span
											class="input-group-btn">
											<button class="btn btn-default" type="button" id="search_btn">Search</button>
										</span>
									</div>
								</div>
								<div class="clearfix"></div>
							</div>

							<div class="x_content">
								<ul id="key_list" class="list-unstyled msg_list"
									style="overflow-y: scroll; height: 45em;">


								</ul>
								<div class="ln_solid"></div>
							</div>
						</div>
					</div>
					<!-- /left list -->


					<!-- right List : 키 상세정보 -> Ajax로 데이터 뿌린다. -->
					<div class="col-md-7 col-xs-12" id="key_detail">
					<div class="x_panel">
						<div class="x_title">
							<h2>열쇠 상세 정보</h2>
							<div class="clearfix"></div>
						</div>
						<div class="x_content">
							<br />
							<form id="demo-form2" data-parsley-validate
								class="form-horizontal form-label-left">
								<div class="form-group">
									<label class="control-label col-md-3 col-sm-3 col-xs-12"
										for="first-name">사용자 정의 : <span class="required">*</span>
									</label>
									<div class="col-md-8 col-sm-8 col-xs-12">
										<input type="text" class="form-control" id="key_name"
											placeholder="정의하고 싶은 이름" disabled> <span
											class="fa bookmark-o form-control-feedback right"
											aria-hidden="true"></span>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3 col-sm-3 col-xs-12"
										for="first-name">이메일 : <span class="required">*</span>
									</label>
									<div class="col-md-8 col-sm-8 col-xs-12">
										<input type="text" class="form-control" id="email" disabled>
										<span class="fa fa-share form-control-feedback right"
											aria-hidden="true"></span>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3 col-sm-3 col-xs-12"
										for="first-name">등급 : <span class="required">*</span>
									</label>
									<div class="col-md-8 col-sm-8 col-xs-12">
										<input type="text" class="form-control" id="grade_name"
											disabled> <span
											class="fa fa-bookmark form-control-feedback right"
											aria-hidden="true"></span>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3 col-sm-3 col-xs-12"
										for="first-name">도어락 코드 : <span class="required">*</span>
									</label>
									<div class="col-md-8 col-sm-8 col-xs-12">
										<input type="text" class="form-control" id="serial_no"
											disabled> <span
											class="fa fa-bookmark form-control-feedback right"
											aria-hidden="true"></span>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3 col-sm-3 col-xs-12"
										for="first-name">부여날짜 : <span class="required">*</span>
									</label>
									<div class="col-md-8 col-sm-8 col-xs-12">
										<input type="text" class="form-control" id="crt_dt" disabled>
										<span class="fa fa-calendar-o form-control-feedback right"
											aria-hidden="true"></span>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3 col-sm-3 col-xs-12"
										for="first-name">유효기간 : <span class="required">*</span>
									</label>
									<div class="col-md-8 col-sm-8 col-xs-12">
										<input type="text" class="form-control" id="date" disabled>
										<span class="fa fa-phone form-control-feedback right"
											aria-hidden="true"></span>
									</div>
								</div>
								<div class="ln_solid"></div>
								<div class="form-group">
									<div class="col-md-6 col-sm-12 col-xs-12 col-md-offset-3">
										<div class="col-xs-6">
											<input type="button" class="form-control btn btn-primary"
												 id="deleteMyKey" value="삭제" />
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
					<!-- right list : end -->
				
				<div class="clearfix"></div>
			</div>
			<!-- main role : /page content -->
		</div>
		<!-- main container end -->
	</div>
	<!-- vendors(plugins library) -->

	<!-- /vendors(plugins library) -->
	<slTag:footer />

</body>
</html>