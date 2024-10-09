package imf.blackjack.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "Game entity representing each game")
public class Game {
    private String id;
    private String playerId;
    private Player player;
    private List<Card> playerHand;
    private Dealer dealer;
    private Deck deck;
    private String winner;
    private boolean isGameOver;

    public Game() {}

    public Game(Player player) {
        this.player = player;
        this.playerId = player.getId();
        this.playerHand = new ArrayList<>();
        this.dealer = new Dealer();
        this.deck = new Deck();
        this.isGameOver = false;
    }

    public Game(String playerId, String playerName) {
        this.player = new Player(playerId, playerName);
        this.playerHand = new ArrayList<>();
        this.dealer = new Dealer();
        this.deck = new Deck();
        this.isGameOver = false;
    }

    public void endGame(String winner) {
        this.isGameOver = true;
        this.winner = winner;
    }

}
