package imf.blackjack.controller;

import imf.blackjack.entity.Player;
import imf.blackjack.service.PlayerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@RestController
@RequestMapping("/player")
@Tag(name = "Player", description = "Operations related to player management")
public class PlayerController {
    private final PlayerService playerService;

    @Operation(summary = "Create a new player", description = "Creates a new player with the name given.")
    @ApiResponse(responseCode = "201", description = "Player created successfully")
    @ApiResponse(responseCode = "500", description = "Player couldn't be created")
    @PostMapping("/create")
    public Mono<Player> createPlayer(String playerName) {
        return playerService.createPlayer(playerName);
    }

    @Operation(summary = "Update the name of a player", description = "Update the name of a player using the player ID.")
    @ApiResponse(responseCode = "200", description = "Player updated successfully")
    @PutMapping("/{playerId}")
    public Mono<Player> changePlayerName(@PathVariable String playerId, @RequestBody String newName) {
        return playerService.changePlayerName(playerId, newName);
    }

    @Operation(summary = "Get the ranking of players", description = "Get the raking of players by wins.")
    @ApiResponse(responseCode = "200", description = "Ranking rendered successfully")
    @GetMapping("/ranking")
    public Flux<Player> getPlayerRanking() {
        return playerService.getPlayerRanking();
    }
}
