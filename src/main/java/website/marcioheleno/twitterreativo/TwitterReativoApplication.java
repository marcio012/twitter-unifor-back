package website.marcioheleno.twitterreativo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableMongoAuditing
@EnableReactiveMongoRepositories
@SpringBootApplication
public class TwitterReativoApplication {

    public static void main(String[] args) {

        SpringApplication.run(TwitterReativoApplication.class, args);
        System.out.println("Olá Mundo com webFlux....");

    }

}
