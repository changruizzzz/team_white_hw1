package com.white.stratego.stratego.game.Model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.white.stratego.stratego.market.model.MarketUnit;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name="game")
public class Game extends MarketUnit{
    @OneToOne
    private Board board;
    private boolean humanWin;
    private boolean compWin;
    private boolean humanTurn;
    private boolean started;
    private boolean ended;
    @OneToOne
    private Board initialBoard;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoveResponse> moves;
    public Game() {
        board = new Board();
        humanWin = false;
        compWin = false;
        humanTurn = true;
        started = false;
        initialBoard = new Board();
        moves = new ArrayList<>();
        ended = false;
    }
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean getHumanWin() {
        return humanWin;
    }

    public void setHumanWin(boolean humanWin) {
        this.humanWin = humanWin;
    }

    public boolean getCompWin() {
        return compWin;
    }

    public void setCompWin(boolean compWin) {
        this.compWin = compWin;
    }

    public boolean getHumanTurn() {
        return humanTurn;
    }

    public void setHumanTurn(boolean humanTurn) {
        this.humanTurn = humanTurn;
    }

    public Board getInitialBoard() {
        return initialBoard;
    }

    public void setInitialBoard(Board initialBoard) {
        this.initialBoard = initialBoard;
    }

    public boolean getStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean getEnded() {
        return ended;
    }

    public void setEnded(boolean end) {
        this.ended = end;
    }

    public List<MoveResponse> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<MoveResponse> moves) {
        this.moves = moves;
    }
}
