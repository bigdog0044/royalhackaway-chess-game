# Project Name: Checkmate Dungeon (Working Title)

## 1. Executive Summary
A browser-based Roguelite Chess game where the player battles through procedural stages against an "Evil AI" (Gemini LLM). The game features RPG progression, jagged procedural boards, and retro aesthetics, backed by a robust Java server.

## 2. Technology Stack
* **Frontend:** Vanilla JavaScript (ES6+) or Lightweight Framework (e.g., Svelte/Vue) for rendering.
* **Backend:** Java (Spring Boot or Jakarta EE).
* **AI Integration:** Google Gemini API.
* **Audio:** Spotify Web API.
* **Database (Optional):** H2 or SQLite for session persistence during runs.

## 3. Core Gameplay Mechanics

### 3.1. The Board (Jagged Grid)
* **Base Structure:** A square grid system ($N \times N$).
* **Procedural Generation:**
    * The board must be "jagged," meaning random squares are removed (void tiles) to create irregular shapes.
    * **Constraint:** The board must remain a single connected component (verified via BFS/Flood Fill algorithm) to ensure all valid tiles are reachable.
* **Scaling:**
    * Stage 1: $6 \times 6$ base.
    * Stage 2+: Increase dimension ($N+1$) or reduce void tile density to expand play area.

### 3.2. Pieces & Movement
* **Archetypes:** Standard chess movement serves as the base, modified by "DND" classes (see `SKILLS.md`).
* **Attacking:**
    * Primary attack method is **Capture** (moving into an occupied square).
    * Pieces have defined **Attack Ranges** (e.g., Ranged units can capture without moving into the square, Melee must move).
* **Configuration:**
    * **Player:** Starts with a selection of available pieces.
    * **Enemy:** Starts with a "Boss" piece and minions.
    * **Placement:** Randomized starting positions on valid tiles at opposite ends of the board.

### 3.3. Progression (The Roguelite Loop)
* **Victory Condition:** Capture ALL of the enemy's pieces.
* **Defeat Condition:** All player pieces are captured.
* **Stage Rewards:**
    * A new random piece on the board.
    * A random powerup from a full list of powerups.
* **Inventory:**
    * Between stages, the player enters a "Camp" screen.
    * Power-ups can be swapped between pieces. Each piece has a single power-up slot.

## 4. Backend Requirements (Java)

### 4.1. Game Logic Engine
* **State Management:** The Java backend acts as the source of truth for the board state.
* **Validation:**
    * Validate all player moves (geometry, collision, cooldowns).
    * Calculate Line of Sight (LoS) for ranged attacks on the jagged board.
* **Map Generation:** Implement the algorithm to generate jagged, connected boards and send the JSON representation to the frontend.

### 4.2. AI Integration (Gemini LLM)
* **Persona:** The AI must adopt the persona of an arrogant, evil Dungeon Master.
* **Input:** The backend sends the board state (JSON/FEN-like notation) and valid move list to Gemini.
* **Output:**
    * **Structured Data:** The coordinate of the chosen move.
    * **Taunt:** A text string mocking the player based on the current game state (e.g., "You sacrificed your Knight? How... quaint.") displayed in the UI.

## 5. Frontend Requirements (JavaScript)

### 5.1. Visuals & UI
* **Style:** Retro/Pixel Art (CRT scanline effects optional).
* **Feedback:** Visual indicators for valid moves, attack range, and enemy intent.
* **Animations:** Simple sprites for movement and attack impacts.

### 5.2. Audio (Spotify API)
* **Integration:** Authenticate via Spotify Web API.
* **Playlist:** Create or target a specific playlist ID featuring **Aphex Twin**.
* **Logic:**
    * On Game Start: Shuffle and Play.
    * On Game Over: Pause or switch track.
    * *Note: Requires User Authentication token handling in JS.*

## 6. API Structure (Draft)
* `GET /api/new-game`: Initializes session, generates Stage 1 board.
* `POST /api/move`: Player sends coordinates. Backend validates, updates state, calls Gemini, and returns new state + AI move + AI taunt.
* `POST /api/camp/equip`: Updates piece loadouts between stages.