package imf.blackjack.controller;

import imf.blackjack.entity.Player;
import imf.blackjack.service.PlayerService;

import lombok.Data;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.management.monitor.MonitorNotification;

@Data
@RestController
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService playerService;

    @PutMapping("/{playerId}")
    public Mono<Player> changePlayerName(@PathVariable String playerId, @RequestBody String newName) {
        return playerService.changePlayerName(playerId, newName);
    }

    @GetMapping("/ranking")
    public Flux<Player> getPlayerRanking() {
        return playerService.getPlayerRanking();
    }
}
