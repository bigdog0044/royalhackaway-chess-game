package com.royalhackaway.checkmatedungeon.controller;

import com.royalhackaway.checkmatedungeon.model.Game;
import com.royalhackaway.checkmatedungeon.model.GameState;
import com.royalhackaway.checkmatedungeon.model.Move;
import com.royalhackaway.checkmatedungeon.model.PowerUpIndexRequest;
import com.royalhackaway.checkmatedungeon.model.Position;
import com.royalhackaway.checkmatedungeon.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/new-game")
    public ResponseEntity<GameState> newGame() {
        GameState gameState = gameService.createNewGame();
        return ResponseEntity.ok(gameState);
    }

    @PostMapping("/move/{gameId}")
    public ResponseEntity<GameState> move(@PathVariable String gameId, @RequestBody Move move) {
        GameState gameState = gameService.processMove(gameId, move);
        if (gameState == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gameState);
    }

    @GetMapping("/game-state/{gameId}")
    public ResponseEntity<GameState> getGameState(@PathVariable String gameId) {
        GameState gameState = gameService.getCompleteGameState(gameId);
        if (gameState == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gameState);
    }

    @PostMapping("/choose-reward/{gameId}")
    public ResponseEntity<GameState> chooseReward(@PathVariable String gameId, @RequestBody int choiceIndex) {
        GameState gameState = gameService.chooseReward(gameId, choiceIndex);
        if (gameState == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gameState);
    }

    @PostMapping("/sacrifice-power-up/{gameId}")
    public ResponseEntity<GameState> sacrificePowerUp(@PathVariable String gameId, @RequestBody PowerUpIndexRequest request) {
        GameState gameState = gameService.sacrificePowerUp(gameId, request.getPieceType(), request.getPowerUpIndex());
        if (gameState == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gameState);
    }

    @PostMapping("/apply-range-improvement/{gameId}")
    public ResponseEntity<GameState> applyRangeImprovement(@PathVariable String gameId, @RequestBody Position position) {
        GameState gameState = gameService.applyRangeImprovement(gameId, position);
        if (gameState == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gameState);
    }
}
