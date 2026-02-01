package com.royalhackaway.checkmatedungeon.model.powerups;

import com.royalhackaway.checkmatedungeon.model.Game;
import com.royalhackaway.checkmatedungeon.model.Piece;
import com.royalhackaway.checkmatedungeon.model.PowerUp;
import com.royalhackaway.checkmatedungeon.model.pieces.PieceType;


public class OmniSlashPowerUp extends PowerUp {

	public OmniSlashPowerUp() {
		super("Omni Slash", "Allows the piece to attack in all directions.", 5, null); // compatible with all piece types
	}

	@Override public String getName() { return "Omni Slash"; }
	@Override public boolean isReady() { return true; }
	@Override public void activate(Game game, Piece piece) {
		// Deterministic placeholder; advanced logic can be added later.
		game.setMessage(getName() + " activated");
	}
}
