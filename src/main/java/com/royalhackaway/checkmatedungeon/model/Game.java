package com.royalhackaway.checkmatedungeon.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.royalhackaway.checkmatedungeon.model.powerups.BootsOfSpeedPowerUp;
import com.royalhackaway.checkmatedungeon.model.powerups.ExtraMovePowerUp;
import com.royalhackaway.checkmatedungeon.model.powerups.HeavyArmorPowerUp;
import com.royalhackaway.checkmatedungeon.model.powerups.OmniSlashPowerUp;
import com.royalhackaway.checkmatedungeon.promptengineering.AICommunication;

/*
  Consolidated Game implementation. Kept intentionally minimal and deterministic
  so tests and Docker builds can proceed. Existing, more advanced logic can be
  merged in later.
*/
public class Game {

	// --- Core state used by GameService and serialization ---
	private String gameId;
	private Board board;
	private Piece.PieceColor currentPlayer = Piece.PieceColor.WHITE;
	private String message = "Game initialized";
	private boolean gameOver = false;
	private Piece.PieceColor winner = null;
	private GameFlowState currentFlowState = null; // nullable to avoid unknown enum constants
	private List<RewardGenerator.RewardOption> availableRewards = new ArrayList<>();
	private PowerUp powerUpToSacrifice = null;
	private Player whitePlayer = null;

	// Extra-move flag used by ExtraMovePowerUp
	private boolean hasExtraMove = false;

	// AI communication for Gemini-powered moves
	private AICommunication aiCommunication;

	// --- Constructors ---
	public Game() {
		this(java.util.UUID.randomUUID().toString());
	}

	public Game(String gameId) {
		this.gameId = gameId;
<<<<<<< HEAD
		this.aiCommunication = new AICommunication();
		this.stage = 1;
=======
>>>>>>> parent of 0370ca7 (CHESS IS WORKING)
		this.board = new Board(6);
		this.board.setupInitialBoard();

		// Equip black pieces with simple power-ups for deterministic testing
		List<PowerUp> pool = List.of(
				new BootsOfSpeedPowerUp(),
				new ExtraMovePowerUp(),
				new HeavyArmorPowerUp(),
				new OmniSlashPowerUp()
		);
		int idx = 0;
		for (int r = 0; r < board.getBoardSize(); r++) {
			for (int c = 0; c < board.getBoardSize(); c++) {
				Piece p = board.getPieceAt(new Position(r, c));
				if (p != null && p.getColor() == Piece.PieceColor.BLACK) {
					PowerUp template = pool.get(idx % pool.size());
					if (template instanceof BootsOfSpeedPowerUp) p.setEquippedPowerUp(new BootsOfSpeedPowerUp());
					else if (template instanceof ExtraMovePowerUp) p.setEquippedPowerUp(new ExtraMovePowerUp());
					else if (template instanceof HeavyArmorPowerUp) p.setEquippedPowerUp(new HeavyArmorPowerUp());
					else if (template instanceof OmniSlashPowerUp) p.setEquippedPowerUp(new OmniSlashPowerUp());
					idx++;
				}
			}
		}
	}

	// --- Getters / Setters ---
	public String getGameId() { return gameId; }
	public Board getBoard() { return board; }
	public Piece.PieceColor getCurrentPlayer() { return currentPlayer; }
	public String getMessage() { return message; }
	public boolean isGameOver() { return gameOver; }
	public Piece.PieceColor getWinner() { return winner; }
	public GameFlowState getCurrentFlowState() { return currentFlowState; }
	public List<RewardGenerator.RewardOption> getAvailableRewards() { return availableRewards; }
	public PowerUp getPowerUpToSacrifice() { return powerUpToSacrifice; }
	public Player getWhitePlayer() { return whitePlayer; }
	public void setMessage(String m) { this.message = m; }
	public void setCurrentPlayer(Piece.PieceColor p) { this.currentPlayer = p; }
	public void setHasExtraMove(boolean v) { this.hasExtraMove = v; }
	public boolean hasExtraMove() { return hasExtraMove; }

	// --- Helpers used by AI and tests ---
	public Position findPiecePosition(Piece piece) {
		if (piece == null) return null;
		for (int r = 0; r < board.getBoardSize(); r++) {
			for (int c = 0; c < board.getBoardSize(); c++) {
				Position pos = new Position(r, c);
				Piece at = board.getPieceAt(pos);
				if (at == piece) return pos;
			}
		}
		return null;
	}

	/**
	 * Move with capture logic that respects one-hit shields (HeavyArmor).
	 * Returns true if the move executed and piece moved; false if capture was blocked.
	 */
	public boolean movePieceWithCapture(Position from, Position to) {
		Piece mover = board.getPieceAt(from);
		if (mover == null || !board.isValid(to)) return false;
		Piece target = board.getPieceAt(to);
		if (target == null) {
			board.movePiece(from, to);
			return true;
		} else {
			if (target.hasOneHitShield()) {
				target.setOneHitShield(false);
				setMessage("Attack blocked by Heavy Armor!");
				return false;
			} else {
				board.setPieceAt(to, mover);
				board.setPieceAt(from, null);
				return true;
			}
		}
	}

	/**Gemini-powered AI turn: Get move from AICommunication, then execute it.
	 * Falls back to simple random move if Gemini fails.
	 */
	public void performAITurn() {
		if (isGameOver()) return;
		
		// Try to get move from Gemini AI
		Move aiMove = null;
		try {
			aiMove = aiCommunication.getAIMove(board);
		} catch (Exception e) {
			System.err.println("Gemini AI failed: " + e.getMessage());
			setMessage("AI error, using fallback move");
		}
		
		// If we got a valid move from Gemini, execute it
		if (aiMove != null && board.isValid(aiMove.getFrom()) && board.isValid(aiMove.getTo())) {
			Piece mover = board.getPieceAt(aiMove.getFrom());
			if (mover != null && mover.getColor() == Piece.PieceColor.BLACK) {
				Set<Position> validMoves = mover.getValidMoves(board, aiMove.getFrom());
				if (validMoves.contains(aiMove.getTo())) {
					movePieceWithCapture(aiMove.getFrom(), aiMove.getTo());
					// Display AI's taunt if available
					if (aiMove.getTaunt() != null && !aiMove.getTaunt().isEmpty()) {
						setMessage("AI: " + aiMove.getTaunt());
					} else {
						setMessage("AI made a move!");
					}
					currentPlayer = Piece.PieceColor.WHITE;
					return;
				}
			}
		}
		
		// Fallback: Simple random move if Gemini move is invalid
		Random rand = new Random(0xC0FFEE);
		for (int r = 0; r < board.getBoardSize(); r++) {
			for (int c = 0; c < board.getBoardSize(); c++) {
				Position pos = new Position(r, c);
				Piece p = board.getPieceAt(pos);
				if (p == null || p.getColor() != Piece.PieceColor.BLACK) continue;
				PowerUp pu = p.getEquippedPowerUp();
				if (pu != null && pu.isReady() && rand.nextDouble() < 0.6) {
					try { pu.tryActivate(this, p); setMessage("AI used " + pu.getName() + "!"); }
					catch (Exception ignored) {}
				}
				Set<Position> moves = p.getValidMoves(board, pos);
				if (!moves.isEmpty()) {
					Position[] arr = moves.toArray(new Position[0]);
					Position chosen = arr[rand.nextInt(arr.length)];
					movePieceWithCapture(pos, chosen);
					// consume extra move if set
					if (hasExtraMove) {
						hasExtraMove = false;
						Set<Position> extra = p.getValidMoves(board, chosen);
						if (!extra.isEmpty()) {
							Position[] arr2 = extra.toArray(new Position[0]);
							Position chosen2 = arr2[rand.nextInt(arr2.length)];
							movePieceWithCapture(chosen, chosen2);
						}
					}
					currentPlayer = Piece.PieceColor.WHITE;
					return;
				}
			}
		}
		currentPlayer = Piece.PieceColor.WHITE;
	}

	// --- Public API expected by GameService ---
	public void makeMove(Position from, Position to) {
		if (!board.isValid(from) || !board.isValid(to)) {
			setMessage("Invalid move coordinates");
			return;
		}
		boolean moved = movePieceWithCapture(from, to);
		if (moved) {
			currentPlayer = Piece.PieceColor.BLACK;
			performAITurn();
		}
	}

	public boolean activatePowerUp(Position pos) {
		Piece p = board.getPieceAt(pos);
		if (p == null || p.getEquippedPowerUp() == null) return false;
		try {
			p.getEquippedPowerUp().tryActivate(this, p);
			return true;
		} catch (Exception ex) {
			setMessage("Power-up activation failed: " + ex.getMessage());
			return false;
		}
	}

	public void chooseReward(int choiceIndex) {
		// no-op placeholder for now
	}

	public void sacrificePowerUp(com.royalhackaway.checkmatedungeon.model.pieces.PieceType pieceType, int powerUpIndex) {
		// no-op placeholder for now
	}

	public void applyRangeImprovement(Position piecePosition) {
		Piece p = board.getPieceAt(piecePosition);
		if (p != null) p.setRangeModifier(p.getRangeModifier() + 1);
	}

	public void start() {
		// Minimal deterministic startup behavior for tests / services.
		this.setMessage("Game started");
		// flow state left as-is to avoid depending on enum constants
	}
}
