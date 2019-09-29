package com.white.stratego.stratego.game.service;

import com.white.stratego.stratego.game.*;
import com.white.stratego.stratego.game.repository.BoardRepository;
import com.white.stratego.stratego.game.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.white.stratego.stratego.market.model.User;

@Service
public class GameService {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    GameRepository gameRepository;

    public Game newGame(User user) {
        Game g = new Game();
        boardRepository.save(g.getInitialBoard());
        boardRepository.save(g.getBoard());
        g.setIf_public(false);
        g.setCreatedBy(user);
        gameRepository.save(g);
        gameStart(g);
        boardRepository.save(g.getInitialBoard());
        boardRepository.save(g.getBoard());
        gameRepository.save(g);
        return g;
    }

    public void gameStart (Game game) {
        BoardSetups tmpSet = new BoardSetups();
        int[] userSet = tmpSet.getSetup(0);
        int[] compSet = tmpSet.getSetup(0);
        game.getBoard().setupBoard(compSet, 'r');
        game.getBoard().setupMiddle();
        game.getBoard().setupBoard(userSet, 'b');
        int[] flagRed = new int[2];
        int[] flagBlue = new int[2];

        // I think that should work as a deep copy for initial board
        game.getInitialBoard().setupBoard(compSet, 'r');
        game.getInitialBoard().setupMiddle();
        game.getInitialBoard().setupBoard(userSet, 'b');


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

    public void processSetupSwap(long id, int x1, int y1, int x2, int y2) {
        Game g = gameRepository.findById(id);
        Board board = g.getBoard();
        Piece[][] pieces = board.getPieces();
        Piece temp = pieces[x1][y1];
        pieces[x1][y1] = pieces[x2][y2];
        pieces[x2][y2] = temp;
        boardRepository.save(board);
    }
}