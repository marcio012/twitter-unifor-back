package website.marcioheleno.twitterreativo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import website.marcioheleno.twitterreativo.model.Twitter;
import website.marcioheleno.twitterreativo.repository.TwitterRepository;

import javax.validation.Valid;

@Slf4j
@RestController
public class TwitterController {


    @Autowired
    private TwitterRepository twitterRepository;

    @GetMapping("/tweets")
    public Flux<Twitter> findAll() {
        log.debug("findAll Blog");
        return twitterRepository.findAll();
    }

    @PostMapping("/tweets")
    public Mono<Twitter> createTwitter(@Valid @RequestBody Twitter twitter) {
        return twitterRepository.save(twitter);
    }

    @GetMapping("/tweets/{id}")
    public Mono<ResponseEntity<Twitter>> getTwetterById(@PathVariable(value = "id") String twetterId) {
        return twitterRepository.findById(twetterId)
            .map(savedTwitter -> ResponseEntity.ok(savedTwitter))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PutMapping("/tweets/{id}")
    public Mono<ResponseEntity<Void>> deleteTwitter(@PathVariable(value = "id") String twetterId) {
        return twitterRepository.findById(twetterId)
            .flatMap(existeTwitter ->
                twitterRepository.delete(existeTwitter)
                .then(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)))
            );
    }

    @GetMapping(value = "/stream/tweets", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Twitter> streamAllTwets() {
        return twitterRepository.findAll();
    }

}
