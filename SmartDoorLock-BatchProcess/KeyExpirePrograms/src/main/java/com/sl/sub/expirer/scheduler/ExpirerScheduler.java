package com.sl.sub.expirer.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sl.sub.expirer.job.ExpirerJob;

@Component
public class ExpirerScheduler extends Thread {
	// indexList
	static ArrayList<String> jobIndex = new ArrayList<String>();
	// Object Map
	private HashMap<String, ExpirerJob> jobMap = new HashMap<String, ExpirerJob>();
	private long jobTime = 0;
	private long schedulerTime = 0;
	private String jobName = "";

	private static Logger logger = LoggerFactory.getLogger(ExpirerScheduler.class);

	public ExpirerScheduler() {

	}

	public int addJob(String key, ExpirerJob job) {
		/**
		 * @param key
		 *            : job's name
		 * @param job
		 *            : job.
		 * @return : the number of the list that scheduler's sequence.
		 */
		jobIndex.add(key);
		jobMap.put(key, job);
		return jobIndex.size() - 1;
	}

	public ExpirerJob getJob(String key) {
		/**
		 * @param key
		 *            : job's name
		 * @return :get Job using key
		 */
		return jobMap.get(key);
	}

	public ExpirerJob getJob(int index) {
		/**
		 * @param key
		 *            : job's name
		 * @return :get Job using key
		 */
		if (0 <= index && index < jobIndex.size()) {
			return jobMap.get(jobIndex.get(index));
		} else {
			return null;
		}
	}

	public ExpirerJob removeJob(String key) {
		if (jobIndex.remove(key)) {
			return jobMap.remove(key);
		} else {
			return null;
		}
	}

	public ExpirerJob removeJob(int index) {
		if (0 <= index && index < jobIndex.size()) {
			String key = jobIndex.remove(index);
			if (key != null) {
				return jobMap.remove(key);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public HashMap<String, ExpirerJob> getJobs() {
		return this.jobMap;
	}

	@Override
	public synchronized void run() {
		try {
			schedulerTime = System.currentTimeMillis();
			logger.info("scheduler : Start");
//			for (String jobName : jobMap.keySet()) {
//				logger.info("job Start : [" + jobName + "]");
//				jobTime = System.currentTimeMillis();
//				(jobMap.get(jobName)).start();
//				jobTime = System.currentTimeMillis() - jobTime;
//				logger.info("job end: [" + jobName + "] time: [" + jobTime + "]");
//			}
			logger.info(jobIndex.size()+"");
			for (String jobName : jobIndex) {
				logger.info("job Start : [" + jobName + "]");
				jobTime = System.currentTimeMillis();
				(jobMap.get(jobName)).start();
				jobTime = System.currentTimeMillis() - jobTime;
				logger.info("job end: [" + jobName + "] time: [" + jobTime + "]");
			}
//			for(int i=0; i<jobIndex.size();i++) {
//				logger.info("job Start : [" + jobName + "]");
//				jobTime = System.currentTimeMillis();
//				jobMap.get(jobIndex.get(i)).start();
//				jobTime = System.currentTimeMillis() - jobTime;
//				logger.info("job end: [" + jobName + "] time: [" + jobTime + "]");
//			}
			schedulerTime = System.currentTimeMillis() - schedulerTime;
			logger.info("scheduler : End Time is : [" + schedulerTime + "]");
			logger.info("\n\n");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean addJobs(HashMap<String, ExpirerJob> jobMap) {
		// TODO Auto-generated method stub
		this.jobMap = jobMap;
		return true;
	}

}
