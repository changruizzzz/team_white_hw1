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
    public void setupBoard(int[] setup, char side_color) {
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
    }
    // setup empty middle pieces
    public void setupMiddle() {
        for (int i = 4; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Piece();
            }
        }
    }
    private void clearBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Piece(0, false, false, true, false, false);
            }
        }
    }

    private void makeMove(Piece p, char direction, int i) {
        int x1 = p.getX();
        int y1 = p.getY();
        int x2 = -1;
        int y2 = -1;
        switch (direction) {
            case 'u':
                x2 = x1;
                y2 = y1 + i;
                break;
            case 'd':
                x2 = x1;
                y2 = y1 - i;
                break;
            case 'l':
                x2 = x1 - i;
                y2 = y1;
                break;
            case 'r':
                x2 = x1 + i;
                y2 = y1;
                break;
        }
        if (checkIfOccupied(y2,x2) == false) {
            // swap moving piece with empty space
            Piece tmp = board[x1][y1];
            board[y1][x1] = board[y2][x2];
            board[y2][x2] = tmp;
        } else {
            // make an attack - it will create either one or two extra empty spaces
            // if ranks are equal = 2 empty spaces, otherwise one extra
            board[y1][x1].attack(board[y2][x2]);
            setEmpty(x1,y1);
        }
    }

    private void setEmpty(int x1, int y1) {
        Piece emptyP = new Piece();
        board[y1][x1] = emptyP;
    }

    private boolean checkIfOccupied(int x2, int y2) {
        if (board[y2][x2].getRank() == 0) {
            return false;
        } else {
            return true;
        }
    }

}
