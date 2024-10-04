package imf.blackjack.entity;

import lombok.Data;

@Data
public class Card {
    private final Suit suit;
    private final Value value;

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}
