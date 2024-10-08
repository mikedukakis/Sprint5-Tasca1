package imf.blackjack.repository;

import imf.blackjack.entity.Player;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Repository
public interface PlayerRepository extends R2dbcRepository<Player, String> {
    Mono<Player> findById(@RequestParam String id);
    Mono<Player> save(Player player);
    @Query("SELECT * FROM player ORDER BY wins DESC")
    Flux<Player> findPlayersOrderedByWins();
}
