package com.royalhackaway.checkmatedungeon.model.pieces;

import com.royalhackaway.checkmatedungeon.model.Board;
import com.royalhackaway.checkmatedungeon.model.Piece;
import com.royalhackaway.checkmatedungeon.model.Position;
import com.royalhackaway.checkmatedungeon.model.pieces.PieceType;

import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(color);
        this.symbol = color == PieceColor.WHITE ? "♘" : "♞";
        this.pieceType = PieceType.KNIGHT;
    }

    @Override
    public Set<Position> getValidMoves(Board board, Position currentPos) {
        Set<Position> validMoves = new HashSet<>();
        int r = currentPos.getRow();
        int c = currentPos.getCol();

        int[] rowOffsets = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] colOffsets = {-1, 1, -2, 2, -2, 2, -1, 1};

        for (int i = 0; i < 8; i++) {
            int newRow = r + rowOffsets[i];
            int newCol = c + colOffsets[i];
            Position newPos = new Position(newRow, newCol);

            if (board.isValid(newPos) && (board.getPieceAt(newPos) == null || board.getPieceAt(newPos).getColor() != this.color)) {
                validMoves.add(newPos);
            }
        }
        return validMoves;
    }
}
