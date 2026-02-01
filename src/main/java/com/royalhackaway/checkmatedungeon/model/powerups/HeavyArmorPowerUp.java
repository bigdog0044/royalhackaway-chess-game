package com.royalhackaway.checkmatedungeon.model.powerups;

import com.royalhackaway.checkmatedungeon.model.Game;
import com.royalhackaway.checkmatedungeon.model.Piece;
import com.royalhackaway.checkmatedungeon.model.PowerUp;
import com.royalhackaway.checkmatedungeon.model.pieces.PieceType;


public class HeavyArmorPowerUp extends PowerUp {

    public HeavyArmorPowerUp() {
        super("Heavy Armor", "Grants the piece a one-hit shield.", 5, null); // compatible with all piece types
    }

	@Override public String getName() { return "Heavy Armor"; }
	@Override public boolean isReady() { return true; }
	@Override public void activate(Game game, Piece piece) {
		piece.setOneHitShield(true);
		game.setMessage(getName() + " activated");
	}
}
