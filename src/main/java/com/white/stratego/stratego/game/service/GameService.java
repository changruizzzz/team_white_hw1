package com.white.stratego.stratego.game.service;

import com.white.stratego.stratego.game.*;
import com.white.stratego.stratego.game.Model.*;
import com.white.stratego.stratego.game.Model.MoveResponse;
import com.white.stratego.stratego.game.repository.BoardRepository;
import com.white.stratego.stratego.game.repository.GameRepository;
import com.white.stratego.stratego.game.repository.MoveResponseRepository;
import com.white.stratego.stratego.game.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.white.stratego.stratego.market.model.User;

import java.util.Set;

@Service
public class GameService {

    @Autowired
    StatisticsRepository statisticsRepository;
    @Autowired
    MoveResponseRepository moveResponseRepository;
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

//    private void gameFlow(Game game, int[] flagRed, int[] flagBlue) {
//        Movement move;
//        int c = 300; // the parameter for speeding the game
//        System.out.print(game.getBoard());
//        while ((!game.getHumanWin() || !game.getCompWin()) && c > 0) {
//            if (!game.getHumanTurn()) {
//                // here is the move that you can use for the front end it is in format xy1 = [x1,y1] xy2 = [x2,y2]
//                move = game.getBoard().MakeMoveAI('b');
//                System.out.print(game.getBoard());
//
//                if (checkBoardForMovablePieces('b', game) == 0) {
//                    System.out.println("Player has no more movable pieces, Computer won the game!");
//                    System.exit(0);
//                }
//                switchTurn(game);
//            } else {
//                // here is the move that you can use for the front end it is in format xy1 = [x1,y1] xy2 = [x2,y2]
//                move = game.getBoard().MakeMoveAI('r');
//                System.out.print(game.getBoard());
//                switchTurn(game);
//                if (checkBoardForMovablePieces('r', game) == 0) {
//                    System.out.println("Computer has no more movable pieces, Player won the game!");
//                    System.exit(0);
//                }
//            }
//            if (move.getX2() == flagRed[0] && move.getY2() == flagRed[1]) {
//                System.out.println("Player found computer's flag and won the game!");
//                System.exit(0);
//            } else if (move.getX2() == flagBlue[0] && move.getY2() == flagBlue[1]) {
//                System.out.println("Computer found players's flag and won the game!");
//                System.exit(0);
//            }
//            c--;
//        }
//    }
//
//    private int checkBoardForMovablePieces(char side, Game game) {
//        int r = side == 'r' ? -1 : 1;
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                Piece currentPiece = game.getBoard().getPieces()[i][j];
//                if (currentPiece.getRank() * r > 0) {
//                    if (currentPiece.getMovable()) {
//                        return 1;
//                    }
//                }
//            }
//        }
//        return 0;
//    }
//
//    private void switchTurn(Game game) {
//        game.setHumanTurn(game.getHumanWin());
//    }

    public void processSetupSwap(long id, int x1, int y1, int x2, int y2) {
        System.err.println(x1 + " " + y1 + " " + x2 + " " + y2);
        Game g = gameRepository.findById(id);
        Board board = g.getBoard();
        Piece[][] pieces = board.getPieces();
        Piece temp = pieces[x1][y1];
        pieces[x1][y1] = pieces[x2][y2];
        pieces[x2][y2] = temp;
        boardRepository.save(board);
    }

    public void start(long id) {
        Game g = gameRepository.findById(id);
        g.setStarted(true);
        copyBoard(g.getInitialBoard(), g.getBoard());
        boardRepository.save(g.getInitialBoard());
        gameRepository.save(g);
    }

    public void deleteById(long id) {
        gameRepository.deleteById(id);
    }

    public Set<Game> findByCreatedBy(User user) {
        return gameRepository.findByCreatedBy(user);
    }

    public Game findById(long id) {
        return gameRepository.findById(id);
    }

    public void copyBoard(Board to, Board from) {
        Piece[][] pieces = new Piece[10][10];
        Piece[][] fromPieces = from.getPieces();
        for(int i = 0; i < 10; i ++) {
            for(int j = 0; j < 10; j++) {
                pieces[i][j] = fromPieces[i][j].clone();
            }
        }
        to.setBoard(pieces);
    }

    public MoveResponse processMove(long id, Movement move) {
        Game g = gameRepository.findById(id);
        Board board = g.getBoard();
        MoveResponse response = new MoveResponse();

        int x1 = move.getX1();
        int y1 = move.getY1();
        int x2 = move.getX2();
        int y2 = move.getY2();

        Piece[][] pieces = g.getBoard().getPieces();
        Piece p1 = pieces[x1][y1];
        if(!p1.getMovable()) {
            response.setSuccess(false);
            response.setMessage("This piece can't not be move");
        }
        else if(x1 != x2 && y1 != y2) {
            response.setSuccess(false);
            response.setMessage("You can only move straight.");
        } else {
            int distance = x1 == x2 ? Math.abs(y1 - y2) : Math.abs(x1 - x2);
            if(distance > 1 && p1.getRank() != 2) {

                    response.setSuccess(false);
                    response.setMessage("Only scout can move more than 1 cell.");

            } else {
                if(distance > 1 && !checkPath(pieces, x1, y1, x2, y2)) {
                    response.setSuccess(false);
                    response.setMessage("There is no path.");
                    return response;
                }

                response.setSuccess(true);
                if (board.isEmpty(x2,y2)) {
                    // swap moving piece with empty space
                    Piece tmp = pieces[x1][y1].clone();
                    tmp.setX(x2);
                    tmp.setY(y2);
                    pieces[x2][y2] = tmp;
                    response.setMessage("empty");
                } else {
                    // make an attack - it will create either one or two extra empty spaces
                    // if ranks are equal = 2 empty spaces, otherwise one extra
                    int fight = pieces[x1][y1].attack(pieces[x2][y2]);
                    response.setRank1(pieces[x1][y1].getRank());
                    response.setRank2(pieces[x2][y2].getRank());
                    // after fight check the result of comparison and if it is 0 - set both fields to empty
                    // if it is positive then human player win
                    // if it is negative - computer win the fight and the field is updated accordingly
                    switch (fight) {
                        case 0:
                            board.setEmpty(x2,y2);
                            response.setMessage("draw");
                            break;
                        case 1:
                            if(pieces[x2][y2].getIsFlag()) {
                                g.setEnded(true);
                                response.setGameEnd(true);
                                Statistics stats = statisticsRepository.findByUser(g.getCreatedBy());
                                if(pieces[x2][y2].getRank() < 0) {
                                    g.setHumanWin(true);
                                    g.setCompWin(false);
                                    stats.setWin(stats.getWin() + 1);
                                } else {
                                    g.setCompWin(true);
                                    g.setHumanWin(false);
                                    stats.setLoss(stats.getLoss() + 1);
                                }
                                stats.setTotal(stats.getTotal() + 1);
                                statisticsRepository.save(stats);
                                gameRepository.save(g);
                            } else {
                                response.setGameEnd(false);
                            }

                            pieces[x2][y2] = pieces[x1][y1].clone();
                            pieces[x2][y2].setY(y2);
                            pieces[x2][y2].setX(x2);
                            response.setMessage("win");
                            break;
                        default:
                            response.setMessage("loss");
                    }
                    pieces[x2][y2].setVisible(true);
                }
                board.setEmpty(x1,y1);
                response.setPiece(pieces[x2][y2]);
                response.setX(x1);
                response.setY(y1);
                boardRepository.save(g.getBoard());
                g.getMoves().add(response);
                moveResponseRepository.save(response);

            }
        }
        return response;
    }

    public Movement askMove(long id, char side) {
        Game g = gameRepository.findById(id);
        Board b = g.getBoard();
        return b.MakeMoveAI(side);
    }

    //assume this is straight
    private boolean checkPath(Piece[][] pieces, int x1, int y1, int x2, int y2) {
        int start;
        int end;
        if(x1 == x2) {
            if(x1 == 4 || x1 == 5) {
                return false;
            }
            if(y1 < y2) {
                start = y1 + 1;
                end = y2;
            } else {
                end = y1;
                start = y2 + 1;
            }
            while(start < end) {
                if(pieces[x1][start].getRank() != 0)
                    return false;
                start++;
            }
        } else {
            if(x1 < x2) {
                start = x1;
                end = x2;
            } else {
                end = x1;
                start = x2;
            }
            if(y1 == 2 || y1 == 3 || y1 == 6 || y1 ==7) {
                System.out.println(start +" " +end);
                System.out.println(!(end < 4));
                System.out.println(!(start >6));
                if(!(end < 4) && !(start >6)) {
                    return false;
                }

            }
            while(start + 1 < end) {
                if(pieces[start + 1][y1].getRank() != 0)
                    return false;
                start++;
            }

        }
        return true;
    }
}