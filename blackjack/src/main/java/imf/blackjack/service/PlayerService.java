package imf.blackjack.service;

import imf.blackjack.entity.Player;
import imf.blackjack.exception.PlayerNotFoundException;
import imf.blackjack.repository.PlayerRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Data
@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Mono<Player> createPlayer(String playerName) {
        Player newPlayer = new Player(playerName);
        return playerRepository.save(newPlayer);
    }

    public Mono<Player> changePlayerName(String playerId, String newName) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found with Id " + playerId)))
                .flatMap(player -> {
                    player.setName(newName);
                    return playerRepository.save(player);
                });
    }

    public Flux<Player> getPlayerRanking() {
        return playerRepository.findPlayersOrderedByWins();
    }
}
