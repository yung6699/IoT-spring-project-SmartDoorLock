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
									회원 인증
								</h2>
								<div class="clearfix"></div>
							</div>
							<div class="x_content">
								<br />
								<form id="demo-form2" data-parsley-validate
									class="form-horizontal form-label-left">
									
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-3 col-xs-12"
											for="first-name">비밀번호 : <span class="required">*</span>
										</label>
										<div class="col-md-8 col-sm-8 col-xs-12">
											<input type="password" id="pass" class="form-control"
												id="inputSuccess5" placeholder="비밀번호를 입력해주세요."> <span
												class="fa fa-phone form-control-feedback right"
												aria-hidden="true"></span>
										</div>
									</div>
									
									<div class="ln_solid"></div>
									<div class="form-group">
										<div class="col-md-6 col-sm-12 col-xs-12 col-md-offset-3">
											<div class="row">
												<input onClick="checkPassword()" type="button" class="form-control btn btn-primary" value="인증" />
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
	<!-- vendors(plugins library) -->
	
	<!-- check Password -->
	<script>
	function checkPassword(){
		$.ajax({
			url: "/${LOGIN_MEMBER.EMAIL}/myInfo/check.do",
			type:"POST",
			data: {password :$("#pass").val()},
			success: function(data) {
				if(data.AJAX_RESULT="AJAX_SUCCESS"){
					location.replace("/${LOGIN_MEMBER.EMAIL}/myInfo/update");
				}else{
					alert("비밀번호를 확인해주세요.");					
				}
			}
		});
	}
	
	</script>




	<!-- /vendors(plugins library) -->

	<slTag:footer />
</body>
</html>