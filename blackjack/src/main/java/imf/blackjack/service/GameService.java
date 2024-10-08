package imf.blackjack.service;

import imf.blackjack.entity.Deck;
import imf.blackjack.entity.Game;
import imf.blackjack.entity.Hand;
import imf.blackjack.exception.GameNotFoundException;
import imf.blackjack.exception.PlayerNotFoundException;
import imf.blackjack.repository.GameRepository;
import imf.blackjack.repository.PlayerRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Data
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public Mono<Game> createNewGame(String playerId) {
        return playerRepository.findById(playerId)
                .flatMap(player -> {
                            Game game = new Game(player.getId(), player.getName());
                            dealInitialCards(game);
                            return gameRepository.save(game);
                        })
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found with Id: " + playerId)));
    }

    public Mono<Game> getGameDetails(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with ID: " + gameId)));
    }

    private void dealInitialCards(Game game) {
        Deck deck = game.getDeck();

        Hand playerHand = game.getPlayer().getHand();
        if (playerHand == null) {
            playerHand = new Hand();
            game.getPlayer().setHand(playerHand);
        }

        playerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        game.getDealer().getHand().addCard(deck.drawCard());
        game.getDealer().getHand().addCard(deck.drawCard());
    }

    public Mono<Game> makeMove(String gameId, String moveType) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with ID: " + gameId)))
                .flatMap(game -> {
                    if (moveType.equalsIgnoreCase("hit")) {
                        return playerHit(game);
                    } else if (moveType.equalsIgnoreCase("stand")) {
                        return dealerPlay(game);
                    }
                    return Mono.just(game);
                });
    }


    private Mono<Game> playerHit(Game game) {
        game.getPlayer().getHand().addCard(game.getDeck().drawCard());
        if (calculateScore(game.getPlayer().getHand()) > 21) {
            game.endGame("dealer");
        }
        return gameRepository.save(game);
    }

    private Mono<Game> dealerPlay(Game game) {
        while (calculateScore(game.getDealer().getHand()) < 17) {
            game.getDealer().getHand().addCard(game.getDeck().drawCard());
        }

        int dealerScore = calculateScore(game.getDealer().getHand());
        int playerScore = calculateScore(game.getPlayer().getHand());

        String winner;
        if (dealerScore > 21 || playerScore > dealerScore) {
            winner = "player";
            game.endGame("player");
        } else if (playerScore == dealerScore) {
            winner = "tie";
            game.endGame("tie");
        } else {
            winner = "dealer";
            game.endGame("dealer");
        }

        return gameRepository.save(game)
                .flatMap(savedGame -> {
                    return updatePlayerStats(game.getPlayer().getId(), winner)
                            .thenReturn(savedGame);
                });
    }

    private Mono<Void> updatePlayerStats(String playerId, String winner) {
        return playerRepository.findById(playerId)
                .flatMap(player -> {
                    if ("player".equals(winner)) {
                        player.setWins(player.getWins() + 1);
                    } else if ("dealer".equals(winner)) {
                        player.setLosses(player.getLosses() + 1);
                    }
                    return playerRepository.save(player);
                })
                .then();
    }


    private int calculateScore(Hand hand) {
        return hand.getScore();
    }

    public Mono<Void> deleteGame(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with ID: " + gameId)))
                .flatMap(gameRepository::delete);
    }

}
