package com.royalhackaway.checkmatedungeon.service;

import com.royalhackaway.checkmatedungeon.model.*;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

@Service
public class GameService {

    // Using a simple in-memory map to store games for the MVP
    private final Map<UUID, Game> games = new ConcurrentHashMap<>();

    public Game createNewGame() {
        UUID gameId = UUID.randomUUID();
        Game game = new Game(gameId.toString());
        game.start();
        games.put(gameId, game);
        return game;
    }

    public Game getGame(String gameId) {
        return games.get(UUID.fromString(gameId));
    }

    public Game processMove(String gameId, Move move) {
        Game game = getGame(gameId);
        if (game != null) {
            game.makeMove(move.getFrom(), move.getTo());
            // In the future, the AI's turn would be processed here.
        }
        return game;
    }

    // Inner class to represent a single game instance
    public static class Game {
        private final String gameId;
        private final Board board;
        private Piece.PieceColor currentPlayer;

        public Game(String gameId) {
            this.gameId = gameId;
            this.board = new Board(8); // Standard 8x8 board for MVP
        }

        public void start() {
            board.setupStandardBoard();
            this.currentPlayer = Piece.PieceColor.WHITE;
        }

        public boolean makeMove(Position from, Position to) {
            Piece piece = board.getPieceAt(from);
            if (piece == null || piece.getColor() != currentPlayer) {
                return false; // Not the player's piece
            }

            if (piece.getValidMoves(board, from).contains(to)) {
                Piece capturedPiece = board.getPieceAt(to); // Store captured piece, if any
                board.movePiece(from, to);

                // Check for Win/Loss Conditions
                checkGameEndConditions();
                if (isGameOver()) {
                    // Handle game over (e.g., reward, next stage)
                    return true;
                }

                // Switch player
                currentPlayer = (currentPlayer == Piece.PieceColor.WHITE) ? Piece.PieceColor.BLACK : Piece.PieceColor.WHITE;
                return true;
            }
            return false;
        }

        private boolean isGameOver = false;
        private Piece.PieceColor winner = null;

        public boolean isGameOver() {
            return isGameOver;
        }

        public Piece.PieceColor getWinner() {
            return winner;
        }

        private void checkGameEndConditions() {
            int whitePieces = 0;
            int blackPieces = 0;

            for (int r = 0; r < board.getBoardSize(); r++) {
                for (int c = 0; c < board.getBoardSize(); c++) {
                    Piece p = board.getPieceAt(new Position(r, c));
                    if (p != null) {
                        if (p.getColor() == Piece.PieceColor.WHITE) {
                            whitePieces++;
                        } else {
                            blackPieces++;
                        }
                    }
                }
            }

            if (blackPieces == 0) {
                isGameOver = true;
                winner = Piece.PieceColor.WHITE;
                // Placeholder for rewards: New random piece + random powerup
                System.out.println("White Wins! Stage Cleared. Granting rewards.");
                // TODO: Implement logic to add new random piece and powerup
            } else if (whitePieces == 0) {
                isGameOver = true;
                winner = Piece.PieceColor.BLACK;
                System.out.println("Black Wins! Game Over."); // Player typically controls white
            }
        }


        public String getGameId() {
            return gameId;
        }

        public Board getBoard() {
            return board;
        }

        public Piece.PieceColor getCurrentPlayer() {
            return currentPlayer;
        }
    }
}
