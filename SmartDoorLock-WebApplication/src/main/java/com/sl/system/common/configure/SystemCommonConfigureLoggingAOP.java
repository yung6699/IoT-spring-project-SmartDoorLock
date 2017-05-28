package com.sl.system.common.configure;

import org.apache.commons.lang.exception.ExceptionUtils; 
import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class SystemCommonConfigureLoggingAOP {
	@Around("execution(* com.sl..*Controller.*(..)) or "
			+ "execution(* com.sl..*Service.*(..)) or "
			+ "execution(* com.sl..*DAO.*(..)) or "
			+ "execution(* com.sl..*Dao.*(..))")
	public Object doLayerLoggingAround(ProceedingJoinPoint pjp)throws Throwable{
		    return this.doLoggingAround(pjp);
	}
	public Object doLoggingAround(ProceedingJoinPoint pjp) throws Throwable{
		Logger logger = LoggerFactory.getLogger(pjp.getTarget().getClass());
		
		String sigName = pjp.getSignature().getName();
		StringBuffer sb = new StringBuffer();
		Object[] obj = pjp.getArgs();
		
		Object retVal = null;
		String objNames = getArgumentNames(obj);
		sb.append(sigName).append("(").append(objNames).append(")");
		
		try{
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			logger.info(sb.toString() + " START ....... Execute Time ....... : " + stopWatch.toString());
			
			retVal = pjp.proceed();
			stopWatch.stop();
			logger.info(sb.toString() + " E N D ....... Execute Time ....... : " + stopWatch.toString());
		}
		catch (Exception e){
			logger.error(sb.toString() + ExceptionUtils.getStackTrace(e));
			throw e;
		}
		return retVal;
	}
	
	private String getArgumentNames(Object[] obj) {
		// TODO Auto-generated method stub
		String objNames = "";
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null) {
				if (i + 1 == obj.length) {
					objNames = objNames + obj[i].getClass().getSimpleName();
				} else {
					objNames = objNames + obj[i].getClass().getSimpleName() + ", ";
				}
			}
		}
		return objNames;
	}
}
