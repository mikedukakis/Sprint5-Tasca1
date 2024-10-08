package imf.blackjack.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Player entity representing game participants")
public class Player {
    private static short nextId = 0;
    private short id;
    private String name;
    private Hand hand;
    private short wins;
    private short losses;

    public Player(String name) {
        this.id = ++nextId;
        nextId = this.id;
        this.name = name;
        this.wins = (short)0;
        this.losses = (short)0;
    }

    @Override
    public String toString() {
        return "Player " + this.name + " with ID " + this.id;
    }
}
