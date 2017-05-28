
$(function(){
	$("#doorlock_detail").hide();
	
	/*멤버 리스트 클릭 시*/
	$('#memberList').delegate("li>div>a","click",function() {
		alert($(this).attr('id')+","+$("#serial_no").val());
		var dataForm = {
				serial_no : $("#serial_no").val(),
				email : $(this).attr('id'),
				send_email : $("#login_email").text(),
				grade : $("#grade").val()
		}
		$.ajax({
			type:"post",
			url:"/"+$("#login_email").text()+"/doorlock_delete",
			dataType:"json",
			data:dataForm,
			success:function(object){
				member_list();
			},
			error : function(req,stat,err){
				alert("req:"+req+"\n"+"stat:"+stat+"\n"+"err"+err);
			}
		});
	});
	
	/*도어락 리스트 클릭 시*/
	$('#doorlock_list').delegate("li","click",function() {
		var dataForm = {
				serial_no : $(this).attr('id'),
				email : $("#login_email").text()
		};
		doorlock_detail(dataForm);
		/*
		해당 ajax 는 doorlock_detail 로직하고 공통됨. 더 만들어 사용할 필요 없음
		$.ajax({
			type:"post",
			url:"/"+$("#login_email").text()+"/doorlock_detail",
			dataType:"json",
			data:dataForm,
			success:function(object){
				$("#doorlock_name").val(object.doorlock_name);
				$("#key_name").val(object.key_name);
				$("#crt_date").val(object.crt_date);
				$("#installed_place").val(object.installed_place);
				$("#installed_place2").val(object.installed_place);
				$("#crt_email").val(object.crt_email);
				$("#serial_no").val(object.serial_no);
				$("#doorlock_detail").show();
			},error:function(req,stat,err){
				alert("req:"+req+"\n"+"stat:"+stat+"\n"+"err"+err);
			}
		});
		*/
	});
/*	$('#doorlock_list').delegate("li","click",function() {
		location.replace("/"+$("#login_email").text()+"/doorlock");
//		document.location.hash("#tab_content1");
		var dataForm={
				serial_no : $(this).attr('id'),
				email : $("#login_email").text()
		};
		doorlock_detail(dataForm);
	});*/
	
	
});


/*
input data format dataForm={ serial_no : $(this).attr('id'), email : $("#login_email").text() };
doorlock_detail(dataForm);
*/

//수정가능한 상태로 만들기
function doorlock_detail_updatable(){
	
}
//수정하기
function doorlock_detail_update(){
	
}
//도어락 삭제하기
function doorlock_detail_remove(){
	
}

//도어락의 상세 정보를 표시한다.
function doorlock_detail(dataForm){
	$.ajax({
		type:"post",
		url:"/"+$("#login_email").text()+"/doorlock_detail",
		dataType:"json",
		data:dataForm,
		success:function(object){
			$("#doorlockDetail").empty();
			$("#doorlockDetail").append(
				'<div class="form-group">'+
					'<label class="control-label col-md-3 col-sm-3 col-xs-12" for="doorlock_name">도어락 이름 :</label>'+
					'<div class="col-md-8 col-sm-8 col-xs-12">'+
						'<input type="text" class="form-control" id="doorlock_name" value="'+object.doorlock_name+'" readonly>'+
					'</div>'+
				'</div>'+
				//여기 삭제하라고 하면 삭제하겠음. 
				'<div class="form-group">'+
					'<label class="control-label col-md-3 col-sm-3 col-xs-12" for="key_name">열쇠 이름 :</label>'+
					'<div class="col-md-8 col-sm-8 col-xs-12">'+
						'<input type="text" class="form-control" id="key_name" value="'+object.key_name+'" readonly>'+
					'</div>'+
				'</div>'+
				
				'<div class="form-group">'+
					'<label class="control-label col-md-3 col-sm-3 col-xs-12" for="crt_email">마스터 :</label>'+
					'<div class="col-md-8 col-sm-8 col-xs-12">'+
						'<input type="text" class="form-control" id="crt_email" value="'+object.crt_email+'" readonly>'+
					'</div>'+
				'</div>'+
				'<div class="form-group">'+
					'<label class="control-label col-md-3 col-sm-3 col-xs-12" for="crt_date">등록 일자 :</label>'+
					'<div class="col-md-8 col-sm-8 col-xs-12">'+
						'<input type="text" class="form-control" id="crt_date" value="'+object.crt_date+'" readonly>'+
					'</div>'+
				'</div>'+
				'<div class="form-group">'+
					'<label class="control-label col-md-3 col-sm-3 col-xs-12" for="installed_place">설치 지역 :</label>'+
					'<div class="col-md-8 col-sm-8 col-xs-12">'+
						'<input type="text" class="form-control" id="installed_place" value="'+object.installed_place+'" readonly>'+
					'</div>'+
				'</div>'+
//				2016-09-12 yjcho 설치 지역 및 위치를 그냥 하나로 합침.. 데이터베이스테이블에도 존재하지 않네;;
//				'<div class="form-group">'+
//					'<label class="control-label col-md-3 col-sm-3 col-xs-12" for="installed_place2">설치 위치 :</label>'+
//					'<div class="col-md-8 col-sm-8 col-xs-12">'+
//						'<input type="text" class="form-control" id="installed_place2" value="'+object.installed_place2+'" disabled>'+
//					'</div>'+
//				'</div>'+
				'<div class="form-group">'+
					'<label class="control-label col-md-3 col-sm-3 col-xs-12" for="serial_no">시리얼 번호:</label>'+
					'<div class="col-md-8 col-sm-8 col-xs-12">'+
						'<input type="text" class="form-control" id="serial_no" value="'+object.serial_no+'" readonly>'+
					'</div>'+
				'</div>'+
				'<div class="ln_solid"></div>'+
					'<div class="form-group">'+
						'<div class="col-xs-6">'+
							'<input type="button" class="form-control btn btn-primary" id="updataDetail" value="수정하기" />'+
						'</div>'+
						'<div class="col-xs-6">'+
							'<input type="button" class="form-control btn btn-warnning" id="deleteDetail" value="삭제하기" />'+
					'</div>'+
				'</div>'
			);
			$("#doorlock_detail").show();
		},error : function(req,stat,err){
			alert("req:"+req+"\n"+"stat:"+stat+"\n"+"err"+err);
		}
	});
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
						'<li class="row">'+
							'<div class="col-xs-9 col-sm-10 col-md-9 col-lg-10">'+
								'<span>'+
									'<span class="attr">행위자: </span>'+
									'<span class="value">'+obj.send_email+'</span>'+
								'</span><br />'+
								'<span>'+
									'<span class="attr">피행위자: </span>'+
									'<span class="value">'+obj.recv_email+'</span>'+
								'</span><br />'+
								'<span>'+
									'<span class="attr">행위자: </span>'+
									'<span class="value">'+obj.send_email+'</span>'+
								'</span><br />'+
								'<span>'+
									'<span class="attr">메세지: </span>'+
									'<span class="value">'+obj.message+'</span>'+
								'</span><br />'+
								'<span>'+
									'<span class="attr">날짜: </span>'+
									'<span class="value">'+obj.log_date+'</span>'+
								'</span><br />'+
							'</div>'+
						'</li>'
					);
			});
		},
		error : function(req,stat,err){
			alert("req:"+req+"\nstat:"+stat+"\nerr"+err);
		}
		
	});
}
/*
 * 
 2016-09-02 도어락관리에서 사용자 정의 이름은 사용되지 않음. 
 
function editKeyName(){
	var dataForm = {
			key_name : $("#key_name").val(),
			serial_no : $("#serial_no").val(),
			email : $("#login_email").text()
	};
	$.ajax({
		type:"post",
		url:"/"+$("#login_email").text()+"/doorlock_edit",
		dataType:"json",
		data: dataForm,
		success : function(object){
			alert('사용자 정의 변경 성공');
		},
		error : function(req,stat,err){
			alert("req:"+req+"\nstat:"+stat+"\nerr"+err);
		}
	});
}
*/


function addMember(){
	bootbox.dialog({
        title: "This is a form in a modal.",
        message:
		    '<div class="row">'+
		        '<div class="col-md-12 col-sm-12 col-xs-12">'+
		        	'<div class="x_panel">'+
		        		'<div class="x_content">'+
		        			'<p>This is a basic form wizard example that inherits the colors from the selected scheme.</p>'+
		                    '<div id="wizard" class="form_wizard wizard_horizontal">'+
		                    
		                    	/*Step_display*/
		                    	'<ul class="wizard_steps">'+
				                	'<li>'+
				                		'<a>'+
				                			'<span class="step_no" style="background:#aeb0b2" id="step1">1</span>'+
				                		'</a>'+
				                	'</li>'+
				                	'<li>'+
				                		'<a>'+
				                			'<span class="step_no" style="background:#36383a" id="step2">2</span>'+
				                		'</a>'+
				                	'</li>'+
				                	'<li>'+
				                		'<a>'+
				                			'<span class="step_no" style="background:#36383a" id="step3">3</span>'+
				                		'</a>'+
				                	'</li>'+
				                '</ul>'+
				                /*End of Step_display*/
				                
				                /*Step-1_display*/
				                '<div id="step-1">'+
				                	'<form class="form-horizontal form-label-left">'+
				                		'<div class="form-group">'+
				                			'<label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Email</label>'+
				                			'<div class="col-md-6 col-sm-6 col-xs-12">'+
				                				'<input type="text" id="dialog_email" required="required" class="form-control col-md-7 col-xs-12">'+
				                			'</div>'+
				                		'</div>'+
				                	'</form>'+
				                '</div>'+
				                /*End of Step-1_display*/
				                
			                '</div>'+
		                '</div>'+
		            '</div>'+        
		        '</div>'+
		        '<div class="col-md-3 col-sm-3 col-xs-3" id="dialog_bt_div">'+
		        	'<input type="button" class="form-control btn btn-primary " onclick="checkMember()" id="checkMemberBt" value="확인" />'+
		        '</div>'+
		    '</div>',
    });
}
function refresh(){
	member_list();
}
function checkMember(){
	var dataForm ={
    		email : $("#dialog_email").val(),
    		serial_no : $("#serial_no").val()
    	};
	$.ajax({
		type:"post",
		url:"/"+$("#login_email").text()+"/check_member",
		dataType:"json",
		data:dataForm,
		success:function(res){
			if(res.email!=0){
				$("#step1").css("background","#36383a");
	   			$("#step2").css("background","#aeb0b2");
	   			
	   			$("#step-1").empty();
	   			$("#step-1").append(
	   					'<div id="step-2">'+
			   	            '<form class="form-horizontal form-label-left">'+
			   	              	'<div class="form-group">'+
			   	              		'<label class="control-label col-md-3 col-sm-3 col-xs-12" for="grantEmail">Email</label>'+
			   	              		'<div class="col-md-6 col-sm-6 col-xs-12">'+
			   	              			'<input type="text" id="grantEmail" class="form-control col-md-7 col-xs-12" value='+res.email+' disabled>'+
			   	              		'</div>'+
			   	              	'</div>'+
			   	              	'<div class="form-group">'+
				   	              	'<label class="control-label col-md-3 col-sm-3 col-xs-12" for="grantName">Name</label>'+
				   	              	'<div class="col-md-6 col-sm-6 col-xs-12" >'+
				   	              		'<input type="text" id="grantName" class="form-control col-md-7 col-xs-12" value='+res.name+' disabled>'+
				   	              	'</div>'+
				   	            '</div>'+ 
				   	            '<div class="form-group">'+
				   	              	'<label class="control-label col-md-3 col-sm-3 col-xs-12" for="grantPhone">Phone</label>'+
				   	              	'<div class="col-md-6 col-sm-6 col-xs-12" >'+
				   	              		'<input type="text" id="grantPhone" class="form-control col-md-7 col-xs-12" value='+res.phone_num+' disabled>'+
				   	              	'</div>'+
				   	            '</div>'+
				   	            '<div class="form-group">'+
				   	              	'<label class="control-label col-md-3 col-sm-3 col-xs-12" for="grantSerial">도어락</label>'+
				   	              	'<div class="col-md-6 col-sm-6 col-xs-12" >'+
				   	              		'<input type="text" id="grantSerial" class="form-control col-md-7 col-xs-12" value='+$("#serial_no").val()+' disabled>'+
				   	              	'</div>'+
				   	            '</div>'+
				   	            '<div class="form-group col-md-6 col-sm-6 col-xs-12">'+
					   	          	'<select name="select-bar" id="select-bar">'+
					   	          		'<option value = "MANAGER">매니저</option>'+
					   	          		'<option value = "MEMBER">사용자</option>'+
					   	          	'</select>'+
					   	        '</div>'+
			   	            '</form>'+
			   	        '</div>'
	   			);
	   			$("#dialog_bt_div").empty();
	   			$("#dialog_bt_div").append(
	   					'<input type="button" class="form-control btn btn-primary " onclick="" id="" value="이전으로" />'+
	   					'<input type="button" class="form-control btn btn-primary " onclick="grantKey()" id="grantKeyBt" value="다음으로" />'
	   			);
			}
		},
		error : function(req,stat,err){
			alert("err");
		}
	});
}
function grantKey(){
	var dataForm={
			email : $("#grantEmail").val(),
			name : $("#grantName").val(),
			phone_num : $("#grantPhone").val(),
			serial_no : $("#grantSerial").val(),
			grade : $("#select-bar option:selected").val(),
			crt_email : $("#login_email").text()
	};
	$.ajax({
		type:"post",
		url:"/"+$("#login_email").text()+"/doorlock_grant_key",
		dataType:"json",
		data:dataForm,
		success:function(res){
			if(res==1){
				alert(dataForm.email+"님에게 "+dataForm.grade+"키를 부여했습니다.");
			}else{
				alert("키 부여에 실패했습니다.");
			}
			member_list();
		},
		error : function (req,stat,err){
			alert("err : "+err);
		}
	});
}
function member_list(){
	var dataForm={ serial_no : $("#serial_no").val() };
	$.ajax({
		type:"post",
		url:"/"+$("#login_email").text()+"/doorlock_member_list",
		dataType:"json",
		data:dataForm,
		success:function(object){
			$("#memberList").empty();
			$.each(object,function(key,val){
				$("#memberList").append(
						'<li class="row">'+
							'<div class="col-xs-9 col-sm-10 col-md-9 col-lg-10">'+
								'<span>'+
									'<span class="attr">이메일 : </span>'+
									'<span class="value">'+val.email+'</span>'+
								'</span><br />'+
								'<span>'+
									'<span class="attr">등급 : </span>'+
									'<span class="value">'+val.grade+'</span>'+
								'</span><br />'+
								'<span>'+
									'<span class="attr">상태 : </span>'+
									'<span class="value">'+val.state+'</span>'+
								'</span><br />'+
								'<span>'+
									'<span class="attr">시작일 : </span>'+
									'<span class="value">'+val.start_date+'</span>'+
								'</span><br />'+
								'<span>'+
									'<span class="attr">만료일 : </span>'+
									'<span class="value">'+val.expired_date+'</span>'+
								'</span><br />'+
							'</div>'+
							'<div style="padding: 10px 10px 0px 0px;" class="image_right">'+
								'<i class="fa fa-4x fa-lock"></i>'+
							'</div>'+
							'<div style="padding: 10px 10px 0px 0px;" class="image_right">'+
								'<a id="'+val.email+'">'+
									'<i class="fa fa-4x fa-minus"></i>'+
								'</a>'+
							'</div>'+

						'</li>'
					);
				/*if(val.grade=='MANAGER'){
					$("#memberList").append('<div>삭제</div>');
				}*/
			});
		},error: function(req,stat,err){
			alert('err : '+err);
		}
	});
}

