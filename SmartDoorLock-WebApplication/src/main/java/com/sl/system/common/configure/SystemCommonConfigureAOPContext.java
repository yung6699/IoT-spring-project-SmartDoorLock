package com.sl.system.common.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class SystemCommonConfigureAOPContext {
	@Bean(name={"loggingAOP"})
	public SystemCommonConfigureLoggingAOP loggingAOP(){
		return new SystemCommonConfigureLoggingAOP();
	}
	
}
