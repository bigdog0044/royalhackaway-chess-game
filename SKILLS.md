# Skills & Classes Definitions

## 1. Class Archetypes (Pieces)
*Movement notation follows standard Chess algebraic concepts.*

| Class | Icon/Sprite | Movement Base | Attack Range | Special Trait |
| :--- | :--- | :--- | :--- | :--- |
| **Paladin (Knight)** | 🛡️ | L-Shape (Jump) | Melee (1) | **Holy Jump:** Ignores "Void" tiles and obstacles. |
| **Ranger (Bishop)** | 🏹 | Diagonal | Ranged (3) | **Snipe:** Captures piece *without* moving to the square. |
| **Berserker (Rook)** | 🪓 | Orthogonal | Melee (1) | **Charge:** Knocks back adjacent enemies if move ends next to them. |
| **Sorcerer (Queen)** | 🔮 | Any Direction | Ranged (2) | **Glass Cannon:** Can move infinitely, but only attack within 2 tiles. |

## 2. Power-Up Slots (Swappable Items)
*These items are drafted between stages and slotted into pieces. Each piece has a single power-up slot.*

### Tier 1 (Common)
* **Boots of Speed:** Increases movement range by 1 tile.
* **Heavy Armor:** This piece cannot be captured by the first attack per stage (1-hit shield).

### Tier 2 (Rare)
* **Scroll of Phasing:** This piece can move through 1 wall/void tile per turn.
* **Necromancer's Gem:** Capturing an enemy turns them into a friendly "Skeleton" (Pawn logic) instead of removing them.
* **Counter-Stance:** If an enemy ends their turn adjacent to this piece, they are immediately captured (Passive).

### Tier 3 (Legendary)
* **The Gemini Amulet:** Once per stage, ask the AI to make the *worst* possible move for the enemy team (The AI must hallucinate a blunder).
* **Chronos Dial:** Undo the last enemy turn.
* **Omni-Slash:** Attack all adjacent squares simultaneously (AoE Capture).