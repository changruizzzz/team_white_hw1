package com.white.stratego.stratego.game.service;

import com.white.stratego.stratego.game.*;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class GameService {


    //    GameUser human = new GameUser();
//    GameUser comp = new GameUser();


    public void gameStart (Game game) {
//        board.clearBoard();
        Scanner in = new Scanner(System.in);
        BoardSetups tmpSet = new BoardSetups();
        int[] userSet = tmpSet.getSetup(0);
        int[] compSet = tmpSet.getSetup(0);
        game.getBoard().setupBoard(compSet, 'r');
        game.getBoard().setupMiddle();
        game.getBoard().setupBoard(userSet, 'b');
        int[] flagRed = new int[2];
        int[] flagBlue = new int[2];
        //Please fix this!!
        game.setBoard(game.getBoard());

        // I think that should work as a deep copy for initial board
        game.getInitialBoard().setupBoard(compSet, 'r');
        game.getInitialBoard().setupMiddle();
        game.getInitialBoard().setupBoard(userSet, 'b');
        // maybe that line will do the same, but I haven't check
//        game.setInitialBoard(game.getBoard());

        // storing the coordinates of the flags in variables,
        // so no need to search and easy to check after each move if flag was captured
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (game.getBoard().getPieces()[i][j].getRank() == 11){
                    flagBlue[0] = i;
                    flagBlue[1] = j;
                } else if (game.getBoard().getPieces()[i][j].getRank() == -11) {
                    flagRed[0] = i;
                    flagRed[1] = j;
                }
            }
        }
        gameFlow(game, flagRed, flagBlue);
    }

    private void gameFlow(Game game, int[] flagRed, int[] flagBlue) {
        Movement move = new Movement();
        int c = 300; // the parameter for speeding the game
        System.out.print(game.getBoard());
        while ((!game.getHumanWin() || !game.getCompWin()) && c > 0) {
            if (!game.getHumanTurn()) {
                // here is the move that you can use for the front end it is in format xy1 = [x1,y1] xy2 = [x2,y2]
                move = game.getBoard().MakeMoveAI('b');
                System.out.print(game.getBoard());

                if (checkBoardForMovablePieces('b', game) == 0) {
                    System.out.println("Player has no more movable pieces, Computer won the game!");
                    System.exit(0);
                }
                switchTurn(game);
            } else {
                // here is the move that you can use for the front end it is in format xy1 = [x1,y1] xy2 = [x2,y2]
                move = game.getBoard().MakeMoveAI('r');
                System.out.print(game.getBoard());
                switchTurn(game);
                if (checkBoardForMovablePieces('r', game) == 0) {
                    System.out.println("Computer has no more movable pieces, Player won the game!");
                    System.exit(0);
                }
            }
            if (move.getXy2()[0] == flagRed[0] && move.getXy2()[1] == flagRed[1]) {
                System.out.println("Player found computer's flag and won the game!");
                System.exit(0);
            } else if (move.getXy2()[0] == flagBlue[0] && move.getXy2()[1] == flagBlue[1]) {
                System.out.println("Computer found players's flag and won the game!");
                System.exit(0);
            }
            c--;
        }
    }

    private int checkBoardForMovablePieces(char side, Game game) {
        int r = side == 'r' ? -1 : 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Piece currentPiece = game.getBoard().getPieces()[i][j];
                if (currentPiece.getRank() * r > 0) {
                    if (currentPiece.getMovable()) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    private void switchTurn(Game game) {
        game.setHumanTurn(game.getHumanWin());
    }
}


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