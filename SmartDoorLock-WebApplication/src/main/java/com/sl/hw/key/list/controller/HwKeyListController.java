package com.sl.hw.key.list.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.hw.key.list.service.HwKeyListService;

import net.sf.json.JSONArray;

@Controller
public class HwKeyListController {
	
	private static final Logger logger = LoggerFactory.getLogger(HwKeyListController.class);
	
	@Autowired
	HwKeyListService service;
	
	@RequestMapping(value="/hw/key/list", method= RequestMethod.POST)
	@ResponseBody
	public JSONArray selectKeyList(@RequestParam(value="serial_no")String serial_no, Locale locale){
		return service.selectKeyList(serial_no);
	}
}
