package org.siesta;

import org.h2.server.web.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class ApplicationCollector {

    private static final Logger log = LoggerFactory.getLogger(ApplicationCollector.class);

    public static void main(String[] args) {
        SpringApplication.run(ApplicationCollector.class);
    }

    // showing H2 console
    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }

    //TODO handle exception witch threw in multithreading methods into exceptions list
    //TODO Use Aspect and Annotation for using cache

}
