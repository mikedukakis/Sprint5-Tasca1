package imf.blackjack.service;

import imf.blackjack.entity.Game;
import imf.blackjack.entity.Player;
import imf.blackjack.exception.GameNotFoundException;
import imf.blackjack.repository.GameRepository;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Data
@Service
public class GameService {

    private final GameRepository gameRepository;

    public Mono<Game> createNewGame(String playerName) {
        Player player = new Player(new ObjectId().toHexString(), playerName);
        Game game = new Game(/new ObjectId().toHexString(), player);
        return gameRepository.save(game);
    }

    public Mono<Game> getGameDetails(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with ID: " + gameId)));
    }

    public Mono<Void> deleteGame(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with ID: " + gameId)))
                .flatMap(gameRepository::delete);
    }

    public Mono<Game> makeMove(String gameId, String moveType) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with ID: " + gameId)))
                .flatMap(game -> {
                    processMove(game, moveType);
                    return gameRepository.save(game);
                });
    }
}
