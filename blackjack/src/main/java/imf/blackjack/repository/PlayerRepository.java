package imf.blackjack.repository;

import imf.blackjack.entity.Player;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerRepository extends ReactiveCrudRepository<Player, Long> {
    Mono<Player> findByName(String name);
}
