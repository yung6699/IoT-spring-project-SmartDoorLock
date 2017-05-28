<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/resources/include/include.jsp"%>



</head>
<body class="nav-md">
	<div id="login_email" style="display: none">${LOGIN_MEMBER.EMAIL}</div>
								
	<c:if test="${LOGIN_MEMBER.STATE eq '2'}">
		<script>
			alert("비밀번호를 방금 막 찾은 회원이시군요!\n 비밀번호를 변경해주세요.");
			location.href="/${LOGIN_MEMBER.EMAIL}/myInfo/";
		</script>
	</c:if>
	<div class="container body">
		<div class="main_container">
			<slTag:nav-bar />
			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="row">
						<div class="col-lg-7 col-md-7 col-sm-12 col-xs-12">
							<div class="row top_tiles" id="service_info_card">
								<div class="animated flipInY col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<div class="tile-stats">
										<div class="icon">
											<i class="fa fa-user-plus"></i>
										</div>
										<div class="count">32</div>
										<h3>새로 가입한 회원</h3>
										<p>도어락을 스마트하게  관리하는 사람들</p>
									</div>
								</div>
								<div class="animated flipInY col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<div class="tile-stats">
										<div class="icon">
											<i class="fa fa-unlock-alt"></i>
										</div>
										<div class="count">25</div>
										<h3>운용중인 도어락</h3>
										<p>스마트한 도어락이 관리되고 있습니다</p>
									</div>
								</div>
								<div class="animated flipInY col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<div class="tile-stats">
										<div class="icon">
											<i class="fa fa-key"></i>
										</div>
										<div class="count">49</div>
										<h3>스마트 열쇠</h3>
										<p>열쇠가 누구에게 있는지 확인해 보아요 </p>
									</div>
								</div>
								<div class="animated flipInY col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<div class="tile-stats">
										<div class="icon">
											<i class="fa fa-cloud-upload"></i>
										</div>
										<div class="count">529</div>
										<h3>서비스 이용량</h3>
										<p>활발한 도어락의 관리!</p>
									</div>
								</div>
							</div>
							<div class="x_panel">
								<div class="x_title">
									<h2>
										스마트 락 시스템 이용량
										<small>매달 통계 수치</small>
									</h2>
									
									<div class="clearfix"></div>
									<!-- 검색 기능과 키 상세검색시 쓸려고 일부러 만들어 놨다. -->
								</div>
								<div class="x_content">
									<br />
									<div class="col-xs-12">
										<div class="demo-container" style="height: 280px">
											<div id="placeholder33x" class="demo-placeholder"></div>
										</div>
									</div>
								</div>
							</div>
							<div class="x_panel">
								<div class="x_title">
									<h2>
										${LOGIN_MEMBER.NAME }(${LOGIN_MEMBER.EMAIL }) 스마트락 시스템 이용량<small>Activity shares</small>
									</h2>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">

									<div class="row"
										style="border-bottom: 1px solid #E0E0E0; padding-bottom: 5px; margin-bottom: 5px;">
										<div id="user_usage"></div>
									</div>
								</div>
							</div>
							
						</div>
						<div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
							<div class="x_panel">
								<div class="x_title">
									<h2>뉴스피드</h2>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<ul id="news_list" class="list-unstyled msg_list"
										style="overflow-y: scroll; height: 76em;">

									</ul>
									<div class="ln_solid"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="">
						<div class="row">
							<div class="x_panel">
								<div class="x_title">
									<h2>
										사용자 이용 정보
									</h2>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<div>
										<div class="row" style="text-align: center;">
											<div class="col-md-4">
												<div id="user_have_key"></div>
											</div>
											<div class="col-md-8">
												<div id="user_have_key2" style="height:350px;width: 100%;"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="">
						<div class="row">
							<div class="x_panel">
								<div class="x_title">
									<h2>
										관리하는 도어락 정보
									</h2>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<div>
										<div class="row" style="height: 500px;">
											<div class="col-md-6">
												<h2>각 도어락당 이용 날짜</h2>
												<div style="position:absolute;width: 100%;height:400px" id="doorlock_usage"></div>
											</div>
											<div class="col-md-6">
												<h2>각 도어락당 권한 소유 상태</h2>
												<div style="position:absolute;width: 100%;height:400px"  id="doorlock_have_keys"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /page content -->
		</div>
	</div>

	<!-- footer content -->
	<slTag:footer />
	<!-- /footer content -->
	<!-- venders -->
	<!-- FastClick -->
	<script src="${PATH_VENDORS}/fastclick/lib/fastclick.js"></script>
	<!-- NProgress -->
	<script src="${PATH_VENDORS}/nprogress/nprogress.js"></script>
	<!-- Chart.js -->
	<script src="${PATH_VENDORS}/Chart.js/dist/Chart.min.js"></script>
	<!-- Flot -->
	<script src="${PATH_VENDORS}/Flot/jquery.flot.js"></script>
	<script src="${PATH_VENDORS}/Flot/jquery.flot.pie.js"></script>
	<script src="${PATH_VENDORS}/Flot/jquery.flot.time.js"></script>
	<script src="${PATH_VENDORS}/Flot/jquery.flot.stack.js"></script>
	<script src="${PATH_VENDORS}/Flot/jquery.flot.resize.js"></script>
	<!-- Flot plugins -->
	<script src="${PATH_VENDORS_JS}/flot/jquery.flot.orderBars.js"></script>
	<script src="${PATH_VENDORS_JS}/flot/date.js"></script>
	<script src="${PATH_VENDORS_JS}/flot/jquery.flot.spline.js"></script>
	<script src="${PATH_VENDORS_JS}/flot/curvedLines.js"></script>
	<!-- bootstrap-daterangepicker -->
	<script src="${PATH_VENDORS_JS}/moment/moment.min.js"></script>
	<script src="${PATH_VENDORS_JS}/datepicker/daterangepicker.js"></script>
	<!-- morris.js -->
	<script src="${PATH_VENDORS}/raphael/raphael.min.js"></script>
	<script src="${PATH_VENDORS}/morris.js/morris.min.js"></script>
	
	<!--  canvas.js  -->
	<!-- http://canvasjs.com/editor/?id=http://canvasjs.com/example/gallery/bar/olympic_all_times/ -->
	<script src="${PATH_VENDORS}/canvasjs-1.9.2/canvasjs.min.js"></script>
	
	<script src="${PATH_VENDORS}/sigmajs/sigma.min.js"></script>
	<script src="${PATH_VENDORS}/sigmajs/plugins/sigma.parsers.gexf.min.js"></script>
	<script src="${PATH_VENDORS}/sigmajs/plugins/sigma.parsers.json.min.js"></script>
	<!-- /venders -->
	
	<script src="${PATH_JS }/web/main/main_newspeed.js"></script>
	<script src="${PATH_JS }/web/main/main_graph.js"></script>


</body>
</html>