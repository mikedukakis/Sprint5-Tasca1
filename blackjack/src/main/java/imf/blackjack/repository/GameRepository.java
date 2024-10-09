package imf.blackjack.repository;

import com.mongodb.lang.NonNull;
import imf.blackjack.entity.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface GameRepository extends ReactiveMongoRepository<Game, String> {
    @Override
    Mono<Game> findById(@NonNull String id);
}
