package com.sl.sub.expirer.main;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.sl.sub.expirer.common.ContextKey;
import com.sl.sub.expirer.config.ExpirerConfig;
import com.sl.sub.expirer.context.ExpirerContext;
import com.sl.sub.expirer.job.ExpirerJob;
import com.sl.sub.expirer.scheduler.ExpirerScheduler;
import com.sl.sub.expirer.scheduler.ExpirerSchedulerService;
import com.sl.sub.expirer.task.Task_CheckExpiredKey;
import com.sl.sub.expirer.task.Task_Check_KeyValTime;
import com.sl.sub.expirer.task.mongo.Task_Graph_INIT_CAT_ACCOMODATION_SALE;
import com.sl.sub.expirer.task.mongo.Task_Graph_INIT_CAT_COMMON_KEY_MEMBER;
import com.sl.sub.expirer.task.mongo.Task_Graph_INIT_CAT_COMMON_KEY_USAGE;
import com.sl.sub.expirer.task.mongo.Task_Graph_INIT_DOORLOCK_KEY_HAVE;
import com.sl.sub.expirer.task.mongo.Task_Graph_INIT_DOORLOCK_USAGE;
import com.sl.sub.expirer.task.mongo.Task_Graph_INIT_USER_KEY_HAVE;
import com.sl.sub.expirer.task.mongo.Task_Graph_INIT_USER_USAGE;
import com.sl.sub.expirer.task.mongo.Task_Graph_MakeData;
import com.sl.sub.expirer.task.mongo.Task_Graph_USER_KEY_HAVE;
import com.sl.sub.expirer.task.mongo.Task_Graph_USER_USAGE;

@Component
public class ExpirerMain {

	private static Logger logger = LoggerFactory.getLogger(ExpirerMain.class);

	public ExpirerMain() {
			new ExpirerConfig();
	}
	
	
	public static void main(String args[]) throws Exception {
		// initializeㅓ
		new ExpirerMain();
		
		// start the programm
//		ExpirerContext.context = new ClassPathXmlApplicationContext("file:///" + System.getProperty(ContextKey.PATH_SMART_DOOR_LOCK) + "/config.xml");
		ExpirerContext.context = new ClassPathXmlApplicationContext("file:///" + System.getProperty(ContextKey.PATH_SMART_DOOR_LOCK) + "/config.xml");
		ExpirerContext.context.registerShutdownHook();
		contextInit();
			
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

	}
	public static void contextInit(){
		Properties context= (Properties)ExpirerContext.context.getBean("context");
		ExpirerContext.SCHEDULER_INTERVAL_TIME = Long.parseLong((String) context.get("scheduler.interval.time"));
		ExpirerContext.TASK_INTERVAL_KEY_VAL_TIME = Long.parseLong((String) context.get("task.interval.key.val.time"));
		ExpirerContext.TASK_INTERVAL_KEY_MST = Long.parseLong((String) context.get("task.interval.key.mst"));
		ExpirerContext.TASK_INTERVAL_GRAPH = Long.parseLong((String) context.get("task.interval.graph"));
		ExpirerContext.CAT_ACCOMODATION_USUAL_PRICE = Integer.parseInt((String) context.get("cat.accomodation.usual.price"));
		ExpirerContext.CAT_ACCOMODATION_HOLIDAY_PRICE = Integer.parseInt((String) context.get("cat.accomodation.holiday.price"));
		
		logger.info("\n"
				+ "\nSetting Time"
				+ "\nSCHEDULER_INTERVAL_TIME == "+ExpirerContext.SCHEDULER_INTERVAL_TIME
				+ "\nTASK_INTERVAL_KEY_VAL_TIME == "+ExpirerContext.TASK_INTERVAL_KEY_VAL_TIME
				+ "\nTASK_INTERVAL_KEY_MST == "+ExpirerContext.TASK_INTERVAL_KEY_MST
				+ "\nTASK_INTERVAL_GRAPH == "+ExpirerContext.TASK_INTERVAL_GRAPH
				);
	}

}
