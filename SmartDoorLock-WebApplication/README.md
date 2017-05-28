# 프로젝트 Git Repositories
 - [시스템(전체)](https://github.com/yung6699/SmartDoorLock) : (시스템 소개를 위해 존재하는 깃 repo) 
 - [웹, 서비스](../SmartDoorLock-WebApplication) : 관리자 페이지 이며 앱보다 더 넓은 관리기능 제공
 - [앱](../SmartDoorLock-HybridApplication) : 실제 도어락을 여는 주체이며 웹보다 적은 관리기능을 제공한다.
 - [하드웨어](../SmartDoorLock-Arduino) : 블루투스 모듈과 Wi-Fi모듈을 이용한 하드웨어 도어락
 - [배치 프로세스](../SmartDoorLock-LogAnalyzers) : 서비스에 발생된 로그를 분석하여 서비스 이용자에게 그래프를 제공한다.

# 웹 서비스 프로젝트 소개

- 웹, 앱, 하드웨어의 요청을 처리하는 시스템의 서비스 역할을 수행
- 관리자의 기능이 집중되어있으며 로그열람, 통계그래프를 제공하는 역할을 수행함
- 한 사람이 여러 도어락에 대한 다양한 종류의 권한을 받을 수 있음
- 한 도어락이 여러사람에게 다양한 종류의 권한을 부여할 수 있음
- 사용자:도어락 => n:m의 관계를 갖음

# 도어락에 대한 권한별 기능 이용 범위

![권한별 이용 범위](https://github.com/yung6699/SmartDoorLock-WebApplication/raw/master/docs/authfunction.png)

# URL 구조

 - 웹 : {smartlock}/ 
 - 앱 : {smartlock}/app/
 - 시스템(이메일인증 등) : {smartlock}/system/         

# 서비스DB ERD

![서비스 DB ERD](https://github.com/yung6699/SmartDoorLock-WebApplication/raw/master/docs/serviceDB.png)

# 주요 코드

 1. 뉴스피드 전송 및 로그 남기기

  ``` java 
    //SystemLogSetterImpl.java 
	
	@Override
	public void setNewspeed(SystemLogSetterVO vo,String oneMember) {
		// TODO Auto-generated method stub
		this.setDoorlockLog(vo);
		if(oneMember==null||oneMember.equals("")){
		//기본 관리자 급들에게만 전송
			List<String> user= sqlSession.selectList(NS+"getNewspeedDoorlockUser",vo);
			for(String temp : user){
				vo.setRecv_email(temp);
				sqlSession.insert(NS+"setNewspeed",vo);
				gcm.pushNotificationToGCM((String)sqlSession.selectOne(NS+"getGcmId",vo.getRecv_email()), vo.getMessage());
			}
		}else if(oneMember.equals("ALL")){
		//모두에게 로그를 전송
			List<String> user= sqlSession.selectList(NS+"getNewspeedDoorlockALLUser",vo);
			for(String temp : user){
				logger.info(temp +": sellermoon");
				vo.setRecv_email(temp);
				sqlSession.insert(NS+"setNewspeed",vo);
				gcm.pushNotificationToGCM((String)sqlSession.selectOne(NS+"getGcmId",vo.getRecv_email()), vo.getMessage());
			}
		} else {
		// 단 한명에게만 전송
			vo.setRecv_email(oneMember);
			sqlSession.insert(NS+"setNewspeed",vo);
			gcm.pushNotificationToGCM((String)sqlSession.selectOne(NS+"getGcmId",vo.getRecv_email()), vo.getMessage());
		}
	}
	

  ```

 2. 아쉬운 쿼리 코드

  ``` java 

    //WebDoorlockDAOImpl.java 
	
	@Override
	public JSONObject doorlockCreate(WebDoorlockVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(this.getClass().getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);
		
		try{
			if(Integer.valueOf((Integer)sqlSession.selectOne(NS+"doorlockCreate_0",vo))>=1){
				throw new Exception("doorlockCreate");
			}
			if(sqlSession.update(NS+"doorlockCreate_1",vo)!=1){
				throw new Exception("doorlockCreate_1");
			}
			WebDoorlockVO vo_2 = (WebDoorlockVO) sqlSession.selectOne(NS+"doorlockCreate_2",vo);
			if(vo_2==null){
				throw new Exception("doorlockCreate_2");	
			}else{
				vo.setType(vo_2.getType());	
			}
			
			Object obj_3 = sqlSession.selectOne(NS+"doorlockCreate_3",vo);
			int sort_3 = 0;
			if(obj_3==null){
				sort_3 = 0;			
			}else{
				sort_3 = (Integer) obj_3;
				vo.setSort(sort_3);
			}
			
			if(sqlSession.insert(NS+"doorlockCreate_4",vo)!=1){
				throw new Exception("doorlockCreate_4");
			}
			
			Object obj_5 = sqlSession.selectOne(NS+"doorlockCreate_5",vo);
			int sort_5 = 0;
			if(obj_5==null){
				sort_5 = 0;		
			}else{
				sort_5 = (Integer) obj_5;
				vo.setSort(sort_5);
			}
			
			vo = this.recursiveQuery(vo);
			
			if(sqlSession.insert(NS+"doorlockCreate_7",vo)!=1){
				throw new Exception("doorlockCreate_7");
			}
			if(sqlSession.insert(NS+"doorlockCreate_8",vo)!=1){
				throw new Exception("doorlockCreate_8");
			}
			
			WebDoorlockVO vo_8 = (WebDoorlockVO) sqlSession.selectOne(NS+"doorlockCreate_9",vo);
			if(vo_8==null){
				throw new Exception("doorlockCreate_9");	
			}else{
				vo.setState_name(vo_8.getState_name());
				vo.setCrt_dt(vo_8.getCrt_dt());	
			}
			
			result=JSONObject.fromObject(vo);
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_SUCCESS);
			platformTransactionManager.commit(status);

		}catch(Exception e){
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG,ContextKey.MSG_DOORLOCK_REGISTER_INSERT_FAIL);
			platformTransactionManager.rollback(status);
			e.printStackTrace();
			return result;
		}
		return result;
	}

  ```

# 산출문서

 - [UI 정의서](https://github.com/yung6699/SmartDoorLock-WebApplication/raw/master/docs/UI%20%EC%84%A4%EA%B3%84%EC%84%9C.pdf)

# 기술 스택

 - 클라이언트
  - [UI Template 이용](https://github.com/puikinsh/gentelella) 
  - 통계.js: [morris.js, sigma.js, canvas.js, curvedLines.js, flot.js, chart.js]
  - UI/UX.css : [font-awsome.css, bootstrap.css]

 - 서버
  - WAS: apache, tomcat7
  - 프레임워크: 스프링 3.x 
  - DB: OracleDB(서비스), MongoDB(그래프)
  - 주요라이브러리 : logback, mybatis, GCM push, jackson
  - OS : Linux ubuntu 14.04 LTS 
         

