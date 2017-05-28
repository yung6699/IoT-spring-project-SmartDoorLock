package com.sl.sub.expirer.scheduler;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sl.sub.expirer.context.ExpirerContext;
import com.sl.sub.expirer.job.ExpirerJob;


@Component
public class ExpirerSchedulerService  extends Thread{
	public String text = "test";
	private boolean clock=true;
	
	
	private static Logger logger = LoggerFactory.getLogger(ExpirerSchedulerService.class);
	private ExpirerScheduler scheduler;
	private HashMap<String,ExpirerJob> jobMap;
	public ExpirerSchedulerService(){
		this.text="wow!";
	}
	public void setScheduler(ExpirerScheduler scheduler){
		this.scheduler=scheduler;
		jobMap = this.scheduler.getJobs();
	}
	
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
}