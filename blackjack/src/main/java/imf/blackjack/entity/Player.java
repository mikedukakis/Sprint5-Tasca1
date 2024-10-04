package imf.blackjack.entity;

import lombok.Data;

@Data
public class Player {
    private String id;
    private String name;
    private short wins;
    private short losses;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.wins = (short)0;
        this.losses = (short)0;
    }

    @Override
    public String toString() {
        return "Player " + this.name + " with ID " + this.id;
    }
}
