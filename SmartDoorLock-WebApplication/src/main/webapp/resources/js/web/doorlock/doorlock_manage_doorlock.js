//문서가 로딩되면 실행되는 작업
$(document).ready(function(){

	leftPanelInit();
	rightPanelInit();
	
	$('#doorlockList').delegate("li","click",function() {
		var dataForm = {
				serial_no : $(this).data('serial_no'),
				grade : $(this).data('my_grade'),
				email : $("#login_email").val()
		};
		doorlockSelect(dataForm);
	});
});

//화면의 왼쪽 패널의 초기화
function leftPanelInit(){ 
	var dataForm= {email : $("#login_email").val()};
	$.ajax({
		type:"post",
		url:"/"+dataForm.email+"/doorlock/list",
		dataType:"json",
		data:dataForm,
		success:function(response){
			$("#doorlockList").html(" ");
			for(let index in response){
				$("#doorlockList").append(
					`<li class="row well" data-my_grade="${response[index].grade}" data-serial_no="${response[index].serial_no}" >
						<div class="col-xs-9 col-sm-10 col-md-12 col-lg-10">
							<div>
								<span class="attr">열쇠 이름(사용자 정의) : </span> <span class="value">${response[index].key_name}</span>
							</div>
							<div>
								<span class="attr">열쇠 권한 : </span> <span class="value">${response[index].grade_name}</span>
							</div>
							<div>
								<span class="attr">도어락 이름 : </span> <span class="value">${response[index].doorlock_name}</span>
							</div>
							<div>
								<span class="attr">도어락 마스터 : </span> <span class="value">${response[index].email}</span>
							</div>
							<div>
								<span class="attr">설치 위치 : </span> <span class="value">${response[index].installed_place}</span>
							</div>
						</div>
						<div class="col-xs-3 col-sm-2 col-lg-2 image_right">
							<i class="fa fa-4x fa-lock"></i>
						</div>
					</li>`
				);
			}
		},complete : function(){
			/*도어락 리스트 클릭 이벤트 삽입*/
			
		},error : function(req,stat,err){
			alert("req:"+req+"\n"+"stat:"+stat+"\n"+"err"+err);
		}
	});
}

//화면의 오른쪽 패널의 초기화
function rightPanelInit(){
	$("#rightPanel").hide();
	$("#plzSelect").animateCss('fadeIn',function(){
		$("#plzSelect").show();
		$("#rightPanel").hide();
	});
}

//도어락의 상세 정보를 표시한다.
function doorlockSelect(dataForm){
	$("#keyList").data("my_grade",dataForm.grade);
	$("#keyList").data("serial_no",dataForm.serial_no);
	
	$('#myTab a[href="#tab_content1"]').tab('show')
	doorlock_info(dataForm);
	doorlock_keys(dataForm);
	doorlock_logs(dataForm);
	//doorlock_grantings(response.GRANTING);
			
	
}

//도어락의 상세정보를 표시합니다.
function doorlock_info(dataForm){ 
	$.ajax({
		type:"post",
		url:`/${dataForm.email}/doorlock/detail`,
		dataType:"json",
		data:dataForm,
		success:function(response){
			console.log(response);
			$("#doorlockName").html(response.doorlock_name);
			$("#doorlockDetail").empty();
			$("#doorlockDetail").append(
				`<div class="form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12" for="doorlock_name">도어락 이름 :</label>
					<div class="col-md-8 col-sm-8 col-xs-12">
						<input type="text" class="form-control" id="doorlock_name" value="${response.doorlock_name}" readonly>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12" for="key_name">열쇠 이름 :</label>
					<div class="col-md-8 col-sm-8 col-xs-12">
						<input type="text" class="form-control" id="key_name" value="${response.key_name}" readonly>
					</div>
				</div>
				 
				<div class="form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12" for="crt_email">마스터 :</label>
					<div class="col-md-8 col-sm-8 col-xs-12">
						<input type="text" class="form-control" id="crt_email" value="${response.email}" readonly>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12" for="crt_date">등록 일자 :</label>
					<div class="col-md-8 col-sm-8 col-xs-12">
						<input type="text" class="form-control" id="crt_date" value="${response.crt_dt}" readonly>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12" for="installed_place">설치 지역 :</label>
					<div class="col-md-8 col-sm-8 col-xs-12">
						<input type="text" class="form-control" id="installed_place" value="${response.installed_place}" readonly>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12" for="serial_no">시리얼 번호:</label>
					<div class="col-md-8 col-sm-8 col-xs-12">
						<input type="text" class="form-control" id="serial_no" value="${response.serial_no}" readonly>
					</div>
				</div>`
			);
		
			if(response.email == $("#login_email").val()){
				console.log("asd");
				$("#doorlockDetail").append(`
					<div class="ln_solid"></div>
						<div class="form-group">
							<div class="col-xs-6">
								<button class="form-control btn btn-primary" id="updateDetail">수정하기</button>'+
							</div>
							<div class="col-xs-6">
								<button class="form-control btn btn-danger" id="deleteDetail">삭제하기</button>'+
						</div>
					</div>
				`);
			}
			// 이런 작성방법은 콜백에서 허용하질 않음. event 파라메터가 들어가질 앓음 씨발거.
			//슈발 어째선지 bind 에다가 직접 메서드 때려박았더니 안됨.. bind, click, on 도 마찬가지임. 이건  syntax 에러인듯.
			//$("#updateDetail").bind('click',dataForm,doorlock_info_updatable(event));
			
			$("#updateDetail").unbind('click');
			$("#updateDetail").bind('click',dataForm,function(event){doorlock_info_updatable(dataForm);});
			$("#deleteDetail").unbind('click');
			$("#deleteDetail").bind('click',dataForm,function(event){doorlock_info_delete(dataForm);});
		},error : function(req,stat,err){
			alert("req:"+req+"\n"+"stat:"+stat+"\n"+"err"+err);
		},complete : function(){
			if($("#rightPanel").css('display')=="none"){
				$("#plzSelect").animateCss('fadeOut',function(){
					$("#plzSelect").hide();
					$("#rightPanel").show();
					$("#rightPanel").animateCss('fadeIn',function(){});
				});	
			}
		}
	});
}

//수정가능한 상태로 만들기
function doorlock_info_updatable(dataForm){
	if(confirm("해당 도어락의 정보를 수정하시겠습니까?")){
		console.log(this.event);
		console.log(dataForm);
		console.log("doorlock_info_updatable(dataform)");
		$("#doorlockDetail #installed_place").attr('readonly',false);
		$("#doorlockDetail #doorlock_name").attr('readonly',false);
		
		$("#updateDetail").removeClass("btn-primary");
		$("#updateDetail").addClass("btn-info");
		$("#updateDetail").html("수정 완료");
		$("#updateDetail").unbind('click');
		$("#updateDetail").bind('click',dataForm,(event)=>{doorlock_info_update(dataForm)});
		
		$("#deleteDetail").removeClass("btn-danger");
		$("#deleteDetail").addClass("btn-default");
		$("#deleteDetail").html("수정 취소");
		$("#deleteDetail").unbind('click');
		$("#deleteDetail").bind('click',dataForm,(event)=>{doorlock_info_updatable_cancel(dataForm)});
	}
}

//수정가능한 상태 취소
function doorlock_info_updatable_cancel(dataForm){
	console.log(this.event);
	console.log(dataForm);
	console.log("doorlock_info_updatable_cancel(dataform)");
	alert('도어락의 기본정보 수정을 취소합니다.');
	doorlockSelect(dataForm);
}

//수정하기
function doorlock_info_update(dataForm){
	console.log(this);
	console.log(dataForm);
	console.log("doorlock_info_update(dataform)");
	dataForm.installed_place = $("#installed_place").val();
	dataForm.doorlock_name = $("#doorlock_name").val();
	console.log(dataForm);
	
	$.ajax({
		type:"POST",
		url:"/"+dataForm.email+"/doorlock/update",
		dataType:"json",
		data:dataForm,
		success:function(response){
			if(response.AJAX_RESULT=='AJAX_SUCCESS'){
				alert(response.RESULT_MSG);
				leftPanelInit();
				doorlockSelect(dataForm);
			}else{
				alert(response.RESULT_MSG);
			}
		},error : function(req,stat,err){
			alert("req:"+req+"\n"+"stat:"+stat+"\n"+"err"+err);
		}
	});
}

//도어락 삭제하기
function doorlock_info_delete(dataForm){
	if(confirm('해당 도어락을 정말 삭제하시겠습니까?')){
		console.log(this.event);
		console.log(dataForm);
		console.log("doorlock_info_delete(dataform)");
		dataForm.installed_place = $("#installed_place").val();
		dataForm.doorlock_name = $("#doorlock_name").val();
		$.ajax({
			type:"POST",
			url:"/"+dataForm.email+"/doorlock/delete",
			dataType:"json",
			data:dataForm,
			success:function(response){
				console.log(response);
				if(response.AJAX_RESULT=='AJAX_SUCCESS'){
					alert(response.RESULT_MSG);
					location.href="/"+dataForm.email+"/doorlock/";
				}else{
					alert(response.RESULT_MSG);
				}
			},error : function(req,stat,err){
				alert("req:"+req+"\n"+"stat:"+stat+"\n"+"err"+err);
			}
		});
	}
}

//도어락을 등록하는 창을 띄운다.
function doorlock_info_create(){ 
	var dataForm= {email : $("#login_email").val()};
	
	var modalPage=[
		$("#createDoorlockModal_1page"),
		$("#createDoorlockModal_2page"),
		$("#createDoorlockModal_3page"),
		$("#createDoorlockModal_4page")
	];
	$("#createDoorlockModal").on('hidden.bs.modal',function(){
		modalPage.filter((value,index,all)=>{
			//modal Page Init
			value.hide();
		});
		$("#createDoorlockModal_NextButton").data("page-index",1);
	 	$("#createDoorlockModal_NextButton").unbind('click');
	 	
	});
	$("#createDoorlockModal").modal('show');
	modalPage[0].show();
	$("#createDoorlockModal_NextButton").data("page-index",1);
	modalPage[0].animateCss('fadeIn',function(){});
	$("#createDoorlockModal_NextButton").click(function(){
		var email = $("#login_email").val();
		let index = parseInt($(this).data("page-index"));
		switch(index){
		case 1:
			next(index);
			break;
		case 2:
			$.ajax({
				url:`/${email}/doorlock/create/check`,
				type:"post",
				data:{serial_no:$("#modal_2page_serial_no").val()},
				dataType:'json',
				success:function(res){
					if('AJAX_SUCCESS'==res.AJAX_RESULT){
						$("#modal_3page_serial_no").val($("#modal_2page_serial_no").val());
						next(index);
					}else{
						alert(res.RESULT_MSG);
					}
				},error : function(req,stat,err){
					alert("req:"+req+"\n"+"stat:"+stat+"\n"+"err"+err);
				}
			});
			break;
		case 3:
			var dataForm = {
					serial_no:$("#modal_3page_serial_no").val(),
					doorlock_name:$("#modal_3page_doorlock_name").val(),
					installed_place:$("#modal_3page_installed_place").val()
			};
			console.log(dataForm);
			$.ajax({
				url:`/${email}/doorlock/create`,
				type:"post",
				data:dataForm,
				dataType:'json',
				success:function(res){
					if('AJAX_SUCCESS'==res.AJAX_RESULT){
						next(index);
					}else{
						alert(res.RESULT_MSG);
					}
				},error : function(req,stat,err){
					alert("req:"+req+"\n"+"stat:"+stat+"\n"+"err"+err);
				}
			});
			break;
		case 4:
			var dataForm = {
				email :$("#login_email").val(),
				serial_no :$("#modal_3page_serial_no").val()
			};
		
			$("#createDoorlockModal").modal('hide');
			doorlockSelect(dataForm);
			leftPanelInit();
			break;
		default:
			alert("잘못된 접근 입니다. 새로고침 해주세용");
			break;
			
		}
		
	});

	function next(index){
		console.log(modalPage);
		modalPage[index-1].animateCss('fadeOut',function(){
			modalPage[index-1].hide();
			modalPage[index].show();
			modalPage[index].animateCss('fadeIn',function(){});
			$("#createDoorlockModal_NextButton").data("page-index",index+1);
		});	
	}
}

function doorlock_logs(dataForm){
	console.log(dataForm);
	$.ajax({
		type:"POST",
		url:"/"+dataForm.email+"/doorlock/logs",
		dataType:"json",
		data:dataForm,
		success:function(response){
			console.log(response);
			$("#logList").html("");
			response.filter((item)=>{
				var ImageAndReqeust = getImageMessage(dataForm.email,item);
				
				$("#logList").append(
					`<li class='row well'>
						<div class='col-xs-9 col-sm-10 col-md-12 col-lg-10'>
							<div>
								<span class='attr'>날 짜 : </span> <span class='value'>${item.log_date}</span>
							</div>
							<div>
								<span class='attr'>내 용 : </span> <span class='value'>${item.message}</span>
							</div>
						</div>
						<div class='col-xs-3 col-sm-2 col-lg-2 image_right'>
							<img width='50' src='/resources/images/pages/main/newspeed/${ImageAndReqeust.image}'/>	     
						</div>
					</li>`
				);
			});
			
		},error : function(req,stat,err){
			console.log(req);
			console.log(stat);
			console.log(err);
		}
	});
}
function getImageMessage(email,item){
	console.log(item);
	var image = "";
	      
	switch(item.state){
		case "WEB_DOORLOCK_KEY_CREATE_REQUEST":
			image = "DOORLOCK_KEY_CREATE_REQUEST.png";
			break;
		case "APP_DOORLOCK_KEY_CREATE_REQUEST":
			image = "DOORLOCK_KEY_CREATE_REQUEST.png";
			break;
		case "WEB_KEY_DELETE":
			image = "KEY_DELETE.png";
			break;
		case "WEB_DOORLOCK_CREATE_MASTER":
			image = "DOORLOCK_CREATE_MASTER.png";
			break;
		case "WEB_DOORLOCK_CREATE":
			image = "DOORLOCK_CREATE.png";
			break;
		case "WEB_DOORLOCK_DELETE":
			image = "DOORLOCK_DELETE.png";
			break;
		case "WEB_DOORLOCK_KEY_CREATE":
			image = "DOORLOCK_KEY_CREATE.png";
			break;
		case "WEB_DOORLOCK_KEY_DELETE":
			image = "DOORLOCK_KEY_DELETE.png";
			break;
		case "WEB_DOORLOCK_KEY_UPDATE":
			image = "DOORLOCK_KEY_UPDATE.png";
			break;
		case "WEB_NEWSPEED_KEY_RESPONSE_ACCEPT":
			image = "NEWSPEED_KEY_RESPONSE_ACCEPT.png";
			break;
		case "WEB_NEWSPEED_KEY_RESPONSE_REFUSE":
			image = "NEWSPEED_KEY_RESPONSE_REFUSE.png";
			break;
		case "WEB_ACCOUNT_DELETE":
			image = "ACCOUNT_DELETE.png";
			break;
		case "APP_KEY_DELETE":
			image = "KEY_DELETE.png";
			break;
		case "APP_DOORLOCK_CREATE":
			image = "DOORLOCK_CREATE.png";
			break;
		case "APP_DOORLOCK_CREATE_MASTER":
			image = "DOORLOCK_CREATE_MASTER.png";
			break;
		case "APP_DOORLOCK_DELETE":
			image = "DOORLOCK_DELETE.png";
			break;
		case "APP_DOORLOCK_KEY_CREATE":
			image = "DOORLOCK_KEY_CREATE.png";
			break;
		case "APP_DOORLOCK_KEY_DELETE":
			image = "DOORLOCK_KEY_DELETE.png";
			break;
		case "APP_DOORLOCK_KEY_UPDATE":
			image = "DOORLOCK_KEY_UPDATE.png";
			break;
		case "APP_NEWSPEED_KEY_RESPONSE_ACCEPT":
			image = "NEWSPEED_KEY_RESPONSE_ACCEPT.png";
			break;
		case "APP_NEWSPEED_KEY_RESPONSE_REFUSE":
			image = "NEWSPEED_KEY_RESPONSE_REFUSE.png";
			break;
		case "SUB_KEY_EXPIRE":
			image = "SUB_KEY_EXPIRE.png";
			break;
		case "HW_KEY_OPEN":
			image = "HW_KEY_OPEN.png";
			break;
//FAIL------------------------------------------------------------->
		case "WEB_KEY_DELETE_FAIL":
			image = "KEY_DELETE_FAIL.png";
			break;
		case "WEB_DOORLOCK_CREATE_MASTER_FAIL":
			image = "DOORLOCK_CREATE_MASTER_FAIL.png";
			break;
		case "WEB_DOORLOCK_DELETE_FAIL":
			image = "DOORLOCK_DELETE_FAIL.png";
			break;
		case "WEB_DOORLOCK_KEY_CREATE_FAIL":
			image = "DOORLOCK_KEY_CREATE_FAIL.png";
			break;
		case "WEB_DOORLOCK_KEY_CREATE_REQUEST_FAIL":
			image = "DOORLOCK_KEY_CREATE_REQUEST_FAIL.png";
			break;
		case "WEB_DOORLOCK_KEY_DELETE_FAIL":
			image = "DOORLOCK_KEY_DELETE_FAIL.png";
			break;
		case "WEB_DOORLOCK_KEY_UPDATE_FAIL":
			image = "DOORLOCK_KEY_UPDATE_FAIL.png";
			break;
		case "WEB_NEWSPEED_KEY_RESPONSE_ACCEPT_FAIL":
			image = "NEWSPEED_KEY_RESPONSE_ACCEPT_FAIL.png";
			break;
		case "WEB_NEWSPEED_KEY_RESPONSE_REFUSE_FAIL":
			image = "NEWSPEED_KEY_RESPONSE_REFUSE_FAIL.png";
			break;
		case "WEB_ACCOUNT_DELETE_FAIL":
			image = "ACCOUNT_DELETE_FAIL.png";
			break;
		case "APP_KEY_DELETE_FAIL":
			image = "KEY_DELETE_FAIL.png";
			break;
		case "APP_DOORLOCK_CREATE_FAIL":
			image = "DOORLOCK_CREATE_FAIL.png";
			break;
		case "APP_DOORLOCK_CREATE_MASTER_FAIL":
			image = "DOORLOCK_CREATE_MASTER_FAIL.png";
			break;
		case "APP_DOORLOCK_DELETE_FAIL":
			image = "DOORLOCK_DELETE_FAIL.png";
			break;
		case "APP_DOORLOCK_KEY_CREATE_FAIL":
			image = "DOORLOCK_KEY_CREATE_FAIL.png";
			break;
		case "APP_DOORLOCK_KEY_CREATE_REQUEST_FAIL":
			image = "DOORLOCK_KEY_CREATE_REQUEST_FAIL.png";
			break;
		case "APP_DOORLOCK_KEY_DELETE_FAIL":
			image = "DOORLOCK_KEY_DELETE_FAIL.png";
			break;
		case "APP_DOORLOCK_KEY_UPDATE_FAIL":
			image = "DOORLOCK_KEY_UPDATE_FAIL.png";
			break;
		case "APP_NEWSPEED_KEY_RESPONSE_ACCEPT_FAIL":
			image = "NEWSPEED_KEY_RESPONSE_ACCEPT_FAIL.png";
			break;
		case "APP_NEWSPEED_KEY_RESPONSE_REFUSE_FAIL":
			image = "NEWSPEED_KEY_RESPONSE_REFUSE_FAIL.png";
			break;
		case "SUB_KEY_EXPIRE_FAIL":
			image = "SUB_KEY_EXPIRE_FAIL.png";
			break;
		case "HW_KEY_OPEN_FAIL":
			image = "HW_KEY_OPEN_FAIL.png";
			break;
		default:
			image = "default.png";
			break;
	}
		var result = {image:image};
	return result;
}
