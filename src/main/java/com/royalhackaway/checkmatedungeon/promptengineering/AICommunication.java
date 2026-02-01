package com.royalhackaway.checkmatedungeon.promptengineering;

import com.google.genai.Chat;
import com.google.genai.Client;
import com.royalhackaway.checkmatedungeon.model.Board;

public class AICommunication {
  private boolean isFirstPrompt = false;
  private String setupPrompt(){
    return """
System:
You are the rules engine for Checkmate Dungeon. Follow the rules and output exactly one legal move and a taunt.

Rules:
- Board is N x N with 0-based coordinates: row, col.
- Valid squares are those provided in the board state; ignore voids if present.
- Pieces: Rook (orthogonal slides), Bishop (diagonal slides), Queen (rook+bishop), Knight (L-shaped).
- You can move to empty squares or capture an enemy piece; you cannot capture your own piece.
- Sliding pieces stop at the first occupied square in any direction.
- No kings, pawns, castling, en passant, or promotion.

Input:
- Current player color.
- Board state as JSON with pieces and their positions.
- List of valid moves (if provided) must be obeyed.

Output (JSON only):
{"from":{"row":r,"col":c},"to":{"row":r,"col":c},"taunt":"..."}
""";
  }
  public void promptProcessing (Board board){
    String modelId = "gemini-2.5-flash";
    Client client = new Client();
    Chat chatSession = client.chats.create(modelId);

    //starts the initial prompt
    if (!isFirstPrompt){
      chatSession.sendMessage(setupPrompt());
      isFirstPrompt = true;
    }

    chatSession.sendMessage("Decide what to do next based on the current board stattus: " + board.toString());
  }

  private String setupPrompt(Board board){
    // Create a short board-aware prompt (future: serialize board to compact notation)
    String prompt = "Board state for debugging: " + (board != null ? board.toString() : "no-board") + "\n";
    prompt += "Provide a taunt only (no powerup/move logic).";
    return prompt;
  }

  // Single canonical method used by tests / TestAI
  public void promptProcessing(Board board){
    System.out.println(setupPrompt(board));
  }
}
