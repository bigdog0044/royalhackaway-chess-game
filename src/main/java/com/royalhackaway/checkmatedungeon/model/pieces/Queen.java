package com.royalhackaway.checkmatedungeon.model.pieces;

import com.royalhackaway.checkmatedungeon.model.Board;
import com.royalhackaway.checkmatedungeon.model.Piece;
import com.royalhackaway.checkmatedungeon.model.Position;
import com.royalhackaway.checkmatedungeon.model.pieces.PieceType;

import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece {

    public Queen(PieceColor color) {
        super(color);
        this.symbol = color == PieceColor.WHITE ? "♕" : "♛";
        this.pieceType = PieceType.QUEEN;
    }

    @Override
    public Set<Position> getValidMoves(Board board, Position currentPos) {
        Set<Position> validMoves = new HashSet<>();
        
        // Queen's moves are a combination of Rook and Bishop moves
        validMoves.addAll(Rook.getMoves(board, currentPos, this));
        validMoves.addAll(Bishop.getMoves(board, currentPos, this));
        
        return validMoves;
    }
}
