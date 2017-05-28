<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/resources/include/include.jsp"%>


<link href="${PATH_VENDORS}/animate.css/animate.css" rel="stylesheet" />

 	<script src="${PATH_VENDORS_JS}/moment/moment.min.js"></script>
 	<script src="${PATH_VENDORS_JS}/datepicker/daterangepicker.js"></script>
</head>
<body class="nav-md">
	<input type="hidden" id="login_email" value="${LOGIN_MEMBER.EMAIL}" />
	<div class="container body">
		<div class="main_container">
			<!-- 네비게이션 바  -->
			<slTag:nav-bar />
			<!-- page content -->
			<div class="right_col" role="main">
				<div class="clearfix"></div>
				<!-- 검색 기능과 키 상세검색시 쓸려고 일부러 만들어 놨다. -->
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-5">
					<div class="x_panel">
						<div class="x_title">
							<div class="col-xs-6">
								<h2>도어락 목록</h2>
							</div>
							<div class="col-xs-6">
								<ul style="color: #000;" class="nav navbar-right panel_toolbox">
									<li><a href="javascript:doorlock_info_create()">도어락 등록<i
											class="fa fa-plus"></i></a></li>
								</ul>
							</div>


							<div class="clearfix"></div>
						</div>
						<div class="x_content">
							<ul id="doorlockList" class="list-unstyled msg_list"
								style="overflow-y: scroll; height: 65em;">
							</ul>
							<div class="ln_solid"></div>
						</div>
					</div>
				</div>
				<!-- /left list -->

				<!-- right List -->
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-7"
					id="doorlock_detail">
					<div class="x_panel">
						<div class="x_title">
							<h2 id="doorlockName">&nbsp;</h2>
							<div class="clearfix"></div>
						</div>
						<div class="x_content">
							<div id="plzSelect" class="col-xs-12" style="text-align: center">
								<h2>관리할 도어락을 선택해 주세요</h2>
								<p>도어락 관리는 매니저 이상의 권한이용자들이 도어락을 관리하는 메뉴입니다.</p>
								<br /> <img width="250"
									src="${PATH_IMAGES }/pages/mydoorlock/plzSelect.png" /> <br />
								<br /> <br />
								<div class="well">
									<h2>Notice!!</h2>
									<ul style="text-align: left">
										<li>일반사용자의 권한을 가진사람은 도어락 관리를 이용할 수 없습니다</li>
										<li>일반 사용자의 권한을 가진 사람은 도어락 리스트에 도어락이 출력되지 않을 것 입니다.</li>
										<li>매니저 이상의 권한의 요청은 해당 도어락의 마스터에게 문의하시기 바랍니다.</li>
									</ul>
								</div>
							</div>

							<div id="rightPanel" class="col-xs-12" style="display: none">
								<div class="" role="tabpanel" data-example-id="togglable-tabs">
									<ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
										<li role="presentation" class="active" id="tab1">
											<a href="#tab_content1" role="tab"
											data-toggle="tab" aria-expanded="true">상세 정보</a></li>
											
										<li role="presentation" class="" id="tab2">
											<a href="#tab_content2" role="tab"
											data-toggle="tab" aria-expanded="false">
											열쇠 관리</a></li>
										<li role="presentation" class="" id="tab3">
											<a href="#tab_content3" role="tab"
											data-toggle="tab" aria-expanded="false">
											로그 보기</a></li>
									</ul>
									<div id="myTabContent" class="tab-content">
										<div role="tabpanel" class="tab-pane fade active in"
											id="tab_content1" aria-labelledby="home-tab">
											<div id="doorlockDetail" data-parsley-validate
												class="form-horizontal form-label-left">
											</div>
										</div>

										<div role="tabpanel" class="tab-pane fade" id="tab_content2"
											aria-labelledby="profile-tab">
											<div
												class="col-md-12 col-sm-12 col-xs-12 form-group pull-right top_search"
												style="height: 30px; margin-bottom: 5px; border-bottom-width: 5px; margin-top: 10px;">
												<ul class="nav navbar-right panel_toolbox">
													<li onclick="key_create()"><a>권한 사용자 추가<i class="fa fa-plus"></i></a></li>
													<li onclick="doorlock_keys(undefined)" ><a>새로 고침<i
															class="fa fa-refresh"></i></a></li>
													<!-- <li><a><i class="fa fa-check"></i></a></li> -->
												</ul>
											</div>
											<div class="x_content">
												<ul id="keyList" class="list-unstyled msg_list"
													style="overflow-y: scroll; height: 40em;">

												</ul>
												<div class="ln_solid"></div>
											</div>
										</div>

										<div role="tabpanel" class="tab-pane fade" id="tab_content3"
											aria-labelledby="profile-tab">
											<div class="x_content">
												<ul id="logList" class="list-unstyled msg_list"
													style="overflow-y: scroll; height: 40em;">

												</ul>
												<div class="ln_solid"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- /page content -->
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	
	
	<!--doorlock modal page -->
	<div class="modal fade" id="createDoorlockModal" tabindex="-1"
		role="dialog">
		<div id="accessModalPanel" class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 id="accessModalTitle" class="modal-title">도어락 등록</h4>
				</div>
				<div id="accessModalContent" class="modal-body"
					style="height: 25em;">
					<div id="createDoorlockModal_1page" style="display: none;">
						<div style="text-align: center; width: 100%">
							<h1>도어락을 등록해주세요!</h1>
							<img src="${PATH_IMAGES }/pages/mydoorlock/createDoorlock.png"
								width="150" /> <br />
							<br />
							<br />
						</div>
						<ul>
							<li>구매하신 스마트 도어락을 사용하기 위해서는 도어락을 등록해야 합니다.</li>
							<li>구매한 도어락이 이미 등록되어있는 경우 <a
								href="mailto:dydwls121200@gmail.com">문의 메일</a> 주시기 바랍니다.
							</li>
						</ul>
					</div>
					<div id="createDoorlockModal_2page" style="display: none;">
						<div class="form-group">
							<label for="modal_2page_serial_no"> 시리얼 번호
								[XXXXX-XXXXX-XXXXX-XXXXX-XXXXX 총 25자 입니다.] </label> <input type="text"
								id="modal_2page_serial_no" class="form-control" />
						</div>
						<div style="text-align: center; width: 100%">
							<h1>유효한 시리얼 번호인가요?</h1>
							<img src="${PATH_IMAGES }/pages/mydoorlock/doorlock.png"
								width="150" /> <br />
							<br />
							<br />
						</div>
					</div>
					<div id="createDoorlockModal_3page" style="display: none;">
						<div class="form-group">
							<label for="modal_3page_serial_no"> 시리얼 번호 [유효한 시리얼 번호입니다.] </label> <input
								type="text" id="modal_3page_serial_no" class="form-control" readonly />
						</div>
						<div class="form-group">
							<label for="modal_3page_doorlock_name"> 도어락 이름 </label> <input
								type="text" id="modal_3page_doorlock_name" 
								placeholder="도어락의 이름을 정해주세요"
								class="form-control" />
						</div>
						<div class="form-group">
							<label for="modal_3page_installed_place"> 설치 장소</label> <input
								type="text" id="modal_3page_installed_place"
								placeholder="설치장소를 입력해주세요"
								class="form-control" />
						</div>
					</div>
					<div id="createDoorlockModal_4page" style="display: none;">
						<div style="text-align: center; width: 100%">
							<h1>도어락 등록에 성공하셨습니다.</h1>
							<img src="${PATH_IMAGES }/pages/mydoorlock/congratulation.png"
								width="150" />
							<br />
							<br />
						</div>	
						<ul>
							<li>도어락 관리에서 해당 도어락의 관리를 양도할 수 있습니다.</li>
							<li>도어락을 등록 하게되면 마스터 권한의 열쇠를 부여받게 됩니다.</li>
							<li>도어락을 등록하면 타인에게 열쇠를 부여할 수 있습니다.</li>
						</ul>
					</div>
				</div>
				<div id="accessModalFooter" class="modal-footer">
					<button id="createDoorlockModal_NextButton"
						class="col-sm-offset-4 col-sm-4 btn btn-primary">다음</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- key modal page -->
	<div class="modal fade" id="keyModal" tabindex="-1"
		role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 id="keyModalTitle" class="modal-title">열쇠 정보</h4>
				</div>
				<div id="keyModalContent" class="modal-body">
				</div>
				<div id="keyModalFooter" class="modal-footer">
					&nbsp;
					<button id="keyModal_updateButton"
						class="col-md-offset-3 col-xs-offset-1 col-xs-5 col-md-3 btn btn-primary">수정하기</button>
					<button id="keyModal_deleteButton"
						class="col-xs-5 col-md-3 btn btn-danger">삭제하기</button>
						
				</div>
			</div>
		</div>
	</div>
	<!-- key modal page -->
	<div class="modal fade" id="modalCreateKey" tabindex="-1"
		role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 id="modalCreateKeyTitle" class="modal-title">열쇠 권한 부여</h4>
				</div>
				<div id="modalCreateKeyContentParent" class="modal-body">
					<div class="row">
		        		<div class="col-md-12 col-sm-12 col-xs-12" style="text-align:center;">
		        			<div class="x_panel">
		        				<div class="x_content">
		        					<p>스마트락 시스템에 등록된 회원들에게 권한을 부여합니다</p>
		                    		<div id="wizard" class="form_wizard wizard_horizontal">
				                    	<ul class="wizard_steps" style="padding-left: 0px;">
						                	<li>
						                		<a>
						                			<span class="step_no" style="background:#5A738E" id="step1">1</span>
						                		</a>
						                	</li>
						                	<li>
						                		<a>
						                			<span class="step_no" style="background:#B7BDD2" id="step2">2</span>
						                		</a>
						                	</li>
						                	<li>
						                		<a>
						                			<span class="step_no" style="background:#B7BDD2" id="step3">3</span>
						                		</a>
						                	</li>
						                </ul>
						                <div id="modalCreateKeyContent" data-page=1 class="row" style="height:30em;">
						                	<img src="/resources/images/pages/mydoorlock/addKeyUser.png" width="300"/>
						                	<br/>
						                	<br/>
						                	<p>권한을 부여할 사용자의 '이메일'을 입력해 주세요 </p>

						                	<div class="form-horizontal form-label-left" style="margin:auto;width:70%">
						                		<div class="form-group">
						                			<div class="">
						                				<input type="text" id="dialog_email" placeholder="이메일을 입력해주세요    (ex: example@smartlock.com)" class="form-control" />
						                			</div>
						                		</div>
						                	</div>
						                </div>
					                </div>
				                </div>
				            </div>        
				        </div>
				        <div class="form-group">
					        <div class="col-xs-offset-2 col-xs-8" id="modalCreateKeyFooter">
					        	<input type="button" class="form-control btn btn-primary" onclick="key_create_next()" id="checkMemberBt" value="확인" />
			        		</div>
		        		</div>
				    </div>
				</div>
			</div>
		</div>
	</div>

	<slTag:footer />
	

	<script src="${PATH_JS }/web/doorlock/doorlock_manage_doorlock.js"></script>
	<script src="${PATH_JS }/web/doorlock/doorlock_manage_keys.js"></script>

</body>
</html>