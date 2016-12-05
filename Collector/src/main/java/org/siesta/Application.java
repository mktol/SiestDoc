package org.siesta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableJpaRepositories
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

//    @Bean
//    public CommandLineRunner demo(UserRepository repository) {
//        return (args) -> {
//            // save a couple of customers
//
//            log.info("");
//        };
//    }

/*    @Bean
    public OneRepoConnector oneRepoConnector(){
        return new OneRepoConnector("admin", "admin");
    }*/
}
