package org.siesta;

import org.siesta.service.OneRepoConnector;
import org.siesta.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


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

    @Bean
    CommandLineRunner init(RepositoryService repositoryService) {
        return (args) ->{
            OneRepoConnector repoOneConnector = new OneRepoConnector("admin", "admin");
            OneRepoConnector repoOneConnector2 = new OneRepoConnector("admin", "admin");
            repoOneConnector.setRepoName("repoOne");
            repoOneConnector.setUrl("http://localhost:8080/rest");
            repoOneConnector2.setUrl("http://localhost:8082/rest");
            repoOneConnector2.setRepoName("repoTwo");
            repositoryService.addConnector(repoOneConnector);
            repositoryService.addConnector(repoOneConnector2);
        };

    }

    @Bean
    public OneRepoConnector oneRepoConnector(){
        return new OneRepoConnector("admin", "admin");
    }
}
