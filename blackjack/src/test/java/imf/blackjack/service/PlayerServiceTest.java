package imf.blackjack.service;

import imf.blackjack.entity.Player;
import imf.blackjack.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testCreatePlayer() {
        Player player = new Player("001","Johnny");

        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(player));
        Mono<Player> result = playerService.createPlayer("Johnny");
        StepVerifier.create(result)
                .expectNextMatches(savedPlayer -> savedPlayer.getName().equals("Johnny"))
                .verifyComplete();
    }
}
