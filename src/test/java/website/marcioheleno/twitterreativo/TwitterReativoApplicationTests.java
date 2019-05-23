package website.marcioheleno.twitterreativo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import website.marcioheleno.twitterreativo.model.Twitter;
import website.marcioheleno.twitterreativo.repository.TwitterRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TwitterReativoApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    TwitterRepository twitterRepository;



    @Test
    public void contextLoads() {

        Twitter twitter = new Twitter();
        twitter.setText("Primeiro Twitter.");

        webTestClient.post().uri("/tweets")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(twitter), Twitter.class)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.text").isEqualTo("Primeiro Twitter.");
    }

}
