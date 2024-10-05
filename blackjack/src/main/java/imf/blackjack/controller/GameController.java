package imf.blackjack.controller;

import imf.blackjack.entity.Game;
import imf.blackjack.service.GameService;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Data
@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Game> createNewGame(@RequestBody String playerName) {
        return gameService.createNewGame(playerName);
    }

    @GetMapping("/{id}")
    public Mono<Game> getGameDetails(@PathVariable String id) {
        return gameService.getGameDetails(id);
    }

//    @PostMapping("/{id}/play")
//    public Mono<Game> makeMove(@PathVariable String id, @RequestBody String moveType) {
//        return gameService.makeMove(id, moveType);
//    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteGame(@PathVariable String id) {
        return gameService.deleteGame(id);
    }

}
