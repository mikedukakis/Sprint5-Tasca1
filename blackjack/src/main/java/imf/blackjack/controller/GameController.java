package imf.blackjack.controller;

import imf.blackjack.entity.Game;
import imf.blackjack.service.GameService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Data
@RestController
@RequestMapping("/game")
@Tag(name = "Game", description = "Operations related to game management")
public class GameController {
    private final GameService gameService;

    @Operation(summary = "Create a new game", description = "Creates a new game with the player given.")
    @ApiResponse(responseCode = "201", description = "Game created successfully")
    @PostMapping("/new")
    public Mono<Game> createNewGame(@RequestBody String playerName) {
        return gameService.createNewGame(playerName);
    }

    @Operation(summary = "Get details of game", description = "Get the details of the game using the game Id")
    @ApiResponse(responseCode = "200", description = "Details provided successfully")
    @GetMapping("/{id}")
    public Mono<Game> getGameDetails(@PathVariable String id) {
        return gameService.getGameDetails(id);
    }

//    @PostMapping("/{id}/play")
//    public Mono<Game> makeMove(@PathVariable String id, @RequestBody String moveType) {
//        return gameService.makeMove(id, moveType);
//    }

    @Operation(summary = "Delete an existing game", description = "Delete a game using the game Id")
    @ApiResponse(responseCode = "204", description = "No content if the game has been deleted correctly")
    @DeleteMapping("/{id}/delete")
    public Mono<Void> deleteGame(@PathVariable String id) {
        return gameService.deleteGame(id);
    }

}
