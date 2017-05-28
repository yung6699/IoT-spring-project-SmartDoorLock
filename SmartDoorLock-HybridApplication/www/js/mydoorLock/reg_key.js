$(function(){
	$('#reg_key_page').on("pagebeforeshow",function(){
		var member_info = JSON.parse(window.localStorage.getItem('MEMBER_INFO')),
		       temp = member_info.key_start_date+" ~ "+member_info.key_expire_date;
		
		$("#member_name").html(member_info.member_name);
		$("#member_email").html(member_info.member_email);
		$("#grade_name").html(member_info.grade_name);
		$("#key_crt_dt").html(member_info.key_crt_dt);
		$("#state_name").html(member_info.state_name+'('+member_info.state+')');
		$("#key_date").html(temp);
	});

});