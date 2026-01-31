document.addEventListener('DOMContentLoaded', () => {
    const boardContainer = document.getElementById('board-container');
    const statusMessage = document.getElementById('status-message');
    const currentPlayerSpan = document.getElementById('current-player');

    let gameId = null;
    let selectedSquare = null;

    const API_URL = '/api';

    async function newGame() {
        try {
            const response = await fetch(`${API_URL}/new-game`, { method: 'POST' });
            if (!response.ok) throw new Error('Failed to start new game');
            const gameState = await response.json();
            gameId = gameState.gameId;
            renderBoard(gameState);
        } catch (error) {
            statusMessage.textContent = error.message;
        }
    }

    function renderBoard(gameState) {
        boardContainer.innerHTML = '';
        statusMessage.textContent = gameState.message;
        currentPlayerSpan.textContent = gameState.currentPlayer;

        for (let r = 0; r < gameState.board.length; r++) {
            for (let c = 0; c < gameState.board[r].length; c++) {
                const square = document.createElement('div');
                square.classList.add('square');
                square.classList.add((r + c) % 2 === 0 ? 'light' : 'dark');
                square.dataset.row = r;
                square.dataset.col = c;

                const piece = gameState.board[r][c];
                if (piece) {
                    const pieceElement = document.createElement('span');
                    pieceElement.classList.add('piece');
                    pieceElement.textContent = piece.symbol;
                    pieceElement.style.color = piece.color === 'WHITE' ? '#fff' : '#000';
                    square.appendChild(pieceElement);
                }

                square.addEventListener('click', () => onSquareClick(r, c, piece));
                boardContainer.appendChild(square);
            }
        }
    }

    async function onSquareClick(row, col, piece) {
        if (!gameId) return;

        if (selectedSquare) {
            // This is the second click (destination)
            const move = {
                from: { row: selectedSquare.row, col: selectedSquare.col },
                to: { row, col }
            };

            try {
                const response = await fetch(`${API_URL}/move/${gameId}`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(move)
                });
                if (!response.ok) throw new Error('Invalid move');
                const gameState = await response.json();
                renderBoard(gameState);
            } catch (error) {
                statusMessage.textContent = error.message;
            } finally {
                selectedSquare = null;
                clearHighlights();
            }
        } else if (piece) {
            // This is the first click (selecting a piece)
            selectedSquare = { row, col };
            highlightSquare(row, col);
        }
    }

    function highlightSquare(row, col) {
        clearHighlights();
        const square = boardContainer.querySelector(`[data-row='${row}'][data-col='${col}']`);
        if (square) {
            square.classList.add('selected');
        }
    }

    function clearHighlights() {
        document.querySelectorAll('.square.selected').forEach(s => s.classList.remove('selected'));
    }

    // Start a new game on page load
    newGame();
});
