package website.marcioheleno.twitterreativo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import website.marcioheleno.twitterreativo.model.Twitter;

@Repository
public interface TwitterRepository extends ReactiveMongoRepository<Twitter, String> {

}
