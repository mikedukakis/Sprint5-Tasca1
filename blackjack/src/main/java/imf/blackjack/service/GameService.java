package imf.blackjack.service;

import imf.blackjack.entity.Card;
import imf.blackjack.entity.Deck;
import imf.blackjack.entity.Game;
import imf.blackjack.exception.GameNotFoundException;
import imf.blackjack.exception.PlayerNotFoundException;
import imf.blackjack.repository.GameRepository;
import imf.blackjack.repository.PlayerRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

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

        if (game.getPlayerHand().isEmpty()) {
            game.getPlayerHand().add(deck.drawCard());
            game.getPlayerHand().add(deck.drawCard());
        }

        game.setPlayerScore(calculateScore(game.getPlayerHand()));

        game.getDealer().getHand().addCard(deck.drawCard());
        game.getDealer().getHand().addCard(deck.drawCard());

        game.getDealer().getHand().setScore(calculateScore(game.getDealer().getHand().getCards()));
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
        game.getPlayerHand().add(game.getDeck().drawCard());
        game.setPlayerScore(calculateScore(game.getPlayerHand()));

        if (calculateScore(game.getPlayerHand()) > 21) {
            game.endGame("dealer");
        }
        return gameRepository.save(game);
    }

    @Transactional
    private Mono<Game> dealerPlay(Game game) {
        while (calculateScore(game.getDealer().getHand().getCards()) < 17) {
            game.getDealer().getHand().addCard(game.getDeck().drawCard());
        }

        int dealerScore = calculateScore(game.getDealer().getHand().getCards());
        int playerScore = calculateScore(game.getPlayerHand());

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
                .flatMap(savedGame -> updatePlayerStats(game.getPlayer().getId(), game.getWinner())
                        .thenReturn(savedGame));
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
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found with Id: " + playerId)))
                .then();
    }



    private int calculateScore(List<Card> cards) {
        int score = 0;
        int aces = 0;

        for (Card card : cards) {
            String value = String.valueOf(card.getValue());

            switch (value) {
                case "TWO":
                    score += 2;
                    break;
                case "THREE":
                    score += 3;
                    break;
                case "FOUR":
                    score += 4;
                    break;
                case "FIVE":
                    score += 5;
                    break;
                case "SIX":
                    score += 6;
                    break;
                case "SEVEN":
                    score += 7;
                    break;
                case "EIGHT":
                    score += 8;
                    break;
                case "NINE":
                    score += 9;
                    break;
                case "TEN":
                case "JACK":
                case "QUEEN":
                case "KING":
                    score += 10;
                    break;
                case "ACE":
                    aces += 1;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown card value: " + value);
            }
        }for (int i = 0; i < aces; i++) {
            // If adding 11 keeps us under or at 21, count ace as 11, otherwise as 1
            if (score + 11 <= 21) {
                score += 11;
            } else {
                score += 1;
            }
        }

        return score;
    }

    public Mono<Void> deleteGame(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with ID: " + gameId)))
                .flatMap(gameRepository::delete);
    }

}
