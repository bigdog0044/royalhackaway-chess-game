package com.royalhackaway.checkmatedungeon.model.pieces;

import com.royalhackaway.checkmatedungeon.model.Board;
import com.royalhackaway.checkmatedungeon.model.Piece;
import com.royalhackaway.checkmatedungeon.model.Position;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {

    public Pawn(PieceColor color) {
        super(color);
        this.symbol = color == PieceColor.WHITE ? "♙" : "♟";
    }

    @Override
    public Set<Position> getValidMoves(Board board, Position currentPos) {
        Set<Position> validMoves = new HashSet<>();
        int r = currentPos.getRow();
        int c = currentPos.getCol();
        int direction = (this.color == PieceColor.WHITE) ? -1 : 1;

        // 1. Forward move
        Position oneStep = new Position(r + direction, c);
        if (board.isValid(oneStep) && board.getPieceAt(oneStep) == null) {
            validMoves.add(oneStep);

            // 2. Initial two-step move
            boolean atStartRow = (this.color == PieceColor.WHITE && r == 6) || (this.color == PieceColor.BLACK && r == 1);
            if (atStartRow) {
                Position twoSteps = new Position(r + 2 * direction, c);
                if (board.isValid(twoSteps) && board.getPieceAt(twoSteps) == null) {
                    validMoves.add(twoSteps);
                }
            }
        }

        // 3. Captures
        int[] captureCols = {c - 1, c + 1};
        for (int captureCol : captureCols) {
            Position capturePos = new Position(r + direction, captureCol);
            if (board.isValid(capturePos) && board.getPieceAt(capturePos) != null && board.getPieceAt(capturePos).getColor() != this.color) {
                validMoves.add(capturePos);
            }
        }
        
        // En-passant and promotion are not implemented for the MVP
        
        return validMoves;
    }
}
