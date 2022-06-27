package com.jdc.assignment.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.support.GenericXmlApplicationContext;

@WebListener
public class SpringContextManager implements ServletContextListener{

	public static final String SPRING_CONTEXT = "spring.context";
	
	private GenericXmlApplicationContext springContext;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("IOC container is Initialized.");
		springContext = new GenericXmlApplicationContext("classpath:/application.xml");
		sce.getServletContext().setAttribute(SPRING_CONTEXT, springContext);
	}
	
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("IoC_Container is Destroyed.");
		if(null != springContext) {
			springContext.close();
		}
	}
	
}
