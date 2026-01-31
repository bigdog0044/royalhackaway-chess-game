package com.royalhackaway.checkmatedungeon.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.royalhackaway.checkmatedungeon.model.pieces.*;

import java.util.Set;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = King.class, name = "king"),
    @JsonSubTypes.Type(value = Queen.class, name = "queen"),
    @JsonSubTypes.Type(value = Rook.class, name = "rook"),
    @JsonSubTypes.Type(value = Bishop.class, name = "bishop"),
    @JsonSubTypes.Type(value = Knight.class, name = "knight"),
    @JsonSubTypes.Type(value = Pawn.class, name = "pawn")
})
public abstract class Piece {

    protected final PieceColor color;
    protected String symbol;

    public Piece(PieceColor color) {
        this.color = color;
    }

    public PieceColor getColor() {
        return color;
    }

    public String getSymbol() {
        return symbol;
    }

    /**
     * Calculates all possible moves for this piece from its current position.
     * This method does not consider whether the move would place the king in check.
     *
     * @param board The current state of the board.
     * @param currentPos The piece's current position on the board.
     * @return A set of valid destination positions.
     */
    public abstract Set<Position> getValidMoves(Board board, Position currentPos);

    public enum PieceColor {
        WHITE, BLACK
    }
}
