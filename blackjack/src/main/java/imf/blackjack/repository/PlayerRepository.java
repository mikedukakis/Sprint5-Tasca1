package imf.blackjack.repository;


import imf.blackjack.entity.Player;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Repository
public interface PlayerRepository extends R2dbcRepository<Player, String> {
    Mono<Player> findById(String id);
    Mono<Player> save(Player player);

    Flux<Player> findByOrderByWinsDesc();

}
