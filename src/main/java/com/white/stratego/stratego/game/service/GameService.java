package com.white.stratego.stratego.game.service;

import com.white.stratego.stratego.game.Board;
import com.white.stratego.stratego.game.BoardSetups;
import com.white.stratego.stratego.game.Game;
import com.white.stratego.stratego.game.Piece;
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
        //Please fix this!!
        game.setBoard(game.getBoard());


        System.out.print(game.getBoard());

//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                System.out.printf("Piece %d %d rank = %d", i, j, board.getBoard()[i][j].getRank());
//            }
//        }

        int finish = 0;
        int c = 300;
        System.out.print(game.getBoard());
        while ((!game.getHumanWin() || !game.getCompWin()) && c > 0) {
            if (!game.getHumanTurn()) {
                finish = game.getBoard().MakeMoveAI('b');
                System.out.print(game.getBoard());
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
                if (checkBoardForMovablePieces('b', game) == 0) {
                    System.out.println("Player has no more movable pieces, Computer won the game!");
                    System.exit(0);
                }
                switchTurn(game);
            } else {
                finish = game.getBoard().MakeMoveAI('r');
                System.out.print(game.getBoard());
                switchTurn(game);
                if (checkBoardForMovablePieces('r', game) == 0) {
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
        game.setInitialBoard(game.getBoard());

    }

    private int checkBoardForMovablePieces(char side, Game game) {
        int r = side == 'r' ? -1 : 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Piece currentPiece = game.getBoard().getBoard()[i][j];
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
