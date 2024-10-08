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
        getScore();
    }

    public int getScore() {
        short score = 0;
        short aces = 0;

        for (Card card : cards) {
            switch (card.getValue()) {
                case TWO: score += (short)2; break;
                case THREE: score += (short)3; break;
                case FOUR: score += (short)4; break;
                case FIVE: score += (short)5; break;
                case SIX: score += (short)6; break;
                case SEVEN: score += (short)7; break;
                case EIGHT: score += (short)8; break;
                case NINE: score += (short)9; break;
                case TEN:
                case JACK:
                case QUEEN:
                case KING: score += (short)10; break;
                case ACE: aces++; score += (short)11; break;
            }
        }

        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }

        return score;
    }

}
