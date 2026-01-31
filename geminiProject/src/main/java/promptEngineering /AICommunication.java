package promptEngineering;

import com.google.genai.Chat;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.royalhackaway.checkmatedungeon.model.Board;

public class AICommunication {

  public String setupPrompt(){
    
  }
  public void sendPrompt(){
    String modelId = "gemini-2.5-flash";
    Client client = new Client();
    Chat chatSession = client.chats.create(modelId);

  }
  // public static void main(String[] args) {
  //   // The client gets the API key from the environment variable `GEMINI_API_KEY`.
  //   Client client = new Client();
  //   //uses to select the model
  //   String modelId = "gemini-2.5-flash";

  //   //creates a chat session in which it remembers previous conversations
  //   Chat chatSession = client.chats.create(modelId);

  //   //this is the message sending stage

  //   // 2. Send first message
  //       chatSession.sendMessage("My favorite color is Blue.");

  //   // 3. Send second message - the AI will remember!
  //   GenerateContentResponse response = chatSession.sendMessage("What is my favorite color?");
        
  //   System.out.println(response.text()); // Output: "Your favorite color is Blue."
  // }
}
