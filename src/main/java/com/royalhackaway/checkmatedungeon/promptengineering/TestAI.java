package com.royalhackaway.checkmatedungeon.promptengineering;

import com.royalhackaway.checkmatedungeon.model.Board;

public class TestAI {
    public static void main(String[] args) {
        Board board = new Board(8);
        board.setupStandardBoard();
        AICommunication aicoms = new AICommunication();
        aicoms.promptProcessing(board);
    }
}
