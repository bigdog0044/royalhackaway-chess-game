package com.royalhackaway.checkmatedungeon.model.pieces;

import com.royalhackaway.checkmatedungeon.model.Board;
import com.royalhackaway.checkmatedungeon.model.Piece;
import com.royalhackaway.checkmatedungeon.model.Position;
import com.royalhackaway.checkmatedungeon.model.pieces.PieceType;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece {

    public Bishop(PieceColor color) {
        super(color);
        this.symbol = color == PieceColor.WHITE ? "♗" : "♝";
        this.pieceType = PieceType.BISHOP;
    }

    @Override
    public Set<Position> getValidMoves(Board board, Position currentPos) {
        return getMoves(board, currentPos, this);
    }

    public static Set<Position> getMoves(Board board, Position currentPos, Piece piece) {
        Set<Position> validMoves = new HashSet<>();
        int r = currentPos.getRow();
        int c = currentPos.getCol();

        int[] rowOffsets = {-1, -1, 1, 1};
        int[] colOffsets = {-1, 1, -1, 1};

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < board.getBoardSize() + piece.getRangeModifier(); j++) {
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
                    if (pieceAtNewPos.getColor() != piece.getColor()) {
                        validMoves.add(newPos); // Capture
                    }
                    break; // Stop after finding a piece
                }
            }
        }
        return validMoves;
    }
}
