package com.sl.web.graph.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.system.log.setter.SystemLogSetterVO;
import com.sl.web.doorlock.service.WebDoorlockService;
import com.sl.web.doorlock.vo.WebDoorlockVO;
import com.sl.web.graph.service.WebGraphService;
import com.sl.web.graph.vo.WebGraphVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class WebGraphController {
	
	@Autowired
	private WebGraphService service;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value="/{email}/graph",method=RequestMethod.POST)
	@ResponseBody
	public JSONArray getGraph(@PathVariable(value = "email") String email,WebGraphVO vo, Locale locale){
		vo.setEmail(email);
		logger.info(JSONObject.fromObject(vo).toString());
		return service.getGraph(vo);
	}

	@RequestMapping(value="/{email}/graph/service/info",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getServiceInfo(@PathVariable(value = "email") String email,WebGraphVO vo, Locale locale){
		vo.setEmail(email);
		logger.info(JSONObject.fromObject(vo).toString());
		return service.getServiceInfo(vo);
	}
	
	
}
