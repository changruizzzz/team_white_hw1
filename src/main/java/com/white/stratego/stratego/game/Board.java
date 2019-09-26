package com.white.stratego.stratego.game;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.Math;

@Entity(name="board")
public class Board {

    private Piece[][] board = new Piece[10][10];

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Board() {
        clearBoard();
    }

    public long getId() {
        return id;
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public void setId(long id) {
        this.id = id;
    }

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
        int s = 0;
        switch (side_color) {
            case 'r':
                start = 3;
                System.out.println("Setting up red");
                for (int i = start; i > start - 4; i--) {
                    for (int j = 9; j > -1; j--) {
                        boolean moveable = true;
                        boolean isBomb = false;
                        boolean isFlag = false;

                        if (setup[s] == 11) {
                            moveable = false;
                            isBomb = true;
                        } else if (setup[s] == 13) {
                            moveable = false;
                            isFlag = true;
                        }
                        Piece newPiece = new Piece(setup[s]*(-1), false, false, moveable, isBomb, isFlag);
                        // set x and y for the Piece
                        newPiece.setX(i);
                        newPiece.setY(j);

                        board[i][j] = newPiece;
                        s++;
                    }
                }
                break;
            case 'b':
                start = 6;
                System.out.println("Setting up blue");
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
                        newPiece.setX(i);
                        newPiece.setY(j);

                        board[i][j] = newPiece;
                        s++;
                    }
                }
                break;
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
    // call that function before setting up the board
    public void clearBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = new Piece();
            }
        }
    }

    //    public void makeMove(Piece p, char direction, int i) {
//        int x1 = p.getX();
//        int y1 = p.getY();
//        int x2 = -1;
//        int y2 = -1;
//        switch (direction) {
//            case 'u':
//                x2 = x1;
//                y2 = y1 + i;
//                break;
//            case 'd':
//                x2 = x1;
//                y2 = y1 - i;
//                break;
//            case 'l':
//                x2 = x1 - i;
//                y2 = y1;
//                break;
//            case 'r':
//                x2 = x1 + i;
//                y2 = y1;
//                break;
//        }
//        if (isEmpty(x2,y2)) {
//            // swap moving piece with empty space
//            Piece tmp = board[x1][y1];
//            board[x1][y1] = board[x2][y2];
//            board[x2][y2] = tmp;
//        } else {
//            // make an attack - it will create either one or two extra empty spaces
//            // if ranks are equal = 2 empty spaces, otherwise one extra
//            board[x1][y1].attack(board[x2][y2]);
//            setEmpty(x1,y1);
//        }
//    }
    public int makeMove(int x1, int y1, char direction, int i) {
        int x2 = -1;
        int y2 = -1;
        int result = 1;
        switch (direction) {
            case 'l':
                x2 = x1;
                y2 = y1 - i;
                break;
            case 'r':
                x2 = x1;
                y2 = y1 + i;
                break;
            case 'u':
                x2 = x1 - i;
                y2 = y1;
                break;
            case 'd':
                x2 = x1 + i;
                y2 = y1;
                break;
        }
        if (isEmpty(x2,y2)) {
            // swap moving piece with empty space
            Piece tmp = board[x1][y1];
            board[x1][y1] = board[x2][y2];
            board[x2][y2] = tmp;
            board[x2][y2].setXY(x2,y2);
        } else {
            // make an attack - it will create either one or two extra empty spaces
            // if ranks are equal = 2 empty spaces, otherwise one extra
            int fight = board[x1][y1].attack(board[x2][y2]);
            // after fight check the result of comparison and if it is 0 - set both fields to empty
            // if it is positive then human player win
            // if it is negative - computer win the fight and the field is updated accordingly
            switch (fight) {
                case 0:
                    setEmpty(x2,y2);
                    break;
                case 1:
                    board[x2][y2] = board[x1][y1];
                    board[x2][y2].setXY(x2,y2);
                    break;
                case -1:
                    break;
                case 11:
                    result = 11;
                    break;
                case -11:
                    result = -11;
                    break;
            }
            setEmpty(x1,y1);
        }

        return result;
    }


    private void setEmpty(int x1, int y1) {
        Piece emptyP = new Piece();
        emptyP.setXY(x1, y1);
        board[x1][y1] = emptyP;
    }

    private boolean isEmpty(int x2, int y2) {
        return (board[x2][y2].getRank() == 0);
    }

    @Override
    public String toString() {
        String row = "";
        for (int i = 0; i < 10; i++) {
            String row1;
            if (i == 9) {
                row1 = (i+1) + " |";
            } else {
                row1 = (i+1) + "  |";
            }
            String row2 = "   |";
            for (int j = 0; j < 10; j++) {
                if (i > 3 && i < 6 && (j == 2 || j == 6)) {
                    row1 += board[i][j].topLine() + " ";
                } else {
                    row1 += board[i][j].topLine() + "|";
                }
                if (i > 3 && i < 6 && (j == 2 || j == 6)) {
                    row2 += board[i][j].bottomLine() + " ";
                } else {
                    row2 += board[i][j].bottomLine() + "|";
                }
            }
            System.out.println();
            if (i == 4) {
                row += row1 + "\n" + row2 + "\n   .-----.-----.           .-----.-----.           .-----.-----.\n";
            } else {
                row += row1 + "\n" + row2 + "\n   .-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.\n";
            }
        }

        return  "      1     2     3     4     5     6     7     8     9     10 \n"+
                "   ._____._____._____._____._____._____._____._____._____._____.\n" + row;
    }

    public int MakeMoveAI(char side) {
        int r = side == 'r' ? -1 : 1;
        int result = 1;
        char myMove;
        if (r == 1) {
            for (int i = 0; i < 10; i+=1) {
                for (int j = 0; j < 10; j+=1) {
                    Piece myP = board[i][j];
                    if ((myP.getRank() * r > 0) && myP.isMovable()) {
                        String attackFirst = immediateAttack(myP, side);
                        String attack = checkAvailAttack(myP, side);
                        String moves = checkAvailMoves(myP);

                        if (attackFirst.length() != 0) {
                            myMove = attackFirst.charAt((int)(Math.random() * attackFirst.length()));
                            result = makeMove(myP.getX(), myP.getY(), myMove, 1);
                            return result;
                        }
                        if (attack.length() != 0) {
                            myMove = attack.charAt((int)(Math.random() * attack.length()));
                            result = makeMove(myP.getX(), myP.getY(), myMove, 1);
                            return result;
                        }
                        if (moves.length() != 0) {
                            int m = (int)(Math.random() * moves.length());
                            myMove = moves.charAt(m);
                            result = makeMove(myP.getX(), myP.getY(), myMove, 1);
                            return result;
                        }
                    }
                }
            }
        } else {
            for (int i = 9; i > 0; i-=1) {
                for (int j = 0; j < 10; j+=1) {
                    Piece myP = board[i][j];
                    if ((myP.getRank() * r > 0) && myP.isMovable()) {
                        String attackFirst = immediateAttack(myP, side);
                        String attack = checkAvailAttack(myP, side);
                        String moves = checkAvailMoves(myP);

                        if (attackFirst.length() != 0) {
                            myMove = attackFirst.charAt((int)(Math.random() * attackFirst.length()));
                            result = makeMove(myP.getX(), myP.getY(), myMove, 1);
                            return result;
                        }
                        if (attack.length() != 0) {
                            myMove = attack.charAt((int)(Math.random() * attack.length()));
                            result = makeMove(myP.getX(), myP.getY(), myMove, 1);
                            return result;
                        }
                        if (moves.length() != 0) {
                            int m = (int)(Math.random() * moves.length());
                            myMove = moves.charAt(m);
                            result = makeMove(myP.getX(), myP.getY(), myMove, 1);
                            return result;
                        }
                    }
                }
            }
        }
        return result;
    }

    // checking neighbors if there are available empty fields to move
    private String checkAvailMoves(Piece p) {
        int x1 = p.getX();
        int y1 = p.getY();
        String moves = "";
        if (x1 != 0 && isEmpty(x1-1,y1)) {
            if (!isLake(x1-1,y1)) {
                moves += "u";
            }
        }
        if (x1 != 9 && isEmpty(x1+1,y1)) {
            if (!isLake(x1+1,y1)) {
                moves += "d";
            }
        }
        if (y1 != 0 && isEmpty(x1,y1-1)) {
            if (!isLake(x1,y1-1)) {
                moves += "l";
            }
        }
        if (y1 != 9 && isEmpty(x1,y1+1)) {
            if (!isLake(x1,y1-1)) {
                moves += "r";
            }
        }
        return moves;
    }

    private boolean isLake(int x, int y) {
        return (((y > 1 && y < 4) || (y > 5 && y < 8)) && (x > 3 && x < 6));
    }

    // checking neighbors if there are unknown enemies pieces
    private String checkAvailAttack(Piece p, char side) {
        int x1 = p.getX();
        int y1 = p.getY();
        int r = side == 'r' ? -1 : 1;
        // checking only invisible neighbors
        String attack = checkNeighbors(x1, y1, r, p, false);

        return attack;
    }
    // checking neighbors if there are visible enemies pieces
    private String immediateAttack(Piece p, char side) {
        int x1 = p.getX();
        int y1 = p.getY();
        int r = side == 'r' ? -1 : 1;
        // checking only visible neighbors
        String attackFirst = checkNeighbors(x1, y1, r, p, true);

        return attackFirst;
    }

    // iterating through left, right, up, down and calling helper function to check fields
    private String checkNeighbors(int x1, int y1, int r, Piece p, boolean visible) {
        String result = "";
        int tmpX = x1-1;
        int tmpY = y1;
        result += attackHelper(x1, 0, tmpX, tmpY, r, p, "u", visible);

        tmpX = x1+1;
        tmpY = y1;
        result += attackHelper(x1, 9, tmpX, tmpY, r, p, "d", visible);

        tmpX = x1;
        tmpY = y1-1;
        result += attackHelper(y1, 0, tmpX, tmpY, r, p, "l", visible);

        tmpX = x1;
        tmpY = y1+1;
        result += attackHelper(y1, 9, tmpX, tmpY, r, p, "r", visible);
        return result;
    }

    public Piece[][] getBoard() {
        return board;
    }

    // helper function to discover pieces available to attack
    private String attackHelper(int coord, int edge, int tmpX, int tmpY, int r, Piece p, String direction, boolean visible) {
        String result = "";
        if (coord != edge && !isEmpty(tmpX,tmpY)) {
            Piece tmpP = board[tmpX][tmpY];
            int rank = tmpP.getRank();
            if ((rank * r < 0) && (tmpP.isVisible() == visible)) {
                if ((visible == true) && (rank < p.getRank()) || ((rank == 10) && (p.getRank() == 1))) {
                    result += direction;
                } else if (visible == false) {
                    result += direction;
                }
            }
        }
        return result;
    }

/*
       ._____._____._____._____._____._____._____._____._____._____.
       |  10 |  9  | -9  | -10 |  8  | -8  |  10 |  10 |  10 |  10 |
       |  vm |  vm |  vm |  v  |  v  |  vm |  v. |  v. |  v. |  v. |
       .-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.
       | 10  |  F  |  10 |     |  10 |  10 |  10 |  10 |  10 |  10 |
       | v.  |  v. |  v. |     |  v. |  v. |  v. |  v. |  v. |  v. |
       .-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.
       | 10  |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |
       | v.  |  v. |  v. |  v. |     |  v. |  v. |  v. |  v. |  v. |
       .-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.
       | 10  |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |
       | v.  |  v. |  v. |  v. |  v. |  v. |  v. |  v. |  v. |  v. |
       .-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.
       | 10  |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |
       | v.  |  v. |  v. |  v. |  v. |  v. |  v. |  v. |  v. |  v. |
       .-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.
       | 10  |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |
       | v.  |  v. |  v. |  v. |  v. |  v. |  v. |  v. |  v. |  v. |
       .-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.
       | 10  |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |
       | v.  |  v. |  v. |  v. |  v. |  v. |  v. |  v. |  v. |  v. |
       .-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.
       | 10  |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |
       | v.  |  v. |  v. |  v. |  v. |  v. |  v. |  v. |  v. |  v. |
       .-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.
       | 10  |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |
       | v.  |  v. |  v. |  v. |  v. |  v. |  v. |  v. |  v. |  v. |
       .-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.
       | 10  |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |  10 |
       | v.  |  v. |  v. |  v. |  v. |  v. |  v. |  v. |  v. |  v. |
       .-----.-----.-----.-----.-----.-----.-----.-----.-----.-----.

*/
}
