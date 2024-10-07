package imf.blackjack.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Game entity representing each game")
public class Game {
    private String id;
    private Player player;
    private Dealer dealer;
    private Deck deck;
    private String winner;

    public Game(String id, Player player) {
        this.id = id;
        this.player = player;
        this.dealer = new Dealer();
        this.deck = new Deck();
        this.winner = null;
    }


}
