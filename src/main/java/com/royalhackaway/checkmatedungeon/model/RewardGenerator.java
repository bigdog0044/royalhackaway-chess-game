package com.royalhackaway.checkmatedungeon.model;

import com.royalhackaway.checkmatedungeon.model.pieces.Bishop;
import com.royalhackaway.checkmatedungeon.model.pieces.Knight;
import com.royalhackaway.checkmatedungeon.model.pieces.Rook;
import com.royalhackaway.checkmatedungeon.model.powerups.ExtraMovePowerUp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RewardGenerator {

    private static final Random random = new Random();

    public static List<RewardOption> generateRewards(int ante, int stage) {
        List<RewardOption> options = new ArrayList<>();

        // Generate 3 random reward options
        for (int i = 0; i < 3; i++) {
            RewardOption option = generateSingleReward(ante, stage);
            if (option != null) {
                options.add(option);
            }
        }
        return options;
    }

    private static RewardOption generateSingleReward(int ante, int stage) {
        if (stage == 4) { // Boss Battle rewards
            int choice = random.nextInt(3);
            switch (choice) {
                case 0: return generateLegendarySpellReward();
                case 1: return generateQueenReward();
                case 2: return generateRangeImprovementReward(); // Needs a target piece later
            }
        } else { // Normal stage rewards
            if (random.nextBoolean()) {
                return generateNewPieceReward();
            } else {
                return generateNewPowerUpReward();
            }
        }
        return null; // Should not happen
    }


    private static RewardOption generateNewPieceReward() {
        // Randomly choose between Rook, Knight, Bishop
        int choice = random.nextInt(3);
        Piece piece = null;
        String description = "";
        switch (choice) {
            case 0:
                piece = new Rook(Piece.PieceColor.WHITE);
                description = "New Rook";
                break;
            case 1:
                piece = new Knight(Piece.PieceColor.WHITE);
                description = "New Knight";
                break;
            case 2:
                piece = new Bishop(Piece.PieceColor.WHITE);
                description = "New Bishop";
                break;
        }
        return RewardOption.forPiece(RewardType.NEW_PIECE, description, piece);
    }

    private static RewardOption generateNewPowerUpReward() {
        // For now, only ExtraMovePowerUp
        PowerUp powerUp = new ExtraMovePowerUp();
        String description = "New Extra Move Power-Up";
        return RewardOption.forPowerUp(RewardType.NEW_POWER_UP, description, powerUp);
    }

    private static RewardOption generateLegendarySpellReward() {
        // Placeholder for a legendary spell
        PowerUp powerUp = new ExtraMovePowerUp(); // TODO: Replace with actual legendary spell
        String description = "Legendary Extra Move Spell";
        return RewardOption.forPowerUp(RewardType.LEGENDARY_SPELL, description, powerUp);
    }

    private static RewardOption generateQueenReward() {
        Piece queen = new com.royalhackaway.checkmatedungeon.model.pieces.Queen(Piece.PieceColor.WHITE);
        String description = "New Queen Piece";
        return RewardOption.forPiece(RewardType.QUEEN, description, queen);
    }

    private static RewardOption generateRangeImprovementReward() {
        String description = "Range Improvement (Choose a piece)"; // Actual selection will be in Game.java
        return new RewardOption(RewardType.RANGE_IMPROVEMENT, description, null, null); // No piece/power-up directly
    }

    // RewardType enum
    public enum RewardType {
        NEW_PIECE,
        NEW_POWER_UP,
        LEGENDARY_SPELL,
        RANGE_IMPROVEMENT,
        QUEEN
    }

    // Inner class for RewardOption
    public static class RewardOption {
        private final RewardType type;
        private final String description;
        private final Piece piece;
        private final PowerUp powerUp;

        // Private constructor forces usage of explicit factories
        private RewardOption(RewardType type, String description, Piece piece, PowerUp powerUp) {
            this.type = type;
            this.description = description;
            this.piece = piece;
            this.powerUp = powerUp;
        }

        public static RewardOption forPiece(RewardType type, String description, Piece piece) {
            return new RewardOption(type, description, piece, null);
        }

        public static RewardOption forPowerUp(RewardType type, String description, PowerUp powerUp) {
            return new RewardOption(type, description, null, powerUp);
        }

        public RewardType getType() {
            return type;
        }

        public String getDescription() {
            return description;
        }

        public Piece getPiece() {
            return piece;
        }

        public PowerUp getPowerUp() {
            return powerUp;
        }
    }
}
