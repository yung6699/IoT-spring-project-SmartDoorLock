package com.sl.system.log.setter;

public interface SystemLogSetter {
/*	
		//사용법 
		2, LOG_STATE 에다가 새로 사용할 로그 관련 코드(state) 값을 넣어줍니다. 
			send_email  {송신자} 
			recv_email  {수신자} replaceAll할 형태는 항상 '{WHO}'를 지킬 수 있도록 합니다. 
			@param vo.serial_no 도어락 제품 번호
			@param vo.send_email 송신자
			@param vo.recv_email 수신자
			@param vo.state 상태
			@param vo.message 메세지
			
		3, 소스코드 작성
		SystemLogSetterVO log1 = new SystemLogSetterVO();
		log1.setSend_email(vo.getEmail());
		log1.setSerial_no(vo.getSerial_no());
		
		log1.setState("REGIST_DOORLOCK");
		String message = logSetter.getMessage("REGIST_DOORLOCK");
		message = message.replaceAll("{송신자}", vo.getEmail());
		message = message.replaceAll("{도어락이름}", vo.getDoorlock_name());
		log1.setMessage(message);
		logSetter.setLog(log1);
		
		log1.setState("KEY_GRADE_GET_MASTER");
		message = logSetter.getMessage("KEY_GRADE_GET_MASTER");
		message = message.replaceAll("{송신자}", vo.getEmail());
		message = message.replaceAll("{도어락이름}", vo.getDoorlock_name());
		log1.setMessage(message);
		logSetter.setLog(log1);
		
	
*/
	public SystemLogSetterVO builder(String state, String send_email, String recv_email, String serial_no, String message);
	public SystemLogSetterVO builder(String state, SystemLogSetterVO vo);

	public void setSystemLog(SystemLogSetterVO vo);
	public void setDoorlockLog(SystemLogSetterVO vo);
	public void setNewspeed(SystemLogSetterVO vo,String oneMember);
	
	//진영균 영역
	public void updateNewFlag(String email);
	public void deleteGrantRequest(SystemLogSetterVO vo);

}
