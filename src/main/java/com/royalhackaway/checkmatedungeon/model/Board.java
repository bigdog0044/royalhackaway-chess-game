package com.royalhackaway.checkmatedungeon.model;

import com.royalhackaway.checkmatedungeon.model.pieces.*;

import java.util.*;

public class Board {

    private final int boardSize;
    private final Piece[][] grid;
    private final boolean[][] voidTiles;

    public Board(int size) {
        this.boardSize = size;
        this.grid = new Piece[size][size];
        this.voidTiles = new boolean[size][size];
    }

    public void setupStandardBoard() {
        // Clear board
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                grid[r][c] = null;
            }
        }

        // Setup pieces for a standard 8x8 board
        if (boardSize == 8) {
            // Black pieces
            grid[0][0] = new Rook(Piece.PieceColor.BLACK);
            grid[0][1] = new Knight(Piece.PieceColor.BLACK);
            grid[0][2] = new Bishop(Piece.PieceColor.BLACK);
            grid[0][3] = new Queen(Piece.PieceColor.BLACK);
            grid[0][4] = new King(Piece.PieceColor.BLACK);
            grid[0][5] = new Bishop(Piece.PieceColor.BLACK);
            grid[0][6] = new Knight(Piece.PieceColor.BLACK);
            grid[0][7] = new Rook(Piece.PieceColor.BLACK);
            for (int c = 0; c < 8; c++) {
                grid[1][c] = new Pawn(Piece.PieceColor.BLACK);
            }

            // White pieces
            grid[7][0] = new Rook(Piece.PieceColor.WHITE);
            grid[7][1] = new Knight(Piece.PieceColor.WHITE);
            grid[7][2] = new Bishop(Piece.PieceColor.WHITE);
            grid[7][3] = new Queen(Piece.PieceColor.WHITE);
            grid[7][4] = new King(Piece.PieceColor.WHITE);
            grid[7][5] = new Bishop(Piece.PieceColor.WHITE);
            grid[7][6] = new Knight(Piece.PieceColor.WHITE);
            grid[7][7] = new Rook(Piece.PieceColor.WHITE);
            for (int c = 0; c < 8; c++) {
                grid[6][c] = new Pawn(Piece.PieceColor.WHITE);
            }
        }
    }
    
    // NOTE: The procedural generation for the "jagged" board is complex
    // and will be added in a future iteration. For the MVP, we use a standard board.

    public int getBoardSize() {
        return boardSize;
    }

    public Piece getPieceAt(Position pos) {
        if (!isValid(pos)) return null;
        return grid[pos.getRow()][pos.getCol()];
    }

    public void setPieceAt(Position pos, Piece piece) {
        if (isValid(pos)) {
            grid[pos.getRow()][pos.getCol()] = piece;
        }
    }

    public boolean isValid(Position pos) {
        int r = pos.getRow();
        int c = pos.getCol();
        return r >= 0 && r < boardSize && c >= 0 && c < boardSize && !voidTiles[r][c];
    }
    
    public void movePiece(Position from, Position to) {
        Piece piece = getPieceAt(from);
        setPieceAt(to, piece);
        setPieceAt(from, null);
    }
}
