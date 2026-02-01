package com.royalhackaway.checkmatedungeon.model;

import com.royalhackaway.checkmatedungeon.model.pieces.*;

import java.util.*;

public class Board {

    private int boardSize;
    private Piece[][] grid;
    private boolean[][] voidTiles;

    public Board(int size) {
        this.boardSize = size;
        this.grid = new Piece[size][size];
        this.voidTiles = new boolean[size][size];
    }

    public void grow() {
        int newSize = boardSize + 1;
        Piece[][] newGrid = new Piece[newSize][newSize];
        boolean[][] newVoidTiles = new boolean[newSize][newSize];

        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                newGrid[r][c] = grid[r][c];
            }
        }

        this.boardSize = newSize;
        this.grid = newGrid;
        this.voidTiles = newVoidTiles;
    }

    public void setupInitialBoard() {
        // Clear board and voidTiles
        Random rand = new Random();
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                grid[r][c] = null;
                // 20% chance to be a void tile, but never for starting/ending rows
                if (r != 0 && r != boardSize-1 && c != 0 && c != boardSize-1 && rand.nextDouble() < 0.2) {
                    voidTiles[r][c] = true;
                } else {
                    voidTiles[r][c] = false;
                }
            }
        }

        // Player pieces (WHITE) - only if not void
        if (boardSize > 5) {
            if (!voidTiles[0][0]) grid[0][0] = new Rook(Piece.PieceColor.WHITE);
            if (!voidTiles[0][5]) grid[0][5] = new Rook(Piece.PieceColor.WHITE);
            if (!voidTiles[0][1]) grid[0][1] = new Knight(Piece.PieceColor.WHITE);
            if (!voidTiles[0][4]) grid[0][4] = new Knight(Piece.PieceColor.WHITE);
            if (!voidTiles[0][2]) grid[0][2] = new Bishop(Piece.PieceColor.WHITE);
            if (!voidTiles[0][3]) grid[0][3] = new Bishop(Piece.PieceColor.WHITE);

            // AI pieces (BLACK)
            if (!voidTiles[5][0]) grid[5][0] = new Rook(Piece.PieceColor.BLACK);
            if (!voidTiles[5][5]) grid[5][5] = new Rook(Piece.PieceColor.BLACK);
            if (!voidTiles[5][1]) grid[5][1] = new Knight(Piece.PieceColor.BLACK);
            if (!voidTiles[5][4]) grid[5][4] = new Knight(Piece.PieceColor.BLACK);
            if (!voidTiles[5][2]) grid[5][2] = new Bishop(Piece.PieceColor.BLACK);
            if (!voidTiles[5][3]) grid[5][3] = new Bishop(Piece.PieceColor.BLACK);
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

    public boolean[][] getVoidTiles() {
        // Defensive copy
        boolean[][] copy = new boolean[boardSize][boardSize];
        for (int r = 0; r < boardSize; r++) {
            System.arraycopy(voidTiles[r], 0, copy[r], 0, boardSize);
        }
        return copy;
    }
    
    public void movePiece(Position from, Position to) {
        Piece piece = getPieceAt(from);
        setPieceAt(to, piece);
        setPieceAt(from, null);
    }

    public void addPiece(Piece piece, Position pos) {
        if (isValid(pos)) {
            grid[pos.getRow()][pos.getCol()] = piece;
        }
    }

    public void addAIPiecesForNewAnte(int ante, int stage) {
        // Place new AI pieces on the last row of the grown board
        int lastRow = boardSize - 1;

        if (stage == 4) { // Boss Battle
            // Add a Queen for boss battles
            addPiece(new Queen(Piece.PieceColor.BLACK), new Position(lastRow, 0));
            // Add more pieces or stronger pieces
            addPiece(new Rook(Piece.PieceColor.BLACK), new Position(lastRow, 1));
            addPiece(new Knight(Piece.PieceColor.BLACK), new Position(lastRow, 2));
            addPiece(new Bishop(Piece.PieceColor.BLACK), new Position(lastRow, 3));
        } else { // Normal stages
            // Rook: (lastRow,0), (lastRow,5)
            addPiece(new Rook(Piece.PieceColor.BLACK), new Position(lastRow, 0));
            addPiece(new Rook(Piece.PieceColor.BLACK), new Position(lastRow, 5));

            // Knight: (lastRow,1), (lastRow,4)
            addPiece(new Knight(Piece.PieceColor.BLACK), new Position(lastRow, 1));
            addPiece(new Knight(Piece.PieceColor.BLACK), new Position(lastRow, 4));

            // Bishop: (lastRow,2), (lastRow,3)
            addPiece(new Bishop(Piece.PieceColor.BLACK), new Position(lastRow, 2));
            addPiece(new Bishop(Piece.PieceColor.BLACK), new Position(lastRow, 3));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"n\":").append(boardSize).append(",");
        sb.append("\"pieces\":[");
        boolean first = true;
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                Piece p = grid[r][c];
                if (p != null) {
                    if (!first) sb.append(",");
                    sb.append("{");
                    sb.append("\"type\":\"").append(p.getClass().getSimpleName()).append("\",");
                    sb.append("\"color\":\"").append(p.getColor()).append("\",");
                    sb.append("\"symbol\":\"").append(escapeJson(p.getSymbol())).append("\",");
                    sb.append("\"row\":").append(r).append(",");
                    sb.append("\"col\":").append(c);
                    sb.append("}");
                    first = false;
                }
            }
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}
