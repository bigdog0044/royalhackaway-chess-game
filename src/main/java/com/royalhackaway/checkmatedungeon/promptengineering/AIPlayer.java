package com.royalhackaway.checkmatedungeon.promptengineering;

import com.royalhackaway.checkmatedungeon.model.Board;
import com.royalhackaway.checkmatedungeon.model.Game;
import com.royalhackaway.checkmatedungeon.model.Piece;
import com.royalhackaway.checkmatedungeon.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AIPlayer {
    private static final Random random = new Random();

    public void makeDecision(Game game) {
        // For now, a very simple AI: randomly choose a piece and a valid move
        // Later, this will involve more sophisticated logic, power-up activation, and difficulty scaling

        Board board = game.getBoard();
        List<Position> aiPieces = new ArrayList<>();

        // Find all AI pieces on the board
        for (int r = 0; r < board.getBoardSize(); r++) {
            for (int c = 0; c < board.getBoardSize(); c++) {
                Position currentPos = new Position(r, c);
                Piece piece = board.getPieceAt(currentPos);
                if (piece != null && piece.getColor() == Piece.PieceColor.BLACK) {
                    aiPieces.add(currentPos);
                }
            }
        }

        if (aiPieces.isEmpty()) {
            return; // No AI pieces left, game should be over
        }

        // --- Power-up activation logic ---
        // For simplicity, prioritize activating ExtraMovePowerUp if available and ready
        for (Position pos : aiPieces) {
            Piece piece = board.getPieceAt(pos);
            if (piece != null && piece.getEquippedPowerUp() != null && piece.getEquippedPowerUp().isReady()) {
                // For now, activate if ExtraMovePowerUp. More sophisticated logic later.
                if (piece.getEquippedPowerUp().getName().equals("Extra Move")) {
                    if (game.activatePowerUp(pos)) {
                        return; // Power-up activated, turn passed or extra move granted
                    }
                }
            }
        }
        // --- End Power-up activation logic ---

        List<MoveOption> capturingMoves = new ArrayList<>();
        List<MoveOption> nonCapturingMoves = new ArrayList<>();

        for (Position from : aiPieces) {
            Piece piece = board.getPieceAt(from);
            if (piece == null) continue; // Should not happen if aiPieces list is well-maintained
            
            Set<Position> validMoves = piece.getValidMoves(board, from);
            for (Position to : validMoves) {
                if (board.getPieceAt(to) != null && board.getPieceAt(to).getColor() == Piece.PieceColor.WHITE) {
                    capturingMoves.add(new MoveOption(from, to));
                } else {
                    nonCapturingMoves.add(new MoveOption(from, to));
                }
            }
        }

        MoveOption chosenMove = null;
        if (!capturingMoves.isEmpty()) {
            chosenMove = capturingMoves.get(random.nextInt(capturingMoves.size()));
        } else if (!nonCapturingMoves.isEmpty()) {
            chosenMove = nonCapturingMoves.get(random.nextInt(nonCapturingMoves.size()));
        }

        if (chosenMove != null) {
            game.makeMove(chosenMove.getFrom(), chosenMove.getTo());
        } else {
            // If no moves, just pass turn (shouldn't happen often if game end conditions are checked)
            game.setCurrentPlayer(Piece.PieceColor.WHITE); // Assuming AI is black, pass turn to white
        }
    }

    private static class MoveOption {
        private final Position from;
        private final Position to;

        public MoveOption(Position from, Position to) {
            this.from = from;
            this.to = to;
        }

        public Position getFrom() {
            return from;
        }

        public Position getTo() {
            return to;
        }
    }
}
