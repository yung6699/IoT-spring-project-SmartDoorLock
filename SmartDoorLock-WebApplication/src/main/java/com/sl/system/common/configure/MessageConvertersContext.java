package com.sl.system.common.configure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class MessageConvertersContext extends WebMvcConfigurerAdapter{

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		converters.add(stringConverter());
		converters.add(jacksonConverter());
		super.configureMessageConverters(converters);
	}

	@Bean
	public StringHttpMessageConverter stringConverter(){
		StringHttpMessageConverter converter = new StringHttpMessageConverter();
		converter.setWriteAcceptCharset(false);
		List<MediaType> mediaType=new ArrayList<MediaType>();
		mediaType.add(MediaType.TEXT_PLAIN);
		converter.setSupportedMediaTypes(mediaType);
		converter.setWriteAcceptCharset(false);
		return converter;
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter jacksonConverter(){
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		List<MediaType> mediaType = new ArrayList<MediaType>();
		mediaType.add(MediaType.APPLICATION_JSON);
		converter.setSupportedMediaTypes(mediaType);
		return converter;
	}
	
	
	
}
