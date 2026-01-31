# Developer Skills & Learning Focus

## 1. Java Backend Engineering (Core Logic)
The backbone of this game is a robust Java server that maintains state and validates complex logic.

* **Spring Boot / Jakarta EE:**
    * **REST API Design:** Building stateless endpoints (`/move`, `/start`, `/equip`) to communicate with the frontend.
    * **Session Management:** Handling distinct game sessions for different players without crossing data.
* **Object-Oriented Design (OOD):**
    * **Polymorphism:** Creating a flexible `Piece` interface with subclasses (`Rook`, `Knight`, `Hero`) that override `getValidMoves()` and `attack()`.
    * **Design Patterns:** Implementing the **Strategy Pattern** for movement logic or the **Factory Pattern** for generating random loot/pieces.
* **Algorithmic Logic:**
    * **Graph Theory (BFS/DFS):** Crucial for the "Jagged Board" requirement. You must implement a Flood Fill or Breadth-First Search algorithm to ensure the generated procedural board is fully connected and playable (no isolated islands).
    * **2D Array Manipulation:** Managing grid coordinate systems `(x, y)` efficiently.

## 2. Frontend Development (Visuals & Interaction)
The frontend needs to handle frequent state updates and present a distinct "Retro" aesthetic.

* **Modern JavaScript (ES6+) / TypeScript:**
    * **Async/Await:** Handling the latency between the player making a move and the LLM (Gemini) returning its move/taunt.
    * **State Management:** Keeping track of local inventory, current turn, and highlighted tiles without constant server polling.
* **Rendering Techniques:**
    * **CSS Grid/Flexbox:** Sufficient for a simple grid, but requires advanced CSS for the "jagged" look (handling empty div slots gracefully).
    * **Canvas API (Optional):** If visual effects (scanlines, particles on capture) become complex, knowledge of HTML5 Canvas is beneficial.
* **Spotify Web API:**
    * **OAuth 2.0:** Understanding the authentication flow to get a user token to control their music playback.
    * **SDK Implementation:** Using the Spotify Web Playback SDK to shuffle and control tracks via JavaScript.

## 3. AI & LLM Integration (The "Evil" Engine)
Integrating Gemini is more than just an API call; it requires specific "Prompt Engineering" skills.

* **Prompt Engineering:**
    * **Persona Injection:** Crafting system prompts that force the AI to stay in character (Evil DM) and not break the fourth wall.
    * **Structured Output:** Forcing the LLM to output clean JSON (e.g., `{"move": "e5", "taunt": "..."}`) rather than conversational text, often using one-shot or few-shot prompting.
* **Context Management:**
    * **Token Optimization:** Sending the board state efficiently (e.g., using FEN notation or a compressed matrix) to avoid hitting token limits or high latency.
    * **Error Handling:** Implementing fallback logic if the AI generates an illegal move (hallucination).

## 4. Game Design Mechanics
* **Procedural Generation:**
    * Understanding how to scale difficulty. How do you mathematically ensure Stage 5 is harder than Stage 1? (e.g., increasing enemy density vs. increasing board size).
* **Balancing:**
    * Tweaking damage numbers and cooldowns. A "Roguelite" requires a delicate balance between "challenging" and "unfair."

## 5. DevOps & Tools
* **Git/Version Control:** Managing branches for frontend vs. backend.
* **Build Tools:** Maven or Gradle for the Java backend; Vite or Webpack for the JS frontend.
* **Testing:**
    * **JUnit:** Essential for testing chess movement logic. (e.g., "Does the Knight *actually* jump over obstacles?").