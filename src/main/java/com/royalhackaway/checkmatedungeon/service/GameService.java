package com.royalhackaway.checkmatedungeon.service;

import com.royalhackaway.checkmatedungeon.model.*;
import com.royalhackaway.checkmatedungeon.model.pieces.PieceType;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

@Service
public class GameService {

    // Using a simple in-memory map to store games for the MVP
    private final Map<UUID, Game> games = new ConcurrentHashMap<>();

    public GameState createNewGame() {
        UUID gameId = UUID.randomUUID();
        Game game = new Game(gameId.toString());
        game.start();
        games.put(gameId, game);
        return getCompleteGameState(gameId.toString());
    }

    public Game getGame(String gameId) {
        return games.get(UUID.fromString(gameId));
    }

    public GameState getCompleteGameState(String gameId) {
        Game game = getGame(gameId);
        if (game == null) {
            return null;
        }
        // Construct GameState with all relevant information
        return new GameState(
                game.getGameId(),
                game.getBoard(),
                game.getCurrentPlayer(),
                game.getMessage(),
                game.isGameOver(),
                game.getWinner(),
                game.getCurrentFlowState(),
                game.getAvailableRewards(),
                game.getPowerUpToSacrifice(),
                game.getWhitePlayer(),
                game.getStage()
        );
    }

    public GameState processMove(String gameId, Move move) {
        Game game = getGame(gameId);
        if (game != null) {
            Piece.PieceColor prevWinner = game.getWinner();
            boolean wasGameOver = game.isGameOver();
            game.makeMove(move.getFrom(), move.getTo());
            // Check for win/loss and handle stage progression
            if (!wasGameOver && game.isGameOver()) {
                if (game.getWinner() == Piece.PieceColor.WHITE) {
                    game.advanceStage();
                } else if (game.getWinner() == Piece.PieceColor.BLACK) {
                    game.resetStage();
                }
            }
        }
        return getCompleteGameState(gameId);
    }

    public GameState chooseReward(String gameId, int choiceIndex) {
        Game game = getGame(gameId);
        if (game != null) {
            game.chooseReward(choiceIndex);
        }
        return getCompleteGameState(gameId);
    }

    public GameState sacrificePowerUp(String gameId, PieceType pieceType, int powerUpIndex) {
        Game game = getGame(gameId);
        if (game != null) {
            game.sacrificePowerUp(pieceType, powerUpIndex);
        }
        return getCompleteGameState(gameId);
    }

    public GameState applyRangeImprovement(String gameId, Position piecePosition) {
        Game game = getGame(gameId);
        if (game != null) {
            game.applyRangeImprovement(piecePosition);
        }
        return getCompleteGameState(gameId);
    }
}
