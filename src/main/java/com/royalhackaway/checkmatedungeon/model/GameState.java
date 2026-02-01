package com.royalhackaway.checkmatedungeon.model;

import java.util.List;

public class GameState {

    private final String gameId;
    private final Piece[][] board;
    private final Piece.PieceColor currentPlayer;
    private final String message; // For AI taunts or game status
    private final boolean isGameOver;
    private final Piece.PieceColor winner;
    private final GameFlowState currentFlowState;
    private final List<RewardGenerator.RewardOption> availableRewards;
    private final PowerUp powerUpToSacrifice;
    private final Player whitePlayer; // To expose player's reserve pieces and spell slots
    private final int stage;
    private final boolean[][] voidTiles;

    public GameState(String gameId, Board board, Piece.PieceColor currentPlayer, String message, boolean isGameOver, Piece.PieceColor winner, GameFlowState currentFlowState, List<RewardGenerator.RewardOption> availableRewards, PowerUp powerUpToSacrifice, Player whitePlayer, int stage) {
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
        this.currentFlowState = currentFlowState;
        this.availableRewards = availableRewards;
        this.powerUpToSacrifice = powerUpToSacrifice;
        this.whitePlayer = whitePlayer;
        this.stage = stage;
        this.voidTiles = board.getVoidTiles();
        
    }

    public boolean[][] getVoidTiles() {
        return voidTiles;
    }

    public int getStage() {
        return stage;
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

    public GameFlowState getCurrentFlowState() {
        return currentFlowState;
    }

    public List<RewardGenerator.RewardOption> getAvailableRewards() {
        return availableRewards;
    }

    public PowerUp getPowerUpToSacrifice() {
        return powerUpToSacrifice;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }


    @Override
    public String toString() {
        return board.toString();
    }
     
}
