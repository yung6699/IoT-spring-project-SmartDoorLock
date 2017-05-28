package com.sl.hw.key.check.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.hw.key.check.service.HwKeyCheckService;
import com.sl.hw.key.check.vo.HwKeyCheckVO;

@Controller
public class HwKeyCheckController {
	
	private static final Logger logger = LoggerFactory.getLogger(HwKeyCheckController.class);
	
    @Autowired
	HwKeyCheckService service;
	
	@RequestMapping(value="/hw/key/check", method = RequestMethod.POST)
	@ResponseBody
	public String selectOneCheck(HwKeyCheckVO vo, Locale locale){
		logger.info("/hw/key/check.", locale);
		return service.selectOneCheck(vo);
	}

}
