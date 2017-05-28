<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="CONTEXT_PATH" value="<%=request.getContextPath() %>" />
<c:set var="PATH_RESOURCES" value="${CONTEXT_PATH }/resources" />
<c:set var="PATH_JS" value="${PATH_RESOURCES }/js" />
<c:set var="PATH_CSS" value="${PATH_RESOURCES }/css" />
<c:set var="PATH_IMAGES" value="${PATH_RESOURCES }/images" />
<c:set var="PATH_PLUGINS" value="${PATH_RESOURCES }/plugins" />
<c:set var="TITLE" value="SmartLock 0.1v" />

<html>
<head>
<title>${TITLE}</title>


<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>스마트락 소개 페이지</title>

<!-- Bootstrap Core CSS -->
<link href="${PATH_RESOURCES}/portpolio/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Animate CSS -->
<link href="${PATH_RESOURCES}/portpolio/css/animate.css"
	rel="stylesheet">


<!-- Custom CSS -->
<link href="${PATH_RESOURCES}/portpolio/css/agency.css" rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="${PATH_RESOURCES}/portpolio/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700"
	rel="stylesheet" type="text/css">
<link href='https://fonts.googleapis.com/css?family=Kaushan+Script'
	rel='stylesheet' type='text/css'>
<link
	href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic'
	rel='stylesheet' type='text/css'>
<link
	href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700'
	rel='stylesheet' type='text/css'>
<link href="${PATH_CSS }/home/home.css" rel='stylesheet' type='text/css'>



</head>

<body id="page-top" class="index">

	<!-- Navigation -->
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header page-scroll">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand page-scroll" href="#page-top">Smart Lock</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li class="hidden"><a href="#page-top"></a></li>
					<li><a class="page-scroll" href="#services">서비스</a></li>
					<li><a class="page-scroll" href="#about">스마트락이란?</a></li>
					<li><a class="page-scroll" href="#team">개발구성원</a></li>
					<li><a class="page-scroll" href="#contact">문의메일</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>

	<!-- Header -->


	<header>
		<div class="container">
			<div class="intro-text">
				<div class="intro-lead-in">Welcome To SmartLock!</div>
				<div class="intro-heading">만나서 <br/>반갑습니다!</div>
				<button onclick="accessModalLoginPage();" class="page-scroll btn btn-xl">
					서비스 이용하러 가기</a>
			</div>
		</div>
	</header>
	

	<!-- Services Section -->
	<section id="services">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 text-center">
					<h2 class="section-heading">서비스</h2>
					<h3 class="section-subheading text-muted">세상으로 통하는 문, 세상으로 나가는
						문, SmartLock</h3>
				</div>
			</div>
			<div class="row text-center">
				<div class="col-md-4">
					<span class="fa-stack fa-4x"> <i
						class="fa fa-circle fa-stack-2x text-primary"></i> <i
						class="fa fa-cogs fa-stack-1x fa-inverse"></i>
					</span>
					<h4 class="service-heading">권한 네트워크</h4>
					<p class="text-muted">
						개인이 생성하는 <b>권한 네트워크</b>로 개인이용자 부터 기업 솔루션까지 포용할 수 있는 기능을 제공합니다.
					</p>
					<br/>
					<br/>
					
				</div>
				<div class="col-md-4">
					<span class="fa-stack fa-4x"> <i
						class="fa fa-circle fa-stack-2x text-primary"></i> <i
						class="fa fa-exchange fa-stack-1x fa-inverse"></i>
					</span>
					<h4 class="service-heading">크로스 플랫폼 제공</h4>
					<p class="text-muted">앱과 웹에서 그리고 안드로이드, iOS 어느곳에서나 똑같은 기능을 이용할
						수 있도록, 내가 원하는대로</p>
					<br/>
					<br/>
					<br/>
					
				</div>
				<div class="col-md-4">
					<span class="fa-stack fa-4x"> <i
						class="fa fa-circle fa-stack-2x text-primary"></i> <i
						class="fa fa-line-chart fa-stack-1x fa-inverse"></i>
					</span>
					<h4 class="service-heading">대쉬 보드</h4>
					<p class="text-muted">모든 서비스 이용에 대한 내용을 다양한 그래프와 함께 제공합니다.</p>
					<br/>
					<br/>
					<br/>
					
				</div>
			</div>
		</div>
	</section>

	<!-- About Section -->
	<section id="about">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 text-center">
					<h2 class="section-heading">스마트락이란?</h2>
					<h3 class="section-subheading text-muted">인터넷과 블루투스를 이용한 똑똑한
						도어락</h3>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<ul class="timeline">
							<li>
								<div class="timeline-image">
									<img class="img-circle img-responsive"
										src="${PATH_RESOURCES}/portpolio/img/about/1.jpg" alt="">
								</div>
								<div class="timeline-panel">
									<div class="timeline-heading">
										<h3>열쇠형 도어락</h3>
										<h4 class="subheading">열쇠를 분실했을 경우..</h4>
									</div>
									<div class="timeline-body">
										<p class="text-muted">사용자가 열쇠를 분실했을 경우, 또는 열쇠를 이용해야 하는
											사람들에게 열쇠를 건내주어야 하는경우 그리고.. 열쇠 수리공아저씨를 불러서 겨우 문을 여는 경우!
											아날로그감성도 좋지만 잃어버리면 곤란하죠. 매우! 매우!</p>
									</div>
								</div>
							</li>
							<li class="timeline-inverted">
								<div class="timeline-image">
									<img class="img-circle img-responsive"
										src="${PATH_RESOURCES}/portpolio/img/about/2.jpg" alt="">
								</div>
								<div class="timeline-panel">
									<div class="timeline-heading">
										<h3>비밀번호 도어락</h3>
										<h4 class="subheading">내 집 비밀번호를 왜 니가 알아?</h4>
									</div>
									<div class="timeline-body">
										<p class="text-muted">비밀번호를 다른 사람이 알아버렸는대, 난 왜 모르지? 혹시 나
											말고 아는 사람이 문을 열면 어떻게하지? 여기 누가 왔다 갔지?</p>
									</div>
								</div>
							</li>
							<li>
								<div class="timeline-image">
									<img class="img-circle img-responsive"
										src="${PATH_RESOURCES}/portpolio/img/about/3.jpg" alt="">
								</div>
								<div class="timeline-panel">
									<div class="timeline-heading">
										<h4>RFID 도어락</h4>
										<h4 class="subheading">열쇠형보다 더 강력한보안..?</h4>
									</div>
									<div class="timeline-body">
										<p class="text-muted">기존 열쇠형보다 더 강력한 보안을 지녔지만, 잃어버리면 답이
											없... 답이 없네!? 답이 없어!</p>
									</div>
								</div>
							</li>
							<li class="timeline-inverted">
								<div class="timeline-image">
									<img class="img-circle img-responsive img4" 
										src="${PATH_RESOURCES}/portpolio/img/about/4.jpg" alt=""/>
								</div>
								<div class="timeline-panel">
									<div class="timeline-heading">
										<h4>블루투스 도어락</h4>
										<h4 class="subheading">현재 서비스의 하드웨어</h4>
									</div>
									<div class="timeline-body">
										<p class="text-muted">블루투스 데이터 통신을 도어락 제어 방식이지만, 기록 및
											열쇠부여에 대한 정적인 관리만 제공한다.</p>
									</div>
								</div>
							</li>
							<li class="timeline-inverted">
								<div class="timeline-image">
									<h4>
										SmartLock <br>will <br>do that
									</h4>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
	</section>

	<!-- Team Section -->
	<section id="team" class="bg-light-gray">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 text-center">
					<h2 class="section-heading">개쩌는 양반들</h2>
					<h3 class="section-subheading text-muted">그냥 그거 이렇게 요렇게 대충 하면
						되는거 아님 ? 에헿</h3>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3">
					<div class="team-member">
						<img width="200" height="200"
							src="${PATH_RESOURCES}/portpolio/img/team/hello1.jpg"
							class="img-responsive img-circle" alt="">
						<h4>윤 태영</h4>
						<p class="text-muted">너내 둘 그만좀 싸워라.머리아프다</p>
						<br />
						<p class="text-muted">yty3592@gmail.com</p>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="team-member">
						<img width="200"
							src="${PATH_RESOURCES}/portpolio/img/team/hello2.jpg"
							class="img-responsive img-circle" alt="">
						<h4>조 용진</h4>
						<p class="text-muted">쉬워보이면 너가 하던가!ㅡ ㅡ</p>
						<br />
						<p class="text-muted">dydwls121200@gmail.com</p>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="team-member">
						<img width="200"
							src="${PATH_RESOURCES}/portpolio/img/team/hello3.jpg"
							class="img-responsive img-circle" alt="">
						<h4>진 영균</h4>
						<p class="text-muted">하 돌대가리냐?</p>
						<br />
						<p class="text-muted">ywnwalone@naver.com</p>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="team-member">
						<img width="200"
							src="${PATH_RESOURCES}/portpolio/img/team/hello4.jpg"
							class="img-responsive img-circle" alt="">
						<h4>황 대건</h4>
						<p class="text-muted">너내는 목숨이 하나가 아니냐?</p>
						<br />
						<p class="text-muted">hdk9865@gmail.com</p>

					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2 text-center">
					<p class="large text-muted">
						팀장 겸, 앱 개발 리더 : 윤태영 <br />웹 개발 리더 : 조용진 <br /> 아이디어 및 구상의 리더 : 진영균<br />하드웨어
						개발 리더 : 황대건 <br /> 하루도 쉴 새 없이 욕이 난무하는 그들의 팀에서는 훌륭한 개발 퍼포먼스를 기대할 수
						있을것인가 !!
					</p>
				</div>
			</div>
		</div>
	</section>
	<!-- Contact Section -->
	<section id="contact">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 text-center">
					<h2 class="section-heading">Contact Us</h2>
					<h3 class="section-subheading text-muted">궁금한점이나 등등, 문의메일 주십셔~
						샤샤샤샤</h3>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div id="contactForm" novalidate>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<input type="text" class="form-control"
										placeholder="Your Name *" id="name" required
										data-validation-required-message="Please enter your name.">
									<p class="help-block text-danger"></p>
								</div>
								<div class="form-group">
									<input type="email" class="form-control"
										placeholder="Your Email *" id="email" required
										data-validation-required-message="Please enter your email address.">
									<p class="help-block text-danger"></p>
								</div>
								<div class="form-group">
									<input type="text" class="form-control"
										placeholder="Your Phone *" id="tel" required
										data-validation-required-message="Please enter your phone number.">
									<p class="help-block text-danger"></p>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<textarea class="form-control"
										placeholder="Your Message * ex: 용진님 넘나 잘생긴 것. 대단한  넘 샤샤샤."
										id="message" required
										data-validation-required-message="Please enter a message."></textarea>
									<p class="help-block text-danger"></p>
								</div>
							</div>
							<div class="clearfix"></div>
							<div class="col-lg-12 text-center">
								<div id="success"></div>
								<button type="button" onclick="sendMessage()" class="btn btn-xl">Send
									Message</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
<!--  accessModal 하나로 회원가입 및 로그인을 처리할 수 있도록 한다. -->
	<div class="modal fade" id="accessModal" tabindex="-1" role="dialog">
		<div id="accessModalPanel"class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 id="accessModalTitle" class="modal-title">회원가입</h4>
				</div>
				<div id="accessModalContent" class="modal-body">
					
				</div>
				<div id="accessModalFooter" class="modal-footer">
					
				</div>
			</div>
		</div>
	</div>
	<footer>
		<div class="container">
			<div class="row">
				<div class="col-md-4">
					<span class="copyright">Copyright &copy; Your Website 2014</span>
				</div>`
				<div class="col-md-4">
					<ul class="list-inline social-buttons">
						<li><a href="#"><i class="fa fa-twitter"></i></a></li>
						<li><a href="#"><i class="fa fa-facebook"></i></a></li>
						<li><a href="#"><i class="fa fa-linkedin"></i></a></li>
					</ul>
				</div>
				<div class="col-md-4">
					<ul class="list-inline quicklinks">
						<li><a href="#"></a></li>
						<li><a href="#">Terms of Use</a></li>
					</ul>
				</div>
			</div>
		</div>
	</footer>
	


	<!-- jQuery -->
	<script src="${PATH_RESOURCES}/portpolio/js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="${PATH_RESOURCES}/portpolio/js/bootstrap.min.js"></script>

	<!-- Plugin JavaScript -->
	<script
		src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
	<script src="${PATH_RESOURCES}/portpolio/js/classie.js"></script>
	<script src="${PATH_RESOURCES}/portpolio/js/cbpAnimatedHeader.js"></script>

	<!-- Contact Form JavaScript -->
	<script src="${PATH_RESOURCES}/portpolio/js/jqBootstrapValidation.js"></script>
	<script src="${PATH_RESOURCES}/portpolio/js/contact_me.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="${PATH_RESOURCES}/portpolio/js/agency.js"></script>
	<script src="${PATH_JS }/home/home.js"></script>
</body>

</html>

