
$(function(){
	
	$("#doorlock_detail").hide();
	$('#doorlock_list').delegate("li","click",function() {
		var form;
		var dataForm = {
				serial_no : $(this).attr('id'),
				email : $("#login_email").text()
		}
		//document.location.href="#tab_content1";
		$("#tab_content1").addClass('active');
		$("#tab1").attr('class','active');
		$("#tab_content2").removeClass('active');
		$("#tab2").attr('class','');
		$("#tab_content3").removeClass('active');
		$("#tab3").attr('class','');
		$("#tab_content4").removeClass('active');
		$("#tab4").attr('class','');
	
		//$(this).attr('class','active');
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
				//$("#myTabContent").show();
				$("#doorlock_detail").show();
				
			},error:function(req,stat,err){
				alert("req:"+req+"\nstat:"+stat+"\nerr"+err);
				//$("#doorlock_list").append("<p>code:"+req.stat+"\n"+"message:"+req.responseText+"\n"+"error:"+err+"</p>");
			}
		});
	});
});

function edit_bt1(){
	alert('123');
	//$("#doorlock_detail").refresh();
	var dataForm = {
			key_name : $("#key_name").val(),
			serial_no : $("#serial_no").val(),
			email : $("#login_email").text()
	}
	$.ajax({
		type:"post",
		url:"/"+$("#login_email").text()+"/doorlock_edit",
		dataType:"json",
		data: dataForm,
		success : function(object){
			alert('success');
			//$("#doorlock_detail").refresh();
		},
		error : function(req,stat,err){
			alert("req:"+req+"\nstat:"+stat+"\nerr"+err);
		}
		
		
	});
}

function addMember(){
	bootbox.dialog({
        title: "This is a form in a modal.",
        message:
            '<div class="row">'+

        '<div class="col-md-12 col-sm-12 col-xs-12">'+
          '<div class="x_panel">'+
        	' <div class="x_content">'+
        	'<p>This is a basic form wizard example that inherits the colors from the selected scheme.</p>'+
                    '<div id="wizard" class="form_wizard wizard_horizontal">'+
                      '<ul class="wizard_steps">'+
		                '<li >'+
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
                      
                      '<div id="step-1">'+
                      '<form class="form-horizontal form-label-left">'+
                        '<div class="form-group">'+
                          '<label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Email<span class="required">*</span>'+
                          '</label>'+
                          '<div class="col-md-6 col-sm-6 col-xs-12">'+
                          
                            '<input type="text" id="dialog_email" required="required" class="form-control col-md-7 col-xs-12">'+
                          '</div>'+
                        '</div>'+
                      '</form>'+
                      '</div>'+
                      
/*                      '<div id="step-2">'+
                      '<form class="form-horizontal form-label-left">'+
                        '<div class="form-group">'+
                          '<label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Last Name <span class="required">*</span>'+
                          '</label>'+
                          '<div class="col-md-6 col-sm-6 col-xs-12">'+
                            '<input type="text" id="first-name" required="required" class="form-control col-md-7 col-xs-12">'+
                          '</div>'+
                        '</div>'+
                      '</form>'+
                      '</div>'+
*/
                      
                      
                     '</div>'+
                    '</div>'+
                   '</div>'+
                   
                  '</div>'+
                  '<div class="col-md-3 col-sm-3 col-xs-3" id="dialog_bt_div">'+
                  '<input type="button" class="form-control btn btn-primary " onclick="checkMember()" id="checkMemberBt" value="확인" />'+
                  '</div>'+
                 '</div>',
/*        buttons: {
            success: {
                label: "NEXT",
                className: "btn-success",
                callback: function () {
                	$("#step-1").empty();
           			$("#step-1").append(
           					'<div id="step-2">'+
                            '<form class="form-horizontal form-label-left">'+
                              '<div class="form-group">'+
                                '<label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Last Name <span class="required">*</span>'+
                                '</label>'+
                                '<div class="col-md-6 col-sm-6 col-xs-12">'+
                                  '<input type="text" id="first-name" required="required" class="form-control col-md-7 col-xs-12">'+
                                '</div>'+
                              '</div>'+
                            '</form>'+
                            '</div>'
           			);
                	var dataForm ={
                		email : $("#dialog_email").val() 
                	}
                    $.ajax({
                	    type:"post",
	               		url:"/"+$("#login_email").text()+"/doorlock_check_member",
	               		dataType:"json",
	               		data:dataForm,
	               		success : function(res){
	               			$("#step-1").empty();
	               			$("#step-1").append(
	               					'<div id="step-2">'+
	                                '<form class="form-horizontal form-label-left">'+
	                                  '<div class="form-group">'+
	                                    '<label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Last Name <span class="required">*</span>'+
	                                    '</label>'+
	                                    '<div class="col-md-6 col-sm-6 col-xs-12">'+
	                                      '<input type="text" id="first-name" required="required" class="form-control col-md-7 col-xs-12">'+
	                                    '</div>'+
	                                  '</div>'+
	                                '</form>'+
	                                '</div>'
	               			);
	               		},
	               		error : function(req,stat,err){
	               			
	               		}
                    });
                
                }
            }
        }*/
    });
}
function refresh(){
	member_list();
	
}
function checkMember(){
	var dataForm ={
    		email : $("#dialog_email").val(),
    		serial_no : $("#serial_no").val()
    	}
	//alert($("#serial_no").val());
	var ans;
	if($("#dialog_email").val()==$("#login_email").text()){
		alert('장난치냐이새끼야?');
	}else{
		$.ajax({
			type:"post",
			url:"/"+$("#login_email").text()+"/hasKey",
			dataType:"json",
			data:dataForm,
			success:function(res){
				if(res==0){
					$.ajax({
						type:"post",
				   		url:"/"+$("#login_email").text()+"/doorlock_check_member",
				   		dataType:"json",
				   		data:dataForm,
				   		success : function(res){
				   			//alert('res:'+res.email+','+res.name+','+res.phone_num);
				   			//alert('ser'+$("#serial_no").val());
				   			$("#step1").css("background","#36383a");
				   			$("#step2").css("background","#aeb0b2");
				   			
				   			$("#step-1").empty();
				   			$("#step-1").append(
				   					'<div id="step-2">'+
				   	            '<form class="form-horizontal form-label-left">'+
				   	              '<div class="form-group">'+
				   	                '<label class="control-label col-md-3 col-sm-3 col-xs-12" for="grantEmail">Email'+
				   	                '</label>'+
				   	                '<div class="col-md-6 col-sm-6 col-xs-12">'+
				   	                  '<input type="text" id="grantEmail" class="form-control col-md-7 col-xs-12" value='+res.email+' disabled>'+
				   	                '</div>'+
				   	              '</div>'+
				   	              '<div class="form-group">'+
					   	              '<label class="control-label col-md-3 col-sm-3 col-xs-12" for="grantName">Name'+
					   	              '</label>'+
					   	              '<div class="col-md-6 col-sm-6 col-xs-12" >'+
					   	                '<input type="text" id="grantName" class="form-control col-md-7 col-xs-12" value='+res.name+' disabled>'+
					   	              '</div>'+
					   	          '</div>'+
						   	      '<div class="form-group">'+
					   	              '<label class="control-label col-md-3 col-sm-3 col-xs-12" for="grantPhone">Phone'+
					   	              '</label>'+
					   	              '<div class="col-md-6 col-sm-6 col-xs-12" >'+
					   	                '<input type="text" id="grantPhone" class="form-control col-md-7 col-xs-12" value='+res.phone_num+' disabled>'+
					   	              '</div>'+
					   	          '</div>'+
						   	       '<div class="form-group">'+
					   	              '<label class="control-label col-md-3 col-sm-3 col-xs-12" for="grantSerial">도어락'+
					   	              '</label>'+
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
				   			//$("#select-bar").selectmenu();
				   		},
				   		error : function(req,stat,err){
				   			alert('err');
				   		}
					});
				}
			},
			error: function(req,stat,err){
				alert('err');
			}
		});
		if(ans==0){
			
		}
	}
}
function grantKey(){
	var dataForm={
			email : $("#grantEmail").val(),
			name : $("#grantName").val(),
			phone_num : $("#grantPhone").val(),
			serial_no : $("#grantSerial").val(),
			grade : $("#select-bar option:selected").val(),
			crt_email : $("#login_email").text(),
	}
	//alert('grade:'+$("#select-bar option:selected").val());
	
	$.ajax({
		type:"post",
		url:"/"+$("#login_email").text()+"/doorlock_grant_key",
		dataType:"json",
		data:dataForm,
		success:function(res){
			if(res==1){
				alert("성공");
			}else{
				alert("insert 실패");
			}
			member_list();
		},
		error : function (req,stat,err){
			alert("err");
		}
	});
}
function member_list(){
	var dataForm={
			serial_no : $("#serial_no").val()
	}
	$.ajax({
		type:"post",
		url:"/"+$("#login_email").text()+"/doorlock_member_list",
		dataType:"json",
		data:dataForm,
		success:function(object){
			$("#memberList").empty();
			$.each(object,function(key,val){
				$("#memberList").append('<li class=\"row\">'+
/*						'<div class=\"col-xs-3 col-sm-2 col-md-3 col-lg-2\" class=\"image\">'+
						'<img src=\"${PATH_IMAGES}/img.jpg\" width=\"65\" alt=\"img\" />'+
						'</div>'+*/
						'<div class=\"col-xs-9 col-sm-10 col-md-9 col-lg-10\">'+
						'<span>'+
						'<span class=\"attr\">이메일 : </span>'+
						'<span class=\"value\">'+val.email+'</span>'+
						'</span><br />'+
						'<span class=\"attr\">등급 : </span>'+
						'<span class=\"value\">'+val.grade+'</span>'+
						'</span><br />'+
						'<span class=\"attr\">상태 : </span>'+
						'<span class=\"value\">'+val.state+'</span>'+
						'</span><br />'+
						'<span class=\"attr\">시작일 : </span>'+
						'<span class=\"value\">'+val.start_date+'</span>'+
						'</span><br />'+
						'<span class=\"attr\">만료일 : </span>'+
						'<span class=\"value\">'+val.expired_date+'</span>'+
						'</span><br />'+
						
						
						'</div>'+
						'<div style=\"padding: 10px 10px 0px 0px;\" class=\"image_right\">'+
						'<i class=\"fa fa-4x fa-lock\"></i>'+
						'</div></li>'
				);
			});
			
		},error: function(req,stat,err){
			
		}
	});
}
/*<span> <span class="attr">열쇠 이름 : </span> <span
class="value">어쩌구 저쩌구</span>
</span> <br /> */

