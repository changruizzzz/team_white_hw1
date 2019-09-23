package com.white.stratego.stratego.game;
import java.lang.Math;

public class Board {
    Piece[][] board = new Piece[10][10];

    /**
     * Returns an array of Pieces object that represents the game board.
     * Positive rank will be set for human player and negative for computer
     * Flag will have rank 11 and Bomb will have rank 13
     *
     * @param  setup  an array of int representing the setup for the board
     * @param  side_color blue (human) or red (computer) player represented by character 'b' or 'r'
     * @return      the board setup with Pieces
     */
    public Piece[][] setupBoard(int[] setup, char side_color) {
        int start = 0;
        clearBoard();

        switch (side_color) {
            case 'r':
                start = 0;
                break;
            case 'b':
                start = 6;
                break;
        }
        int s = 0;
        for (int i = start; i < start + 4; i++) {
            for (int j = 0; j < 10; j++) {
                boolean moveable = true;
                boolean isBomb = false;
                boolean isFlag = false;

                if (Math.abs(setup[s]) == 11) {
                    moveable = false;
                    isBomb = true;
                } else if (Math.abs(setup[s]) == 13) {
                    moveable = false;
                    isFlag = true;
                }
                Piece newPiece = new Piece(setup[s], false, false, moveable, isBomb, isFlag);
                // set x and y for the Piece
                newPiece.setX(j);
                newPiece.setY(i);

                board[i][j] = newPiece;
            }
        }
        return board;
    }
    private void clearBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Piece(0, false, false, true, false, false);
            }
        }
    }

}
