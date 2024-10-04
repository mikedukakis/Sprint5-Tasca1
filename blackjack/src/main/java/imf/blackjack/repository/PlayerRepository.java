package imf.blackjack.repository;

import imf.blackjack.entity.Player;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerRepository extends R2dbcRepository<Player, String> {
    Mono<Player> findById(String id);
}
