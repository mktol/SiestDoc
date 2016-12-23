package com.repo.one;

import com.repo.one.service.AutoRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
@ConfigurationProperties(value = "application.properties")
public class Application {

    private final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        System.out.println("Repository is started");
        printCat();
    }

    private static void printCat(){
        try {
            File file = new ClassPathResource("banner.txt").getFile();
            String s = new String(FileCopyUtils.copyToByteArray(file));
            System.out.println(s);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public CommandLineRunner commandLineRunner(AutoRegistrationService autoRegistrationService) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            boolean isRegistered = autoRegistrationService.registrationIntoCollector();
            if(isRegistered){
               logger.info("Service successfully registered");
            }else{
                logger.error("Service not registered, wrong repository name, collector url, or problem with connection to collector");
            }

        };
    }


}