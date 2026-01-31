package com.royalhackaway.checkmatedungeon.model;

import java.util.List;

public class GameState {

    private final String gameId;
    private final Piece[][] board;
    private final Piece.PieceColor currentPlayer;
    private final String message; // For AI taunts or game status
    private final boolean isGameOver;
    private final Piece.PieceColor winner;

    public GameState(String gameId, Board board, Piece.PieceColor currentPlayer, String message, boolean isGameOver, Piece.PieceColor winner) {
        this.gameId = gameId;
        // A simplified representation of the board for the frontend
        this.board = new Piece[board.getBoardSize()][board.getBoardSize()];
        for(int r=0; r < board.getBoardSize(); r++) {
            for (int c=0; c < board.getBoardSize(); c++) {
                this.board[r][c] = board.getPieceAt(new Position(r,c));
            }
        }
        this.currentPlayer = currentPlayer;
        this.message = message;
        this.isGameOver = isGameOver;
        this.winner = winner;
    }

    // Getters for serialization to JSON by Spring Web
    public String getGameId() {
        return gameId;
    }
    
    public Piece[][] getBoard() {
        return board;
    }

    public Piece.PieceColor getCurrentPlayer() {
        return currentPlayer;
    }

    public String getMessage() {
        return message;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Piece.PieceColor getWinner() {
        return winner;
    }
}
