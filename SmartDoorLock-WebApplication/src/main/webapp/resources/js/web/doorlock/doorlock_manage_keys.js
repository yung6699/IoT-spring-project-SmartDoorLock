//문서가 로딩되면 실행되는 작업
$(document).ready(function(){
	$('#keyList').delegate("li","click",function(a) {
		 key_info(this.dataset.key_id,this.dataset.key_email);
	});
	$("#modalCreateKey").on('hidden.bs.modal',function(){
		$("#modalCreateKeyContent, #modalCreateKeyFooter").animateCss("fadeOut",function(){
			$("#modalCreateKeyContent, #modalCreateKeyFooter").empty();
			$("#modalCreateKeyContent, #modalCreateKeyFooter").hide();
			$("#modalCreateKeyContent").append(
				`<img src="/resources/images/pages/mydoorlock/addKeyUser.png" width="300"/>
		    	<br/>
		    	<br/>
		    	<p>권한을 부여할 사용자의 '이메일'을 입력해 주세요 </p>
		    	<div class="form-horizontal form-label-left" style="margin:auto;width:70%">
		    		<div class="form-group">
		    			<div class="">
		    				<input type="text" id="dialog_email" placeholder="이메일을 입력해주세요    (ex: example@smartlock.com)" class="form-control" />
		    			</div>
		    		</div>
		    	</div>`
			);
			
			$("#modalCreateKeyFooter").append(`
				<input type="button" class="form-control btn btn-primary" onclick="key_create_check()" id="checkMemberBt" value="확인" />
			`);	
			
			$("#modalCreateKeyContent, #modalCreateKeyFooter").animateCss("fadeIn",function(){
				$("#step2").css("background-color","#B7BDD2");
				$("#modalCreateKeyContent").data("page",1);
				$("#modalCreateKeyContent, #modalCreateKeyFooter").show();
			});
		});
	});
});

// 도어락의 권한 소유자들을 출력합니다.
function doorlock_keys(dataForm){
	// 도어락 관리 -> 새로고침 을 눌렀을 경우 dataForm 에다가 undefined 가 선언되어 날라오는데 그럴 경우 만들어준다.
	// @param #doorlockList
	//
	if(dataForm == undefined){
		dataForm = {
				serial_no : $("#keyList").data('serial_no'),
				grade : $("#keyList").data('my_grade'),	
				email : $("#login_email").val()
		};
	}
	$.ajax({
		type:"post",
		url:`/${dataForm.email}/doorlock/keys`,
		dataType:"json",
		data:dataForm,
		success:function(response){
			// 새로고침을 위한 serial_no 생성
			// 열쇠에 새로운 사용자 등록을 위한 serial_no 데이터를 남겨둔다.

			$("#keyList").empty();
			response.filter(function(val){
				$("#keyList").append(
					`<li class="row well" data-key_id="${val.key_id}" data-key_email="${val.email}">
						<div class="col-xs-9 col-sm-10 col-md-12 col-lg-10">
							<div>
								<span class="attr">사용자 : </span> <span class="value">${val.email}</span>
							</div>
							<div>
								<span class="attr">열쇠 권한 : </span> <span class="value">${val.grade_name}</span>
							</div>
							<div>
								<span class="attr">열쇠 상태 : </span> <span class="value">${val.state_name}</span>
							</div>
							<div>
								<span class="attr">만료 기간: </span> <span class="value">${val.start_date} ~ ${val.expire_date}</span>
							</div>
						</div>
						<div class="col-xs-3 col-sm-2 col-lg-2 image_right">
							<i class="fa fa-4x fa-key"></i>
						</div>
					</li>`
				);
			});
		},error : function(req,stat,err){
			alert("req:"+req+"\n"+"stat:"+stat+"\n"+"err"+err);
		}
	});
}

// 권한 관리 -> 열쇠 정보 자세히 열람
function key_info(key_id,key_email){
	console.log(key_id);
	if($("#login_email").val()==key_email){
		alert("자기 자신의 키는 열쇠 관리에서 해주시기 바랍니다.");
	}else{
		$("#keyModal").modal('show');
		key_info_request(key_id);	
	}
}

// 권한 관리 -> 열쇠 정보 자세히 열람 -> 모달창에 데이터를 뿌린다.
function key_info_request(key_id){
	var dataForm = {
			key_id:key_id,
			email: $("#login_email").val()
	};
	$.ajax({
		type:"post",
		url:`/${dataForm.email}/doorlock/keys/detail`,
		dataType:"json",
		data:dataForm,
		success:function(response){
			$("#keyModalContent").html("");
			$("#keyModalContent").data("key-state",response.state);
			$("#keyModalContent").data("key-grade",response.grade);
			
			$("#keyModalContent").append(`
				<div class="form-group">
					<label>사용자</label>
					<input id="keyModal_email" class="form-control" type="text"  value="${response.email}" readonly/>
				</div>
				<div class="row">
					<div class="col-xs-12 col-md-6">
						<div class="form-group">
							<label>열쇠 등급</label>
							<select id="keyModal_grade" class="form-control select2_single" disabled>
								<option value="MASTER">마스터</option>
								<option value="MANAGER">매니저</option>
								<option value="MEMBER" disabled>일반 사용자</option>
							</select>
						</div>
					</div>
					<div class="col-xs-12 col-md-6">
						<div class="form-group">
							<label>열쇠 상태</label>
							<select id="keyModal_state" class="form-control select2_single" disabled>
								<option value="ACTIVE">유효한 열쇠</option>
								<option value="INACTIVE">유효하지 않은 열쇠</option>
								<option value="GRANTING" disabled>권한 부여 중</option>
							</select>
						</div>				
					</div>
				</div>
				
				<div class="form-group">
					<label>사용 기간 만료 날짜</label>
					<input type="text" id="keyModal_start_expire_date" class="date-picker form-control"
					value="${moment(new Date(response.start_date)).format('L')} - ${moment(new Date(response.expire_date)).format('L')}"
					disabled/>
				</div>
				<div class="row">
					<div class="col-xs-6 col-md-6">
						<div class="form-group">
							<label>마지막 수정자</label>
							<input class="form-control" type="text" value="${response.updt_email}" readonly/>
						</div>
					</div>
					<div class="col-xs-6 col-md-6">
						<div class="form-group">
							<label>마지막 수정날짜</label>
							<input  class="form-control" type="text" value="${response.updt_dt}" readonly/>
						</div>
					</div>
				</div>
			`);
			
			
			if(response.state=='GRANTING'){
				$("#keyModal_state option[value=ACTIVE]").attr("disabled",true);
				$("#keyModal_state option[value=INACTIVE]").attr("disabled",true);
				$("#keyModal_state option[value=GRANTING]").attr("disabled",false);
			}else{
				$("#keyModal_state option[value=ACTIVE]").attr("disabled",false);
				$("#keyModal_state option[value=INACTIVE]").attr("disabled",false);
				$("#keyModal_state option[value=GRANTING]").attr("disabled",true);
				
			}
			
			if(response.my_grade == 'MASTER'){
				$("#keyModal_updateButton").show();
				$("#keyModal_deleteButton").show();
				$("#keyModal_grade option[value=MASTER]").attr("disabled",true);
				$("#keyModal_grade option[value=MANAGER]").attr("disabled",false);
				$("#keyModal_grade option[value=MEMBER]").attr("disabled",false);

			}else if(response.my_grade=='MANAGER' && response.grade=="MEMBER"){
				$("#keyModal_grade option[value=MASTER]").attr("disabled",true);
				$("#keyModal_grade option[value=MANAGER]").attr("disabled",true);
				$("#keyModal_grade option[value=MEMBER]").attr("disabled",false);
				$("#keyModal_grade").val("MEMBER");
				$("#keyModal_updateButton").show();
				$("#keyModal_deleteButton").show();
			}else{

				$("#keyModal_grade option[value=MASTER]").attr("disabled",true);
				$("#keyModal_grade option[value=MANAGER]").attr("disabled",true);
				$("#keyModal_grade option[value=MEMBER]").attr("disabled",true);
				
				$("#keyModal_updateButton").hide();
				$("#keyModal_deleteButton").hide();	
			}
			$("#keyModal_state").val(response.state);
			$("#keyModal_grade").val(response.grade);
			
			
			// keyModalFooter 에다가 버튼 생성 수정 및 삭제
			// <button id="keyModal_updateButton" 수정하기
			// <button id="keyModal_deleteButton" 삭제하기
			$("#keyModal_updateButton").removeClass("btn-info");
			$("#keyModal_updateButton").addClass("btn-primary");
			$("#keyModal_updateButton").html("수정 하기");
			$("#keyModal_updateButton").unbind('click');
			$("#keyModal_updateButton").bind('click',response,(event)=>{key_info_updatable(response)});
			
			$("#keyModal_deleteButton").removeClass("btn-defualt");
			$("#keyModal_deleteButton").addClass("btn-danger");
			$("#keyModal_deleteButton").html("삭제 하기");
			$("#keyModal_deleteButton").unbind('click');
			$("#keyModal_deleteButton").bind('click',response,(event)=>{key_info_delete(response)});
			
			
			$('#keyModal_start_expire_date').daterangepicker({
			    startDate: moment(new Date(response.start_date)).format('L'),
			    endDate: moment(new Date(response.expire_date)).format('L')
			}, function(start, end, label) {
				response.start_date  = $("#keyModal_start_expire_date").data('daterangepicker').startDate.format('YYYY-MM-DD'),
				response.expire_date = $("#keyModal_start_expire_date").data('daterangepicker').endDate.format('YYYY-MM-DD')
				console.log("New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')");
			});		
		},error : function(req,stat,err){
			alert("req:"+req+"\n"+"stat:"+stat+"\n"+"err"+err);
		}
	});
}


// 권한 관리 -> 열쇠 정보 열람 -> 수정 가능한 상태로 만들기
function key_info_updatable(data){
	if(confirm("해당 도어락의 정보를 수정하시겠습니까?")){		
		$("#keyModal_start_expire_date").attr('disabled',false);
		$("#keyModal_state").attr('disabled',false)
		$("#keyModal_grade").attr('disabled',false)
		
		$("#keyModal_updateButton").removeClass("btn-primary");
		$("#keyModal_updateButton").addClass("btn-info");
		$("#keyModal_updateButton").html("수정 완료");
		$("#keyModal_updateButton").unbind('click');
		$("#keyModal_updateButton").bind('click',data,(event)=>{key_info_update(data)});
		
		$("#keyModal_deleteButton").removeClass("btn-danger");
		$("#keyModal_deleteButton").addClass("btn-default");
		$("#keyModal_deleteButton").html("수정 취소");
		$("#keyModal_deleteButton").unbind('click');
		$("#keyModal_deleteButton").bind('click',data,(event)=>{key_info_updatable_cancel(data)});
	}
}

// 권한 관리 -> 열쇠 정보 열람 -> 수정 가능한 상태 취소
function key_info_updatable_cancel(data){
	key_info_request(data.key_id);
}


// 권한 관리 -> 열쇠 정보 열람 -> 수정하기
function key_info_update(dataForm){
	console.log(dataForm);
	dataForm.start_date  = $("#keyModal_start_expire_date").data('daterangepicker').startDate.format('YYYY-MM-DD'),
	dataForm.expire_date = $("#keyModal_start_expire_date").data('daterangepicker').endDate.format('YYYY-MM-DD')
	dataForm.grade = $("#keyModal_grade").val();
	dataForm.state = $("#keyModal_state").val();
	dataForm.send_email = $("#keyModal_email").val();
		
	$.ajax({ 
		type:"POST", 
		url:'/'+$("#login_email").val()+'/doorlock/keys/update',
		dataType:"json", 
		data:dataForm,
		success:function(response){
			if(response.AJAX_RESULT=='AJAX_SUCCESS'){
				key_info_request(dataForm.key_id);
			}else{ 
				alert(response.RESULT_MSG); 
			} 
		},error :  function(req,stat,err){
			console.log(req);
			console.log(stat);
			console.log(err);
		} 
	});
}


// 권한 관리 -> 열쇠 정보 열람 -> 삭제 하기
function key_info_delete(dataForm){
	if(confirm('해당 열쇠를 삭제하시겠습니까?')){

		dataForm.send_email = $("#keyModal_email").val();
		console.log(dataForm);
		$.ajax({ 
			type:"POST",
			url:'/'+$("#login_email").val()+'/doorlock/keys/delete',
			dataType:"json",
			data:dataForm, 
			success:function(response){ 
				console.log(response);
				if(response.AJAX_RESULT=='AJAX_SUCCESS'){
					doorlock_keys();
					$("#keyModal").modal('hide');
				}else{
					alert(response.RESULT_MSG); 
				} 
			},error : function(req,stat,err){
				console.log(req);
				console.log(stat);
				console.log(err);
			} 
		});
	}
}


function key_create(){
	$("#modalCreateKey").modal("show");
	$("#step2,#step3").css("background-color","#B7BDD2");
}
function key_create_prev(){
	var index = $("#modalCreateKeyContent").data("page");
	index = index==1 ? 1 : index-1;

	if(index==1){
		$("#modalCreateKeyContent, #modalCreateKeyFooter").animateCss("fadeOut",function(){
			$("#modalCreateKeyContent, #modalCreateKeyFooter").empty();
			$("#modalCreateKeyContent, #modalCreateKeyFooter").hide();
			$("#modalCreateKeyContent").append(
				`<img src="/resources/images/pages/mydoorlock/addKeyUser.png" width="300"/>
		    	<br/>
		    	<br/>
		    	<p>권한을 부여할 사용자의 '이메일'을 입력해 주세요 </p>
		    	<div class="form-horizontal form-label-left" style="margin:auto;width:70%">
		    		<div class="form-group">
		    			<div class="">
		    				<input type="text" id="dialog_email" placeholder="이메일을 입력해주세요    (ex: example@smartlock.com)" class="form-control" />
		    			</div>
					</div>
					</div>`
			);
			$("#modalCreateKeyFooter").append(`
				<input type="button" class="form-control btn btn-primary" onclick="key_create_check()" id="checkMemberBt" value="확인" />
			`);	
			
			$("#modalCreateKeyContent, #modalCreateKeyFooter").animateCss("fadeIn",function(){
				$("#step2").css("background-color","#B7BDD2");
				$("#modalCreateKeyContent").data("page",1);
				$("#modalCreateKeyContent, #modalCreateKeyFooter").show();
			});
		});	
	}else if(index==2){
		key_create_check();
	}
}
function key_create_next(){
	var index = $("#modalCreateKeyContent").data("page");
	index = index==4 ? 4 : index+1;
	if(index==2){
		key_create_check();
	}else if(index==3){
		var dataForm={
			email : $("#modalCreateKeyContent_email").val(),
			serial_no : $("#modalCreateKeyContent_serial_no").val(),
			grade : $("#modalCreateKeyContent_grade").val()
		};
		$("#modalCreateKeyContent").data("email",dataForm.email);
		$("#modalCreateKeyContent").data("serial_no",dataForm.serial_no);
		$("#modalCreateKeyContent").data("grade",dataForm.grade);
		
		$("#modalCreateKeyContent, #modalCreateKeyFooter").animateCss("fadeOut",function(){
			$("#modalCreateKeyContent, #modalCreateKeyFooter").empty();
			$("#modalCreateKeyContent, #modalCreateKeyFooter").hide();
			$("#modalCreateKeyContent").append(
				`<img src="/resources/images/pages/mydoorlock/addKeyUser.png" width="300"/>
		    	<br/>
		    	<br/>
		    	<p>권한을 부여할 사용자의 '이메일'을 입력해 주세요 </p>
		    	<div class="form-horizontal form-label-left" style="margin:auto;width:70%">
		    		<div class="form-group">
		    			<div class="">
		    				<input type="text" id="dialog_email" placeholder="이메일을 입력해주세요    (ex: example@smartlock.com)" class="form-control" />
		    			</div>
		    		</div>
		    	</div>`
			);
			$("#modalCreateKeyFooter").append(`
				<input type="button" class="form-control btn btn-primary" onclick="key_create_check()" id="checkMemberBt" value="확인" />
			`);	
			$("#modalCreateKeyContent, #modalCreateKeyFooter").animateCss("fadeIn",function(){
				$("#step2").css("background-color","#B7BDD2");
				$("#modalCreateKeyContent").data("page",1);
				$("#modalCreateKeyContent, #modalCreateKeyFooter").show();
			});
		});		
	}else if(index==4){
		key_create_grant();
	}
}
function key_create_check(){
	
	var email = $("#login_email").val();
	var my_grade = $("#keyList").data('my_grade');
	var dataForm ={
    	email : $("#dialog_email").val(),
    	serial_no :$("#keyList").data('serial_no'),
    	my_grade : my_grade
    };
	
	
	if(dataForm.email==undefined || dataForm.email==""){
		dataForm.email=$("#modalCreateKeyContent").data("email");
	}
	$("#modalCreateKeyContent").data("email",dataForm.email);
	console.log(dataForm);
	$.ajax({
		type:"post",
		url:`/${email}/doorlock/keys/create/check`,
		dataType:"json",
		data:dataForm,
		success:function(response){
			console.log(response);
			if(response.AJAX_RESULT=='AJAX_SUCCESS'){
				$("#modalCreateKeyContent, #modalCreateKeyFooter").animateCss('fadeOut',function(){
					$("#modalCreateKeyContent, #modalCreateKeyFooter").empty();
		   			$("#modalCreateKeyContent").append(`
		   				<div class="form-horizontal form-label-left">
		   					<div class="form-group">
			   	            	<div class="col-xs-offset-3 col-xs-6">
			   	            		<label for="select-bar">부여할 권한 선택</label>
					   	          	<select name="select-bar" id="modalCreateKeyContent_grade" class="form-control select2_single">
					   	          		<OPTION VALUE="MANAGER">매니저</option>
					   	          		<OPTION VALUE="MEMBER">일반 사용자</option>
					   	          		
					   	          	</select>
				   	          	</div>
				   	        </div>
				   	        <div class="form-group">
			   	            	<div class="col-xs-offset-3 col-xs-6">
			   	            		<label for="modalCreateKeyContent_start_expire_date">만료 기간 선택</label>
		   							<input type="text" 
					   	          	id="modalCreateKeyContent_start_expire_date" 
					   	          	class=" form-control date-picker" 
					   	          	value="${moment(new Date()).format('L')} - ${moment((new Date()).setMonth((new Date()).getMonth()+1)).format('L')}" />
				   	        	</div>
				   	        </div>
			   	             <div class="well">
			   	             	<p style="font-size:2em">등록할 회원의 정보</p>
			   	              	<div class="form-group">
			   	              		<label class="control-label col-md-3 col-sm-3 col-xs-3" for="grantEmail">이메일</label>
			   	              		<div class="col-md-7 col-sm-7 col-xs-9">
			   	              			<input type="text" id="modalCreateKeyContent_email" class="form-control" value='${response.email}' readonly>
			   	              		</div>
			   	              	</div>
			   	              	<div class="form-group">
				   	              	<label class="control-label col-md-3 col-sm-3 col-xs-3" for="grantName">이름</label>
				   	              	<div class="col-md-7 col-sm-7 col-xs-9" >
				   	              		<input type="text" class="form-control" value='${response.name}' readonly>
				   	              	</div>
				   	            </div>
				   	            <div class="form-group">
				   	             	<label class="control-label col-md-3 col-sm-3 col-xs-3" for="grantPhone">전화번호</label>
				   	              	<div class="col-md-7 col-sm-7 col-xs-9" >
				   	              		<input type="text" class="form-control" value='${response.phone_num}' readonly>
				   	              	</div>
				   	            </div>
				   	            <div class="form-group">
				   	              	<label class="control-label col-md-3 col-sm-3 col-xs-3" for="grantSerial">도어락</label>
				   	              	<div class="col-md-7 col-sm-7 col-xs-9" >
				   	              		<input type="text" id="modalCreateKeyContent_serial_no" class="form-control" value='${dataForm.serial_no}' disabled>
				   	              	</div>
				   	            </div>
			   	            </div> 
		   	            </div>
		   	        `);
		   			$('#modalCreateKeyContent_start_expire_date').daterangepicker({
					    startDate : moment(new Date()).format('L'),
					    endDate  : moment((new Date()).setMonth((new Date()).getMonth()+1)).format('L')
					});
		   			
		   			$("#modalCreateKeyFooter").append(`
	   					<div class="col-xs-6"> 
			   				<input type="button" class="form-control btn btn-default" 
			   				onclick="key_create_prev()" value="이전으로" />
		   				</div>
		   				<div class="col-xs-6"> 
				   			<input type="button" class="form-control btn btn-primary"
			   				onclick="key_create_grant()" value="권한 부여하기" />
	   					</div>
		   			`);

					if(my_grade == 'MASTER'){
						$("modalCreateKeyContent_grade option[value=MASTER]").attr("disabled",true);
						$("modalCreateKeyContent_grade option[value=MANAGER]").attr("disabled",false);
						$("modalCreateKeyContent_grade option[value=MEMBER]").attr("disabled",false);
					}else if(my_grade=='MANAGER' && response.grade=="MEMBER"){
						$("#modalCreateKeyContent_grade option[value=MASTER]").attr("disabled",true);
						$("#modalCreateKeyContent_grade option[value=MANAGER]").attr("disabled",true);
						$("#modalCreateKeyContent_grade option[value=MEMBER]").attr("disabled",false);
					}else{
						$("#modalCreateKeyContent_grade option[value=MASTER]").attr("disabled",true);
						$("#modalCreateKeyContent_grade option[value=MANAGER]").attr("disabled",true);
						$("#modalCreateKeyContent_grade option[value=MEMBER]").attr("disabled",true);
					}
		   			
		   			
		   			
					$("#modalCreateKeyContent, #modalCreateKeyFooter").animateCss('fadeIn',function(){
						$("#step1,#step2").css("background-color","#5A738E");
						$("#modalCreateKeyContent").data("page",2);
			   		});
				});
			}else{
				alert(response.RESULT_MSG);
			}
		},
		error : function(request,status,error){
			console.log(request);
			console.log(status);
			console.log(error);
		}
	});
}

function key_create_grant(){
	var email = $("#login_email").val();
	var dataForm={
			email : $("#modalCreateKeyContent_email").val(),
			serial_no : $("#modalCreateKeyContent_serial_no").val(),
			grade : $("#modalCreateKeyContent_grade").val(),
			start_date : $("#modalCreateKeyContent_start_expire_date").data('daterangepicker').startDate.format('YYYY-MM-DD'),
   			expire_date : $("#modalCreateKeyContent_start_expire_date").data('daterangepicker').endDate.format('YYYY-MM-DD')
	};
	console.log(dataForm);
	$.ajax({
		type:"post",
		url:`/${email}/doorlock/keys/create`,
		dataType:"json",
		data:dataForm,
		success:function(response){
			console.log(response);
			if(response.AJAX_RESULT=='AJAX_SUCCESS'){
				alert(response.RESULT_MSG);
				$("#modalCreateKeyContent, #modalCreateKeyFooter").animateCss('fadeOut',function(){
					$("#modalCreateKeyContent, #modalCreateKeyFooter").html("");
		   			$("#modalCreateKeyContent").append(`
						<div style="text-align: center; width: 100%">
							<h1>열쇠 부여에 성공하셨습니다.</h1>
							<img src="/resources/images/pages/mydoorlock/congratulation.png"
								width="150" />
							<br />
							<br />
						</div>	
						<ul>
							<li>'일반사용자' 권한의 경우 '앱'에서 블루투스 통신을 이용해 문을 열 수 있습니다.</li>
							<li>'매니저' 권한의 경우 '웹'과'앱'에서 해당 도어락을 관리할 수 있는 권한을 얻습니다.</li>
							<li>'마스터'>'매니저'>'일반사용자' 순으로 열쇠 보유자들을 제어할 수 있습니다.</li>						
						</ul>
						<br/>
						<p style="text-align:center;">서비스 이용에 대단히 감사드립니다.</p>
						<br/>
		   			`);
		   			$("#modalCreateKeyFooter").append(`
		   					<div class="col-xs-offset-3 col-xs-6"> 
				   				<input type="button" class="form-control btn btn-default" 
				   				onclick="key_create_complete()" value="완료" />
			   				</div>
			   			`);
		   			
		   			$("#modalCreateKeyContent, #modalCreateKeyFooter").animateCss('fadeIn',function(){
						$("#step1,#step2,#step3").css("background-color","#5A738E");
						$("#modalCreateKeyContent").data("page",3);
			   		});
				});
			}else{
				alert(response.RESULT_MSG);
			}
		},
		error : function (request,status,error){
			console.log(request);
			console.log(status);
			console.log(error);
		}
	});
}
function key_create_complete(){
	doorlock_keys();
	$("#modalCreateKey").modal("hide");
	$("#modalCreateKeyContent, #modalCreateKeyFooter").html("");
}

function selectLogList(){
	var dataForm = {
		serial_no : $("#serial_no").val()
	}
	$.ajax({
		type:"post",
		url:"/"+$("#login_email").text()+"/doorlock_log_list",
		dataType:"json",
		data : dataForm,
		success : function(res){
			$("#logList").empty();
			$.each(res,function(key,obj){
				$("#logList").append( 
						`<li class="row">
							<div class="col-xs-9 col-sm-10 col-md-9 col-lg-10">
								<span>
									<span class="attr">행위자: </span>
									<span class="value">'+obj.send_email+'</span>
								</span><br />
								<span>
									<span class="attr">피행위자: </span>
									<span class="value">'+obj.recv_email+'</span>
								</span><br />
								<span>
									<span class="attr">행위자: </span>
									<span class="value">'+obj.send_email+'</span>
								</span><br />
								<span>
									<span class="attr">메세지: </span>
									<span class="value">'+obj.message+'</span>
								</span><br />
								<span>
									<span class="attr">날짜: </span>
									<span class="value">'+obj.log_date+'</span>
								</span><br />
							</div>
						</li>`
					);
			});
		},
		error : function(req,stat,err){
			alert("req:"+req+"\nstat:"+stat+"\nerr"+err);
		}
		
	});
}
