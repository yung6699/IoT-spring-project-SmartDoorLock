<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/resources/include/include.jsp"%>

<!-- forms -->
<!-- bootstrap-wysiwyg -->
<link href="${PATH_VENDORS}/google-code-prettify/bin/prettify.min.css" rel="stylesheet" />
<!-- iCheck -->
<link href="${PATH_VENDORS}/iCheck/skins/flat/green.css" rel="stylesheet" />
<!-- Switchery -->
<link href="${PATH_VENDORS}/switchery/dist/switchery.min.css" rel="stylesheet" />
<!-- starrr -->
<link href="${PATH_VENDORS}/starrr/dist/starrr.css" rel="stylesheet" />
<!-- Select2 -->
<link href="${PATH_VENDORS}/select2/dist/css/select2.min.css" rel="stylesheet" />
<!-- Animatecss -->
<link href="${PATH_VENDORS}/animate.css/animate.css" rel="stylesheet" />

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
<script src="${PATH_VENDORS }/bootstrap-wysiwyg/js/bootstrap-wysiwyg.min.js"></script>
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
<script src="${PATH_VENDORS }/devbridge-autocomplete/dist/jquery.autocomplete.min.js"></script>
<!-- starrr -->
<script src="${PATH_VENDORS }/starrr/dist/starrr.js"></script>
<!--  /forms -->

<!-- select2 -->
<script src="${PATH_VENDORS }/select2/dist/js/select2.full.min.js"></script>

<script src="${PATH_VENDORS }/bootstrap-sortable/sortable.js"></script>
<!-- /vendors(plugins library) -->

<!--  해당 페이지 기본 js	 -->
<script src="${PATH_JS }/web/category/category_setting.js"></script>
<!--  /해당 페이지 기본 js -->
<!--  test js -->
<!--  /test js -->
</head>
<body class="nav-md">
	<form id="page_data">
		<input type="hidden" name="email" value="${LOGIN_MEMBER.EMAIL }"/>
		<input type="hidden" name="cat_id" value="${CAT_ID }"/>
	</form>
	
	
	$열쇠 삭제시 trigger 에다가는 로그를 쌓고 카테고리의 항목도 쌓아야 한다. 2016/07/28$
	<div class="container body">
		<div class="main_container">
			<!-- 네비게이션 바  -->
			<slTag:nav-bar />
			<!-- page content -->
			<div class="right_col" role="main">
				<div class="">
					<div class="page-title">
						<div class="title_left">
							<h3 id="cat_list_name"></h3>
							<h4 id="cat_list_type_name"></h4>
							
						</div>
					</div>
					
					<div class="clearfix">
					</div>
					<div style="height:340px;">
						<center>
							<img id="cat_list_type_img" width="300" height="300"/>
							<h1 id="cat_list_type_text"></h1>
						</center>
					</div>
					<!-- 내용에 시작 -->
					<div class="">
						<br/>
						<br/>
						
						<br/>
						<br/>
						<br/>
						<div class="clearfix"></div>

					
						
						
						<div style="padding-top:2em;">
							<h4>자신만의 카테고리를 만들어 보세요!</h4>
							<div class="jumbotron">
								<blockquote>
									<p>- "이 카테고리는 사용자님이 관리하는 장소에 대해 다양한 인프라 서비스를 제공합니다."
										설정할 수 있습니다.</p>
									<p>- "특정한 카테고리는 사용량 뿐만 아니라 열쇠의 이동 그리고 문의 사용빈도를 사용자님이 관리하는 다양한 스마트락 제품을
										관리하고 감시할 수 있도록 다양한 인터페이스를 제공합니다."</p>
								</blockquote>
							</div>								
						</div>
						
						<div class="col-md-6 col-xs-12">
							<div class="x_panel">
								<div class="x_title">
									<h2>분류할 열쇠</h2>
									<div
										class="col-md-8 col-sm-12 col-xs-12 form-group pull-right top_search"
										style="height: 30px; margin-bottom: 5px; border-bottom-width: 5px;">
									</div>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<ul id="key_list" class="list-unstyled msg_list" style="overflow-y: scroll; height: 65em;">
									</ul>
									<div class="ln_solid"></div>
								</div>
							</div>
						</div>
						<!-- /left list -->

						<!-- right List -->
						<div class="col-md-6 col-xs-12">
							<div class="x_panel">
								<div class="x_title">
									<h2>카테고리 편집</h2>
									<div
										style="height: 30px; margin-bottom: 5px; border-bottom-width: 5px;">
										<ul style="color:#000;" class="nav navbar-right panel_toolbox">
											<li><a href="javascript:loadPageInfo({email:'${LOGIN_MEMBER.EMAIL }',cat_id:'${CAT_ID }'});">복귀 <i class="fa fa-refresh"></i></a></li>`
											<li><a href="javascript:submitCategory();">저장 <i class="fa fa-check"></i></a></li>
											<li><a href="javascript:deleteCategory();">삭제 <i class="fa fa-clqose"></i></a></li>
										</ul>
									</div>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<ul id="category_list" class="list-unstyled msg_list"
										style="overflow-y: scroll; height: 65em;">
										
									</ul>
									
									<div class="ln_solid"></div>
								</div>
							</div>
						</div>
							
						 <!-- 사용 설명서-->
						<div class="col-xs-12" id="edit" >
							<div class="x_panel">
								<div class="x_title">
									<h2>카테고리 관리</h2><small>(자신만의 열쇠꾸러미를 만드세요)</small>
									<ul class="nav navbar-right panel_toolbox">
										<li><a class="collapse-link">사용설명서<i
												class="fa fa-chevron-up"></i></a></li>
									</ul>
									<div class="clearfix"></div>
								</div>
	
								<div class="x_content" style="display: none;">
									<!-- blockquote -->
									<h4>분류할 열쇠</h4>
									<div class="jumbotron">
										<blockquote>
											<p>- "항목에 대하여 선택(클릭)하실 경우" 시에 해당 열쇠를 어떤 분류에다가 포함시킬 것인지
												설정할 수 있습니다.</p>
											<p>- "항목에 대하여 드래그(Drag)하실 경우"시에 해당 열쇠를 특정 분류에 드롭(Drop)하여
												카테고리를 설정할 수 있습니다.</p>
										</blockquote>
									</div>
	
									<h4>카테고리 편집</h4>
									<div class="jumbotron">
										<blockquote>
											<p>- "항목(열쇠) 에 대하여 선택(클릭)하실 경우"시에 해당 열쇠를 어떤 분류에다가 포함시킬
												것인지 설정할 수 있습니다. 또는, 분류된 열쇠를 다시 미 분류로 되돌려 놓을 수 있습니다.</p>
											<p>- "항목에 대하여 선택(클릭)하실 경우"시에 해당 열쇠를 어떤 분류에다가 포함시킬 것인지 설정할
												수 있습니다.</p>
											<p>- "항목에 대하여 드래그하실 경우"시에 해당 열쇠를 특정 분류에 드롭하여 카테고리를 설정할 수
												있습니다.</p>
										</blockquote>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					<!-- 
					<div class="row" style="padding-top:3em;">
						<div class="col-lg-offset-4 col-lg-2 col-md-offset-2 col-md-4 col-sm-6 col-xs-6">
							<button class="form-control btn btn-defualt">원래대로</button>
						</div>
						<div class="col-lg-2 col-md-4 col-sm-6 col-xs-6">
							<button class="form-control btn btn-primary">원래대로</button>
						</div>
					</div>
					 -->
					 
					
					
				</div>
			</div>
		</div>
		<!-- /page content -->

		<slTag:footer />
</body>
</html>