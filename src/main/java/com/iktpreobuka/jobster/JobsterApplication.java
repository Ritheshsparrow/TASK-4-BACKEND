package com.iktpreobuka.jobster;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableScheduling
@SpringBootApplication
@EnableWebMvc
public class JobsterApplication {

	public static void main(String[] args) {
		//SpringApplication.run(JobsterApplication.class, args);
		ApplicationContext ctx = SpringApplication.run(JobsterApplication.class, args);
		DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
	
    	
	}
	

	@Bean
    public TomcatServletWebServerFactory tomcatEmbedded() {

		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();

        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 means unlimited
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });

        return tomcat;

    }
	
	/*@Bean
    public ExceptionResolver exceptionResolver() {
        return new ExceptionResolver();
    }*/
	
	@PostConstruct
    public void init(){
      // Setting Spring Boot SetTimeZone
      TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
		
}
