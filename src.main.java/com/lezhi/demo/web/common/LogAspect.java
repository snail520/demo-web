package com.lezhi.demo.web.common;


import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Aspect
// 该注解标示该类为切面类
public class LogAspect {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ObjectMapper jsonMapper;
	
	@Autowired
	MessageModel messageModel;
	
	@Around("within(com.lezhi.demo.web.action.*Action)")
	public Object doExecute(ProceedingJoinPoint  joinPoint) throws Throwable {
        String menuId = request.getParameter("menuId");
		messageModel.setStartTime(new Date());
		String queryString = jsonMapper.writeValueAsString(request.getParameterMap());
		messageModel.setRequestUrl(
				request.getRequestURL().toString() +
				(StringUtils.isNotBlank(queryString) ? "|" + request.getMethod() + "|" + queryString : ""));
		ModelAndView modeAndView = null;
		try{
			//调用Controller方法
			Object retval = joinPoint.proceed();
			if(retval!=null){
				modeAndView = jsonMapper.convertValue(retval, ModelAndView.class);
				messageModel.setModelAndView(modeAndView);
			}
		}catch(Exception e){
			messageModel.setSuccess(501);
			messageModel.setMsg("系统错误");
			messageModel.setExceptionLog(e, 501);
		}
		messageModel.setEndTime(new Date());
		Signature signature = joinPoint.getSignature();  
		MethodSignature methodSignature = (MethodSignature) signature;  
		Method method = methodSignature.getMethod(); 
		
		Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
		MDC.put("ActionMethod", method.getDeclaringClass().getSimpleName()+"/"+methodSignature.getMethod().getName());
		logger.info(messageModel.toString());

		return messageModel.getModelAndView();
	}
}
