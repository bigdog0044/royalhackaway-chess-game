package com.royalhackaway.checkmatedungeon.model.powerups;

import com.royalhackaway.checkmatedungeon.model.Game;
import com.royalhackaway.checkmatedungeon.model.Piece;
import com.royalhackaway.checkmatedungeon.model.PowerUp;
import com.royalhackaway.checkmatedungeon.model.Position;
import com.royalhackaway.checkmatedungeon.model.pieces.PieceType;


public class BootsOfSpeedPowerUp extends PowerUp {

    public BootsOfSpeedPowerUp() {
        super("Boots of Speed", "Increases the movement range of the piece.", 5, null); // compatible with all piece types
    }

	@Override public String getName() { return "Boots of Speed"; }
	@Override public boolean isReady() { return true; }

	@Override
	public void activate(Game game, Piece piece) {
		Position pos = game.findPiecePosition(piece);
		if (pos != null) game.applyRangeImprovement(pos);
		game.setMessage(getName() + " equipped");
	}
}