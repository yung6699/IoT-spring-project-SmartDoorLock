package com.sl.sub.expirer.task;

import org.springframework.stereotype.Repository;


import net.sf.json.JSONObject;


public interface ExpirerTask {
	public JSONObject execute(JSONObject data);
	public void beforeExecute();
	public void afterExecute(boolean commit);
}
