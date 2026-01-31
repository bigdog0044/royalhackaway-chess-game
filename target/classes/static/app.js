document.addEventListener('DOMContentLoaded', () => {

    // --- Boot Loader & Initial Setup ---
    const loader = document.getElementById('loader');
    const mainContent = document.getElementById('main-content');
    const bootLines = document.querySelectorAll('#boot-text p');
    let lineIndex = 0;

    // Hide all boot lines initially
    bootLines.forEach(line => line.style.display = 'none');

    function showNextLine() {
        if (lineIndex < bootLines.length) {
            bootLines[lineIndex].style.display = 'block';
            lineIndex++;
            setTimeout(showNextLine, 250 + Math.random() * 200); // Show lines at a slightly random pace
        } else {
            // All lines shown, wait a moment then fade out loader
            setTimeout(() => {
                loader.style.transition = 'opacity 0.5s';
                loader.style.opacity = '0';
                loader.addEventListener('transitionend', () => {
                    loader.style.display = 'none';
                    mainContent.style.display = 'flex';
                    
                    newGame();
                         // Start the game after the loader is gone
                });
            }, 500);
        }
    }


    // --- Matrix Rain Effect ---
    const canvas = document.getElementById('matrix-canvas');
    const ctx = canvas.getContext('2d');

    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    const katakana = 'アァカサタナハマヤャラワガザダバパイィキシチニヒミリヰギジヂビピウゥクスツヌフムユュルグズブヅプエェケセテネヘメレヱゲゼデベペオォコソトノホモヨョロヲゴゾドボポヴッン';
    const latin = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    const nums = '0123456789';
    const alphabet = katakana + latin + nums;

    const fontSize = 16;
    const columns = canvas.width / fontSize;
    const rainDrops = [];

    for (let x = 0; x < columns; x++) {
        rainDrops[x] = 1;
    }

    const drawMatrix = () => {
        ctx.fillStyle = 'rgba(0, 0, 0, 0.05)';
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        ctx.fillStyle = '#FFF'; // White text
        ctx.font = fontSize + 'px monospace';

        for (let i = 0; i < rainDrops.length; i++) {
            const text = alphabet.charAt(Math.floor(Math.random() * alphabet.length));
            ctx.fillText(text, i * fontSize, rainDrops[i] * fontSize);

            if (rainDrops[i] * fontSize > canvas.height && Math.random() > 0.975) {
                rainDrops[i] = 0;
            }
            rainDrops[i]++;
        }
    };


    // --- Game Logic ---
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
        if (gameState.isGameOver) {
            statusMessage.textContent = `GAME OVER: ${gameState.winner} WINS!`;
            currentPlayerSpan.textContent = 'N/A';
        } else {
            statusMessage.textContent = gameState.message;
            currentPlayerSpan.textContent = gameState.currentPlayer;
        }

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
                    pieceElement.style.color = piece.color === 'WHITE' ? '#FFFFFF' : '#AAAAAA'; // White for White, Gray for Black
                    square.appendChild(pieceElement);
                }

                if (!gameState.isGameOver) {
                    square.addEventListener('click', () => onSquareClick(r, c, piece));
                }
                boardContainer.appendChild(square);
            }
        }
    }

    async function onSquareClick(row, col, piece) {
        if (!gameId) return;

        const currentGameStateResponse = await fetch(`${API_URL}/game-state/${gameId}`);
        const currentGameState = await currentGameStateResponse.json();
        if (currentGameState.isGameOver) {
            renderBoard(currentGameState); // Re-render to show final state
            return;
        }

        if (selectedSquare) {
            // Second click: attempt to move
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
                const newGameState = await response.json();
                renderBoard(newGameState);
            } catch (error) {
                statusMessage.textContent = error.message;
            } finally {
                selectedSquare = null;
                clearHighlights();
            }
        } else if (piece && piece.color === currentGameState.currentPlayer) {
            // First click: select a piece
            selectedSquare = { row, col };
            highlightSquare(row, col);
        }
    }

    function highlightSquare(row, col) {
        clearHighlights();
        const square = boardContainer.querySelector(`[data-row='${row}'][data-col='${col}']`);
        if (square) square.classList.add('selected');
    }

    function clearHighlights() {
        document.querySelectorAll('.square.selected').forEach(s => s.classList.remove('selected'));
    }

    // --- Initialize ---
    setInterval(drawMatrix, 50); // Start Matrix rain
    // Show splash first; start boot sequence after user interaction to allow audio playback
    const splash = document.getElementById('splash');
    const playButton = document.getElementById('play-button');
    const bgAudio = document.getElementById('bg-audio');

    function startGame() {
        // play audio (user gesture enabled)
        if (bgAudio) {
            bgAudio.play().catch(() => {});
        }
        // hide splash and show loader, then start boot sequence
        if (splash) {
            splash.style.transition = 'opacity 0.25s';
            splash.style.opacity = '0';
            splash.addEventListener('transitionend', () => {
                splash.style.display = 'none';
                loader.style.display = 'block';
                showNextLine();
            }, { once: true });
        } else {
            loader.style.display = 'block';
            showNextLine();
        }
    }

    if (playButton) playButton.addEventListener('click', startGame);
    if (splash) splash.addEventListener('click', (e) => {
        // prevent double-trigger when clicking the button
        if (e.target !== playButton) startGame();
    });

});
