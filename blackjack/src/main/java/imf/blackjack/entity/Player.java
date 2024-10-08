package imf.blackjack.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Schema(description = "Player entity representing game participants")
@Table
public class Player {
    @Id
    private String id;

    @Column("name")
    private String name;
    @Column("wins")
    private int wins;
    @Column("losses")
    private int losses;

    public Player() {}

    public Player(String name) {
        this.name = name;
        this.wins = 0;
        this.losses = 0;
    }

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.wins = 0;
        this.losses = 0;
    }

    @Override
    public String toString() {
        return "Player " + this.name + " with ID " + this.id;
    }
}
