package com.royalhackaway.checkmatedungeon.model.pieces;

import com.royalhackaway.checkmatedungeon.model.Board;
import com.royalhackaway.checkmatedungeon.model.Piece;
import com.royalhackaway.checkmatedungeon.model.Position;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {

    public Rook(PieceColor color) {
        super(color);
        this.symbol = color == PieceColor.WHITE ? "♖" : "♜";
    }

    @Override
    public Set<Position> getValidMoves(Board board, Position currentPos) {
        return getMoves(board, currentPos, this.color);
    }
    
    public static Set<Position> getMoves(Board board, Position currentPos, PieceColor color) {
        Set<Position> validMoves = new HashSet<>();
        int r = currentPos.getRow();
        int c = currentPos.getCol();

        int[] rowOffsets = {-1, 1, 0, 0};
        int[] colOffsets = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < board.getBoardSize(); j++) {
                int newRow = r + rowOffsets[i] * j;
                int newCol = c + colOffsets[i] * j;
                Position newPos = new Position(newRow, newCol);

                if (!board.isValid(newPos)) {
                    break; // Stop if off board
                }

                Piece pieceAtNewPos = board.getPieceAt(newPos);
                if (pieceAtNewPos == null) {
                    validMoves.add(newPos); // Empty square
                } else {
                    if (pieceAtNewPos.getColor() != color) {
                        validMoves.add(newPos); // Capture
                    }
                    break; // Stop after finding a piece
                }
            }
        }
        return validMoves;
    }
}
