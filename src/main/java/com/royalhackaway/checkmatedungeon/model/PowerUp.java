package com.royalhackaway.checkmatedungeon.model;

import com.royalhackaway.checkmatedungeon.model.pieces.PieceType;

public abstract class PowerUp {
    protected String name;
    protected String description;
    protected int cooldown;
    protected int currentCooldown;
    protected PieceType compatiblePieceType;

    public PowerUp(String name, String description, int cooldown, PieceType compatiblePieceType) {
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
        this.currentCooldown = 0;
        this.compatiblePieceType = compatiblePieceType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public PieceType getCompatiblePieceType() {
        return compatiblePieceType;
    }

    public boolean isReady() {
        return currentCooldown == 0;
    }

    public void startCooldown() {
        currentCooldown = cooldown;
    }

    public void decrementCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }

    /**
     * Returns true if this power-up can be activated on the provided piece.
     * A null compatiblePieceType means it is compatible with any piece.
     */
    public boolean canActivate(Piece piece) {
        if (piece == null) return false;
        if (compatiblePieceType == null) return true;
        return compatiblePieceType == piece.getPieceType();
    }

    /**
     * Attempts to activate the power-up for the given piece within the game context.
     * - Validates readiness and compatibility
     * - Calls concrete activate()
     * - Starts cooldown when activation succeeds
     *
     * Returns true when activation was performed.
     *
     * Throws IllegalStateException / IllegalArgumentException for invalid usage.
     */
    public synchronized boolean tryActivate(Game game, Piece piece) {
        if (!isReady()) throw new IllegalStateException("PowerUp '" + name + "' is on cooldown");
        if (!canActivate(piece)) throw new IllegalArgumentException("PowerUp '" + name + "' not compatible with piece type");
        activate(game, piece);
        startCooldown();
        return true;
    }

    /**
     * Concrete power-ups implement effects here.
     * Implementation should not call startCooldown(); tryActivate handles that consistently.
     */
    public abstract void activate(Game game, Piece piece);
}
