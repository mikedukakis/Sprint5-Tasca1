package imf.blackjack.entity;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class Dealer {
    private Hand hand;

    public Dealer() {
        this.hand = new Hand();
    }

}
