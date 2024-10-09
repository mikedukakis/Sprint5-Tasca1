package imf.blackjack.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.ArrayList;

@Data
@Document
public class Hand {
    private List<Card> cards;
    private int score;

    public Hand() {
        this.cards = new ArrayList<>();
        this.score = 0;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

}
