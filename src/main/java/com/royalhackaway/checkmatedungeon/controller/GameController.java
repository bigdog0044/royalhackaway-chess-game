package com.royalhackaway.checkmatedungeon.controller;

import com.royalhackaway.checkmatedungeon.model.GameState;
import com.royalhackaway.checkmatedungeon.model.Move;
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
        GameService.Game game = gameService.createNewGame();
        GameState gameState = new GameState(game.getGameId(), game.getBoard(), game.getCurrentPlayer(), "New game started. White's turn.", game.isGameOver(), game.getWinner());
        return ResponseEntity.ok(gameState);
    }

    @PostMapping("/move/{gameId}")
    public ResponseEntity<GameState> move(@PathVariable String gameId, @RequestBody Move move) {
        GameService.Game game = gameService.processMove(gameId, move);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        String message = "";
        if (game.isGameOver()) {
            message = String.format("%s Wins!", game.getWinner());
        } else {
            message = String.format("%s's turn.", game.getCurrentPlayer());
        }
        GameState gameState = new GameState(game.getGameId(), game.getBoard(), game.getCurrentPlayer(), message, game.isGameOver(), game.getWinner());
        return ResponseEntity.ok(gameState);
    }

    @GetMapping("/game-state/{gameId}")
    public ResponseEntity<GameState> getGameState(@PathVariable String gameId) {
        GameService.Game game = gameService.getGame(gameId);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        String message = "";
        if (game.isGameOver()) {
            message = String.format("%s Wins!", game.getWinner());
        } else {
            message = String.format("%s's turn.", game.getCurrentPlayer());
        }
        GameState gameState = new GameState(game.getGameId(), game.getBoard(), game.getCurrentPlayer(), message, game.isGameOver(), game.getWinner());
        return ResponseEntity.ok(gameState);
    }
}
