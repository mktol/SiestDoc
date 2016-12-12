package org.siesta;

import org.siesta.service.RepoConnector;
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

/*    @Bean
    CommandLineRunner init(RepositoryService repositoryService) {
        return (args) ->{
            RepoConnector repoRepoConnector = new RepoConnector("admin", "admin");
            RepoConnector repoRepoConnector2 = new RepoConnector("admin", "admin");
            repoRepoConnector.setRepoName("repoOne");
            repoRepoConnector.setUrl("http://localhost:8080/rest");
            repoRepoConnector2.setUrl("http://localhost:8082/rest");
            repoRepoConnector2.setRepoName("repoTwo");
            repositoryService.addConnector(repoRepoConnector);
            repositoryService.addConnector(repoRepoConnector2);
        };

    }

    @Bean
    public RepoConnector oneRepoConnector(){
        return new RepoConnector("admin", "admin");
    }*/
}
