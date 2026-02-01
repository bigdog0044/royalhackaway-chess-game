package com.royalhackaway.checkmatedungeon.promptengineering;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.genai.Chat;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
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
  public ArrayList<Integer> promptProcessing (Board board){
    String modelId = "gemini-2.5-flash";
    Client client = new Client();
    Chat chatSession = client.chats.create(modelId);

    //starts the initial prompt
    if (!isFirstPrompt){
      chatSession.sendMessage(setupPrompt());
      isFirstPrompt = true;
    }

    GenerateContentResponse response = chatSession.sendMessage("Decide what to do next based on the current board stattus: " + board.toString());
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
        
        ArrayList<Integer> resultArr = new ArrayList<>();
        resultArr.add(Integer.valueOf(fromRow));
        resultArr.add(Integer.valueOf(fromCol));
        resultArr.add(Integer.valueOf(toRow));
        resultArr.add(Integer.valueOf(toCol));
        return resultArr;
    } catch (Exception e) {
        e.printStackTrace();
        return null; 
    }


  }
}
