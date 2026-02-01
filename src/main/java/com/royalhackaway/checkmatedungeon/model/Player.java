package com.royalhackaway.checkmatedungeon.model;

import com.royalhackaway.checkmatedungeon.model.Piece.PieceColor;
import com.royalhackaway.checkmatedungeon.model.pieces.PieceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private final PieceColor color;
    private List<Piece> reservePieces; // Pieces not currently on the board
    private Map<PieceType, List<PowerUp>> spellSlots; // Power-ups managed by piece type, with limited slots per type

    public Player(PieceColor color) {
        this.color = color;
        this.reservePieces = new ArrayList<>();
        this.spellSlots = new HashMap<>();
        // Initialize spell slots for each piece type
        for (PieceType type : PieceType.values()) {
            spellSlots.put(type, new ArrayList<>());
        }
    }

    public PieceColor getColor() {
        return color;
    }

    public List<Piece> getReservePieces() {
        return reservePieces;
    }

    public void addReservePiece(Piece piece) {
        this.reservePieces.add(piece);
    }

    public Map<PieceType, List<PowerUp>> getSpellSlots() {
        return spellSlots;
    }

    public enum AddPowerUpStatus {
        ADDED,
        ALREADY_EXISTS, // PowerUp already exists for this PieceType
        SLOTS_FULL,     // Slots full for this PieceType, sacrifice needed
        INCOMPATIBLE    // PowerUp is not compatible with any piece type (e.g. if compatiblePieceType is null)
    }

    // Method to add a power-up to a specific piece type's slots
    public AddPowerUpStatus addPowerUp(PowerUp powerUp) {
        if (powerUp.getCompatiblePieceType() == null) {
            return AddPowerUpStatus.INCOMPATIBLE;
        }

        List<PowerUp> slots = spellSlots.get(powerUp.getCompatiblePieceType());
        if (slots == null) { // Should not happen with current initialization
            return AddPowerUpStatus.INCOMPATIBLE;
        }

        // Check if power-up already exists (by name for simplicity)
        for (PowerUp existingPowerUp : slots) {
            if (existingPowerUp.getName().equals(powerUp.getName())) {
                // In future: upgrade existing power-up
                return AddPowerUpStatus.ALREADY_EXISTS;
            }
        }

        if (slots.size() < 2) { // Assuming 2 slots per piece category
            slots.add(powerUp);
            return AddPowerUpStatus.ADDED;
        }
        return AddPowerUpStatus.SLOTS_FULL; // Slots full, sacrifice needed
    }

    // Method to remove a power-up (for sacrificing)
    public boolean removePowerUp(PieceType pieceType, PowerUp powerUp) {
        List<PowerUp> slots = spellSlots.get(pieceType);
        if (slots != null) {
            return slots.remove(powerUp);
        }
        return false;
    }


    // Method to check if a player has any pieces left (on board or in reserve)
    public boolean hasPiecesRemaining(Board board) {
        // Check pieces on board
        for (int r = 0; r < board.getBoardSize(); r++) {
            for (int c = 0; c < board.getBoardSize(); c++) {
                Piece p = board.getPieceAt(new Position(r, c));
                if (p != null && p.getColor() == this.color) {
                    return true;
                }
            }
        }
        // Check reserve pieces
        return !reservePieces.isEmpty();
    }
}
