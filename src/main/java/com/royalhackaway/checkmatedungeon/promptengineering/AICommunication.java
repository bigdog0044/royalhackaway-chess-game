package com.royalhackaway.checkmatedungeon.promptengineering;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.genai.Chat;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.royalhackaway.checkmatedungeon.model.Board;
import com.royalhackaway.checkmatedungeon.model.Move;
import com.royalhackaway.checkmatedungeon.model.Position;

public class AICommunication {
  private boolean isFirstPrompt = false;
  
  private String setupPrompt(){
    return """
System:
You are the Evil Dungeon Master AI from Checkmate Dungeon. You are arrogant, mocking, and evil.
Follow the rules and output exactly one legal move and a cruel, taunting message.

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
{"from":{"row":r,"col":c},"to":{"row":r,"col":c},"taunt":"A cruel, mocking message. Keep it short (1-2 sentences). Taunt the player's moves, strategy, or misfortune."}

Be in character. Be evil. Be funny. Mock their piece choices and tactics.
""";
  }
  public Move getAIMove(Board board) {
    String modelId = "gemini-2.5-flash";
    Client client = new Client();
    Chat chatSession = client.chats.create(modelId);

    // Initialize with system prompt
    if (!isFirstPrompt){
      chatSession.sendMessage(setupPrompt());
      isFirstPrompt = true;
    }

    // Request move from Gemini with board state
    String boardPrompt = "Decide what to do next based on the current board status: " + board.toString();
    GenerateContentResponse response = chatSession.sendMessage(boardPrompt);
    String result = response.text();
    
     // Extract JSON if wrapped in code blocks
    String json = result.trim();
    if (json.contains("```json")) {
        json = json.substring(json.indexOf("```json") + 7, json.lastIndexOf("```")).trim();
    } else if (json.contains("```")) {
        json = json.substring(json.indexOf("```") + 3, json.lastIndexOf("```")).trim();
    }

    try {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        
        // Access fields
        JSONObject from = (JSONObject) jsonObject.get("from");
        JSONObject to = (JSONObject) jsonObject.get("to");
        String taunt = (String) jsonObject.get("taunt");
        
        int fromRow = ((Long) from.get("row")).intValue();
        int fromCol = ((Long) from.get("col")).intValue();
        int toRow = ((Long) to.get("row")).intValue();
        int toCol = ((Long) to.get("col")).intValue();
        
        // Create Move object
        Move move = new Move();
        move.setFrom(new Position(fromRow, fromCol));
        move.setTo(new Position(toRow, toCol));
        
        System.out.println("AI Move: (" + fromRow + "," + fromCol + ") -> (" + toRow + "," + toCol + ")");
        System.out.println("AI Taunt: " + taunt);
        
        return move;
    } catch (Exception e) {
        e.printStackTrace();
        return null; 
    }
  }
  
  public void executeAIMove(Board board) {
    Move move = getAIMove(board);
    if (move != null) {
      board.movePiece(move.getFrom(), move.getTo());
      System.out.println("Move executed successfully!");
    } else {
      System.out.println("Failed to get AI move.");
    }
  }
}
