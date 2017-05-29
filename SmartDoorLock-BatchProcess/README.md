# 프로젝트 Git Repositories
 - [시스템(전체)](https://github.com/yung6699/SmartDoorLock) : (시스템 소개를 위해 존재하는 깃 repo) 
 - [웹, 서비스](../SmartDoorLock-WebApplication) : 관리자 페이지 이며 앱보다 더 넓은 관리기능 제공
 - [앱](../SmartDoorLock-HybridApplication) : 실제 도어락을 여는 주체이며 웹보다 적은 관리기능을 제공한다.
 - [하드웨어](../SmartDoorLock-Arduino) : 블루투스 모듈과 Wi-Fi모듈을 이용한 하드웨어 도어락
 - [배치 프로세스](../SmartDoorLock-BatchProcess) : 서비스에 발생된 로그를 분석하여 서비스 이용자에게 그래프를 제공한다.

# 배치 프로세스 소개

 - 권한의 만료 및 시간대별 이용가능 여부를 판단해준다.
 - 서비스DB에 저장된 로그들을 분석하여 그래프 DB에 저장한다.
 - 잡은 총 세개 있으며 '권한 유효성 판단', '권한 만료', '그래프 데이터 생성'
 - 잡은 각각의 Interval time이 돌고있다.

# 동작

![working of batch process](https://github.com/yung6699/SmartDoorLock/blob/master/SmartDoorLock-BatchProcess/docs/batch_working.bmp)


# 주요 코드

1. 태스크, 잡, 스케줄러 정의

 ``` java
	//ExpirerMain.java 
	public void defineTask(){
        // ----------------------------생략 -------------------
		
        ExpirerSchedulerService service = (ExpirerSchedulerService) ExpirerContext.context.getBean("expirerSchedulerService");
		
		ExpirerScheduler scheduler = new ExpirerScheduler();

		ExpirerJob CheckAvailableKey = new ExpirerJob(ExpirerContext.TASK_INTERVAL_KEY_VAL_TIME);
		CheckAvailableKey.addTask("Task_Check_KeyValTime", new Task_Check_KeyValTime());
		
		ExpirerJob CheckExpiredKey = new ExpirerJob(ExpirerContext.TASK_INTERVAL_KEY_MST);
		CheckExpiredKey.addTask("Task_CheckExpiredKey", new Task_CheckExpiredKey());
		
		ExpirerJob graphJob = new ExpirerJob(ExpirerContext.TASK_INTERVAL_GRAPH);
		graphJob.addTask("Task_Graph_MakeData", new Task_Graph_MakeData());
		graphJob.addTask("Task_Graph_INIT_USER_USAGE", new Task_Graph_INIT_USER_USAGE());
		graphJob.addTask("Task_Graph_INIT_USER_KEY_HAVE", new Task_Graph_INIT_USER_KEY_HAVE());
		graphJob.addTask("Task_Graph_INIT_DOORLOCK_USAGE", new Task_Graph_INIT_DOORLOCK_USAGE());
		graphJob.addTask("Task_Graph_INIT_DOORLOCK_KEY_HAVE", new Task_Graph_INIT_DOORLOCK_KEY_HAVE());
		graphJob.addTask("Task_Graph_INIT_CAT_COMMON_KEY_MEMBER", new Task_Graph_INIT_CAT_COMMON_KEY_MEMBER());
		graphJob.addTask("Task_Graph_INIT_CAT_COMMON_KEY_USAGE", new Task_Graph_INIT_CAT_COMMON_KEY_USAGE());
		graphJob.addTask("Task_Graph_INIT_CAT_ACCOMODATION_SALE", new Task_Graph_INIT_CAT_ACCOMODATION_SALE());		
		
		scheduler.addJob("CheckAvailableKey", CheckAvailableKey);
		scheduler.addJob("CheckExpiredKey", CheckExpiredKey);
		
		scheduler.addJob("GRAPH_JOB", graphJob);
		service.setScheduler(scheduler);
		service.start();

        // ----------------------------생략 -------------------
    }
 ```

2. 스케줄러 수행 코드

 ``` java
    //ExpirerSchedulerService.java extends Thread
	@Override
	public void run() {
	// TODO Auto-generated method stub
		while(true){
			try{
				while(clock){
					scheduler=new ExpirerScheduler();
					scheduler.addJobs(this.jobMap);
					ExpirerContext.SCHEDULER_START_TIME=System.currentTimeMillis();
					scheduler.start();
					Thread.sleep(ExpirerContext.SCHEDULER_INTERVAL_TIME); //10초마다 한 번씩.
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				clock=true;		
			}
		}
	}	
 ```

3. 잡 수행코드

 ``` java
    //Expirerscheduler.java extends Thread
	@Override
	public synchronized void run() {
		try {
			schedulerTime = System.currentTimeMillis();
			logger.info("scheduler : Start");
			logger.info(jobIndex.size()+"");
			for (String jobName : jobIndex) {
				logger.info("job Start : [" + jobName + "]");
				jobTime = System.currentTimeMillis();
				(jobMap.get(jobName)).start();
				jobTime = System.currentTimeMillis() - jobTime;
				logger.info("job end: [" + jobName + "] time: [" + jobTime + "]");
			}
			schedulerTime = System.currentTimeMillis() - schedulerTime;
			logger.info("scheduler : End Time is : [" + schedulerTime + "]");
			logger.info("\n\n");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 ```

4. 태스크 수행코드

 ``` java
    //ExpirerJob.java 

	public void start(){
		try{
			if(System.currentTimeMillis()>this.currentWaittingTime+this.waittingTime){
				for(String taskName : taskIndex){
					logger.info("task Start : ["+taskName+"] ==============");
					taskTime = System.currentTimeMillis();
					data = (taskMap.get(taskName)).execute(data);
					taskTime = System.currentTimeMillis()-taskTime;
					logger.info("task End : ["+taskName+"] time ["+taskTime+"] ==============");
				}
				this.currentWaittingTime=ExpirerContext.SCHEDULER_START_TIME;
			}else{
				logger.info("This job is not triggered about the time [watting : "+(System.currentTimeMillis()-(this.currentWaittingTime+this.waittingTime))+" / millis]");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
 ```


# 그래프DB-ERD

![graph DB ERD](https://github.com/yung6699/SmartDoorLock/blob/master/SmartDoorLock-BatchProcess/docs/graphDB.png)

# 기술 스택
 - jdk1.8
 - mongo-jdbc
 - oracle-jdbc
 - Google GCM Push
 - 멀티스레딩

# 산출 문서
 
 - [분석될 로그 상태값 정의서](https://github.com/yung6699/SmartDoorLock/blob/master/SmartDoorLock-BatchProcess/docs/LOG_STATE%20Definition.xlsx) 

