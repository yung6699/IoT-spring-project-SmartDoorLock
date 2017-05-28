<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/resources/include/include.jsp"%>

<!-- forms -->
<!-- bootstrap-wysiwyg -->
<link href="${PATH_VENDORS}/google-code-prettify/bin/prettify.min.css"
	rel="stylesheet" />
<!-- iCheck -->
<link href="${PATH_VENDORS}/iCheck/skins/flat/green.css"
	rel="stylesheet" />
<!-- Switchery -->
<link href="${PATH_VENDORS}/switchery/dist/switchery.min.css"
	rel="stylesheet" />
<!-- starrr -->
<link href="${PATH_VENDORS}/starrr/dist/starrr.css" rel="stylesheet" />
<!-- Select2 -->
<link href="${PATH_VENDORS}/select2/dist/css/select2.min.css"
	rel="stylesheet" />

<!-- /forms -->

<link href="${PATH_CSS}/web/category/category.css" rel="stylesheet" />


<!-- vendors(plugins library) -->
<!-- forms -->
<!-- NProgress -->
<script src="${PATH_VENDORS }/nprogress/nprogress.js"></script>
<!-- bootstrap-progressbar -->
<script
	src="${PATH_VENDORS }/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
<!-- iCheck -->
<script src="${PATH_VENDORS }/iCheck/icheck.min.js"></script>
<!-- bootstrap-daterangepicker -->
<script src="${PATH_VENDORS_JS }/moment/moment.min.js"></script>
<script src="${PATH_VENDORS_JS }/datepicker/daterangepicker.js"></script>
<!-- bootstrap-wysiwyg -->
<script
	src="${PATH_VENDORS }/bootstrap-wysiwyg/js/bootstrap-wysiwyg.min.js"></script>
<script src="${PATH_VENDORS }/jquery.hotkeys/jquery.hotkeys.js"></script>
<script src="${PATH_VENDORS }/google-code-prettify/src/prettify.js"></script>
<!-- switch -->
<script src="${PATH_VENDORS }/switchery/dist/switchery.min.js"></script>
<!-- iCheck -->
<script src="${PATH_VENDORS }/iCheck/icheck.min.js"></script>
<!-- Parsley -->
<script src="${PATH_VENDORS }/parsleyjs/dist/parsley.min.js"></script>
<!-- Autosize -->
<script src="${PATH_VENDORS }/autosize/dist/autosize.min.js"></script>
<!-- jQuery autocomplete -->
<script
	src="${PATH_VENDORS }/devbridge-autocomplete/dist/jquery.autocomplete.min.js"></script>
<!-- starrr -->
<script src="${PATH_VENDORS }/starrr/dist/starrr.js"></script>
<!--  /forms -->

<!-- select2 -->
<script src="${PATH_VENDORS }/select2/dist/js/select2.full.min.js"></script>

<script src="${PATH_VENDORS }/bootstrap-sortable/sortable.js"></script>
<!-- /vendors(plugins library) -->

<!--  해당 페이지 기본 js	 -->
<script src="${PATH_JS }/web/category/category_list.js"></script>
<!--  /해당 페이지 기본 js -->
<!--  test js -->
<!--  /test js -->


</head>
<body class="nav-md">
	${ERROR }
	<form id="page_data">
		<input type="hidden" name="email" value="${LOGIN_MEMBER.EMAIL }" />
	</form>
	<div class="container body">
		<div class="main_container">
			<!-- 네비게이션 바  -->
			<slTag:nav-bar />
			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="page-title">
						<div class="title_left">
							<h3>카테고리</h3>
						</div>
					</div>
					<div class="clearfix"></div>

					<!-- 내용에 시작 -->
					<div class="">
						<div class="x_panel">
							<div class="x_title">
								<h2>목록</h2>
								<ul class="nav navbar-right panel_toolbox">
									<li><a class="collapse-link"> toggle<i
											class="fa fa-chevron-up"></i></a></li>
								</ul>
								<div class="clearfix"></div>
							</div>
							<div class="x_content">
								<div id="category_list">

								</div>
								<!-- list 의 종료 -->
							</div>

						</div>
						<div class="clearfix"></div>
						<div style="padding-top: 2em;">
							<h4>자신만의 카테고리를 만들어 보세요!</h4>
							<div class="jumbotron">
								<blockquote>
									<p>- "이 카테고리는 사용자님이 관리하는 장소에 대해 다양한 인프라 서비스를 제공합니다." 설정할 수
										있습니다.</p>
									<p>- "특정한 카테고리는 사용량 뿐만 아니라 열쇠의 이동 그리고 문의 사용빈도를 사용자님이 관리하는
										다양한 스마트락 제품을 관리하고 감시할 수 있도록 다양한 인터페이스를 제공합니다."</p>
								</blockquote>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
		<!-- /page content -->

		<!-- modal page -->
		<div class="modal fade" id="categoryModalCreate" role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">카테고리 생성</h4>
					</div>
					<div class="modal-body">
						<p>카테고리 생성은 사용자님이 복수개의 열쇠를 효율적으로 관리하기 위한 분류를 나누는 기능입니다.</p>
						<hr />
						<form>
							<div class="form-group">
								<label class="control-label">카테고리 이름</label> <input type="text"
									class="form-control" id="category_name" />
							</div>
							<div class="form-group">
								<label class="control-label">카테고리 종류</label>
								<slTags:select id="category_type" g_code="CATEGORY_TYPE" />
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<div class="row">
							<button type="button"
								class="col-sm-offset-4 col-sm-4 btn btn-primary"
								id="comu_btn_delete" onclick="categoryCreate();">생성</button>
						</div>
					</div>
					<!-- Select2 -->

				</div>
			</div>
		</div>
		<!-- /modal page -->
		<slTag:footer />
	</div>
</body>
</html>