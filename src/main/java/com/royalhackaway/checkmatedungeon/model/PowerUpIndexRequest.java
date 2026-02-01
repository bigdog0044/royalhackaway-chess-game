package com.royalhackaway.checkmatedungeon.model;

import com.royalhackaway.checkmatedungeon.model.pieces.PieceType;

public class PowerUpIndexRequest {
    private PieceType pieceType;
    private int powerUpIndex;

    // Getters and Setters
    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public int getPowerUpIndex() {
        return powerUpIndex;
    }

    public void setPowerUpIndex(int powerUpIndex) {
        this.powerUpIndex = powerUpIndex;
    }
}
