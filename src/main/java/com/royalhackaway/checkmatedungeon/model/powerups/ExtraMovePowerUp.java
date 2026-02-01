package com.royalhackaway.checkmatedungeon.model.powerups;

import com.royalhackaway.checkmatedungeon.model.Game;
import com.royalhackaway.checkmatedungeon.model.Piece;
import com.royalhackaway.checkmatedungeon.model.PowerUp;
import com.royalhackaway.checkmatedungeon.model.pieces.PieceType;

public class ExtraMovePowerUp extends PowerUp {

    public ExtraMovePowerUp() {
        super("Extra Move", "Allows the piece to move again.", 5, null); // compatible with all piece types
    }

    @Override
    public void activate(Game game, Piece piece) {
        if (game == null) throw new IllegalArgumentException("game cannot be null");
        if (piece == null) throw new IllegalArgumentException("piece cannot be null");
        // Compatibility and cooldown are expected to be validated by tryActivate()
        game.setHasExtraMove(true); // Grant an extra move (Game should manage turn logic)
        // do NOT call startCooldown() here — tryActivate handles cooldown start
    }
}
