# GAMEFLOW
## Game Created
    - **Initial Board Setup**: A 6x6 grid. The player starts with 6 pieces at specific positions, and the AI opponent starts with 6 pieces mirroring the player's setup.
        - **Player Pieces**:
            - Rook: (0,0), (0,5) - Moves horizontally or vertically any number of squares.
            - Knight: (0,1), (0,4) - Moves in an 'L' shape (two squares in one direction, then one square perpendicularly).
            - Bishop: (0,2), (0,3) - Moves diagonally any number of squares.
        - **AI Pieces**: Mirrored starting positions and piece types on the opposite side of the board.
    - **Piece Movement**: Standard chess movements apply for each piece type. Capturing occurs when a piece moves to a square occupied by an opponent's piece, removing the opponent's piece.
    - **Ante System**:
        - Consists of 8 antes, each with 4 stages.
        - Stages 1-3 are standard combat encounters against AI.
        - Stage 4 of each ante is a "Boss Battle" with increased difficulty.
        - Completing the 8th ante results in a GAME WON state.
    - **Board Growth**: At the start of each new ante, the board dynamically grows by one row and one column. This growth adds new empty squares, making the battlefield larger and changing strategic possibilities.
## In Game
### Player Turn
- The player has two primary choices per turn:
    1.  **Move a Piece**:
        - Select one of their available pieces.
        - Choose a valid destination square according to the piece's movement rules.
        - If the destination square is occupied by an enemy piece, the enemy piece is captured and removed from the board.
        - Movement must result in a legal board state (e.g., King not in check after the move, unless it's a capturing move that resolves a check).
    2.  **Activate a Piece's Power-up**:
        - Select an available piece with an active power-up.
        - Power-ups have cooldowns; a power-up must not be on cooldown to be activated.
        - Power-up effects vary (e.g., extra move, temporary shield, area-of-effect attack, piece transformation). Specific power-up details are described below.
        - Activating a power-up counts as the player's action for the turn.
### Enemy Turn
- After the player's action, the AI opponent takes its turn.
- The AI also has the same two choices:
    1.  **Move a Piece**: The AI selects one of its pieces and moves it according to its programmed logic to attack player pieces, defend its own, or achieve strategic board positions.
    2.  **Activate a Piece's Power-up**: If an AI piece has an available power-up, the AI may choose to activate it based on its tactical assessment.
- **AI Difficulty**: The AI's decision-making (piece selection, move choice, power-up usage) scales with the ante level, becoming more strategic and aggressive in later antes.
## Between Round (Rewards)
- At the end of each stage (except Boss Battles), the player is presented with a choice of rewards:
    1.  **A New Piece**: The player can add a new piece to their reserve.
        -   **Piece Types**: A standard chess piece (Rook, Knight, Bishop)
    2.  **A New Spell/Power-Up from a Small Selection**: The player is offered to choose from on of 2-3 random spells/power-ups.
        -   **Acquisition**:
            -   If a *brand new spell* (one the player doesn't currently possess) is selected:
                -   If the player has an available spell slot, the new spell fills that slot.
                -   If all spell slots are full, the player *must sacrifice an existing spell/power-up* to make room for the new one. The sacrificed spell is permanently removed.
            -   If an *existing spell* (one the player already possesses) is selected:
                -   Its cooldown is permanently reduced by a certain number of turns (e.g., 1 turn) or its effectiveness is increased.
            - There are two slots for *each piece category* as each powerup is compatible with a single type of piece (rook, bishop or queen)
- **Spell Slots**: Players start with a limited number of spell slots (e.g., 3). More slots might be unlocked through special rewards or progression.
## Boss Round
- **Difficulty**: Boss Rounds are significantly harder than regular stages. They feature more formidable enemy setups, often including unique AI behaviors, stronger pieces, or pieces with activated power-ups from the start.
- **Rewards**: Winning a Boss Round yields enhanced rewards in addition to the standard between-round prizes:
    1.  **Legendary Spells**: Exclusive, powerful spells with game-changing effects (e.g., "Board Reset," "Summon Pawn Army," "Impenetrable Shield for 3 Turns"). These may have longer cooldowns or unique activation conditions.
    2.  **Range Improvement on a Piece**: Select an existing piece and permanently increase its movement range or affected area for its power-up. For example, a Rook could move an extra square, or a Bishop's power-up could affect an additional diagonal.
    3.  **A Queen**: Automatically adds a Queen piece to the player's reserve. This is a highly valuable reward due to the Queen's versatile movement capabilities. If the player already has a Queen, they might instead receive an upgrade to an existing Queen's power-up or a unique Queen-specific legendary spell.

## Win Conditions
- **Campaign Victory**: Successfully completing all 4 stages of Ante 8. This means defeating the final Boss in Ante 8.