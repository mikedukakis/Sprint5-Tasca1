package imf.blackjack.service;

import imf.blackjack.entity.Game;
import imf.blackjack.entity.Player;
import imf.blackjack.repository.GameRepository;
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

public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testCreateNewGame() {
        Player player = new Player("Johnny");
        Game game = new Game(player);
        when(playerRepository.findById(player.getId())).thenReturn(Mono.just(player));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(game));

        Mono<Game> result = gameService.createNewGame(player.getId());

        StepVerifier.create(result)
                .expectNextMatches(savedGame -> savedGame.getPlayer().getName().equals("Johnny"))
                .verifyComplete();
    }
}
