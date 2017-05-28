<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/resources/include/include.jsp"%>

<script src="${PATH_JS }/web/newspeed/newspeed.js"></script>
<style>
	.row img{
		width:10em;
		height:10em;

	}
	.row{

		height:10.5em;

	}
	.row div{
		display:inline-block;
	}
</style>
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
				<div id="login_email" style="display: none">${email}</div>
				<div class="clearfix"></div>
				<div class="row">
					<div class="col-lg-offset-3 col-md-offset-2 col-lg-6 col-md-8 col-sm-12 col-xs-12">
						<div class="x_panel">
							<div class="x_title">
								<h2>
									뉴스피드
								</h2>
								<div class="clearfix"></div>
							</div>
							<div class="x_content">
								<ul id="newspeedList" class="list-unstyled msg_list"
									style="overflow-y: scroll; height: 50em;">
<!-- 									<li class="row">
										<div>
											<img src= "../../resources/images/chess_knight_gray.svg"/>	
										</div>
										<div>
											<span>TEST</span><br>
											<span>TEST</span><br>
											<span>TEST</span><br>
											<span>TEST</span><br>
											<input type="button" value="수락 " />
											<input type="button" value="거절 " />
										</div>
									</li> -->
									
									
								</ul>
								<div class="ln_solid"></div>
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