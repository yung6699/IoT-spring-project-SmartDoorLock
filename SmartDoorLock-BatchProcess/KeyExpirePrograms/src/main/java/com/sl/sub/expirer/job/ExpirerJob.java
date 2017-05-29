package com.sl.sub.expirer.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.sl.sub.expirer.context.ExpirerContext;
import com.sl.sub.expirer.task.ExpirerTask;

import net.sf.json.JSONObject;

@Component
public class ExpirerJob {
	
	private static Logger logger = LoggerFactory.getLogger(ExpirerJob.class);
	//indexList
	private List<String> taskIndex = new ArrayList<String>();
	//Object Map 
	private HashMap<String,ExpirerTask> taskMap = new HashMap<String,ExpirerTask>();
	private long taskTime = 0;

	private long waittingTime = 0;
	private long currentWaittingTime=0;
	private String taskName = "";
	private JSONObject data=new JSONObject();
	
	public ExpirerJob(long waittingTime){
		this.waittingTime = waittingTime;
	}
	public ExpirerJob(){
	}
	public int addTask(String key,ExpirerTask task){
		/**
		 * @param key : task's name
		 * @param task : task.
		 * @return : the number of the list that scheduler's sequence.
		 */
		try{
			taskIndex.add(key);
			taskMap.put(key, task);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return taskIndex.size()-1;
	}
	public ExpirerTask getTask(String key){
		/**
		 * @param key : task's name
		 * @return :get task using key
		*/
		return taskMap.get(key);
	}
	public ExpirerTask getTask(int index){
		/**
		 * @param key : task's name
		 * @return :get task using key
		*/
		if(0<=index&&index<taskIndex.size()){
			return taskMap.get(taskIndex.get(index));
		}else{
			return null;
		}
	}
	public ExpirerTask removeTask(String key){
		if(taskIndex.remove(key)){
			return taskMap.remove(key);		
		}else{
			return null;
		}
	}
	public ExpirerTask removeTask(int index){
		if(0<=index&&index<taskIndex.size()){
			String key = taskIndex.remove(index);
			if(key != null){
				return taskMap.remove(key);		
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	public HashMap<String,ExpirerTask> getTasks(){
		return this.taskMap;
	}
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

}
