package website.marcioheleno.twitterreativo;

import org.assertj.core.api.Assertions;
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

import java.util.Collections;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TwitterReativoApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    TwitterRepository twitterRepository;

    @Test
    public void testCriandoTwitter() {

        Twitter twitter = new Twitter("1", "Primeiro Twitter.", new Date());

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

    @Test
    public void testPegandoTodosTwittes() {
        webTestClient.get().uri("/tweets")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBodyList(Twitter.class);
    }

    @Test
    public void testPegandoUmTwitter() {

        Twitter twitter = twitterRepository
            .save(new Twitter("Primeiro Twitter."))
            .block();

        webTestClient.get()
            .uri("/tweets/{id}", Collections.singletonMap("id", twitter.getId()))
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .consumeWith(response ->
                Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    public void testAtualizandoTweet() {
        Twitter twitter = twitterRepository
            .save(new Twitter("1", "Primeiro Twitter.", new Date()))
            .block();

        Twitter newTweetData = new Twitter("1", "Twitter Atualizado", new Date());

        webTestClient.put()
            .uri("/tweets/{id}", Collections.singletonMap("id", twitter.getId()))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(Mono.just(newTweetData), Twitter.class)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .jsonPath("$.text").isEqualTo("Twitter Atualizado");
    }

    @Test
    public void testDeleteTweet() {
        Twitter twitter = twitterRepository.save(new Twitter("Primeiro Twitter.")).block();

        webTestClient.delete()
            .uri("/tweets/{id}", Collections.singletonMap("id",  twitter.getId()))
            .exchange()
            .expectStatus().isOk();
    }

}
