package com.white.stratego.stratego.game;
import java.util.Scanner;
import com.white.stratego.stratego.market.model.MarketUnit;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "game")
public class Game extends MarketUnit{
    private Board board = new Board();
    boolean humanWin = false;
    boolean compWin = false;
    boolean humanTurn = true;
    public Board initialBoard;
    public Board currentBoard;
    public Board finalBoard;
//    GameUser human = new GameUser();
//    GameUser comp = new GameUser();


    public void gameStart () {
//        board.clearBoard();
        Scanner in = new Scanner(System.in);
        BoardSetups tmpSet = new BoardSetups();
        int[] userSet = tmpSet.getSetup(0);
        int[] compSet = tmpSet.getSetup(0);
        board.setupBoard(compSet, 'r');
        board.setupMiddle();
        board.setupBoard(userSet, 'b');
        initialBoard = board;
        System.out.print(board);

//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                System.out.printf("Piece %d %d rank = %d", i, j, board.getBoard()[i][j].getRank());
//            }
//        }

        int finish = 0;
        int c = 300;
        System.out.print(board);
        while ((humanWin != true || compWin != true) && c > 0) {
            if (humanTurn) {
                finish = board.MakeMoveAI('b');
                System.out.print(board);
//                System.out.println("Please make your move in format <x1 y1 dir step> \n" +
//                        "dir = {'u','d','l','r'} step = 1 or more if it is scout");
//                String s =in.nextLine();
//                String[] coord = s.split("\\s+");
//                if (coord[0] == "e") {
//                    System.exit(0);
//                }
//                int a = board.makeMove(Integer.parseInt(coord[0]) - 1 ,Integer.parseInt(coord[1]) - 1,
//                        coord[2].charAt(0),Integer.parseInt(coord[3]));
//                if (a == 11) {
//                    humanWin = true;
//                } else if (a == -11) {
//                    compWin = true;
//                }
//                System.out.print(board);
                if (checkBoardForMovablePieces('b') == 0) {
                    System.out.println("Player has no more movable pieces, Computer won the game!");
                    System.exit(0);
                }
                switchTurn();
            } else {
                finish = board.MakeMoveAI('r');
                System.out.print(board);
                switchTurn();
                if (checkBoardForMovablePieces('r') == 0) {
                    System.out.println("Computer has no more movable pieces, Player won the game!");
                    System.exit(0);
                }
            }
            if (finish == 11) {
                System.out.println("Player found computer's flag and won the game!");
                System.exit(0);
            } else if (finish == -11) {
                System.out.println("Computer found players's flag and won the game!");
                System.exit(0);
            }
            c--;

        }
    }

    private int checkBoardForMovablePieces(char side) {
        int r = side == 'r' ? -1 : 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Piece currentPiece = board.getBoard()[i][j];
                if (currentPiece.getRank() * r > 0) {
                    if (currentPiece.isMovable()) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    private void switchTurn() {
        humanTurn = !humanTurn;
    }

    public Board getBoard() {
        return board;
    }

}
