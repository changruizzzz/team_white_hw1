package com.white.stratego.stratego.game;
import java.util.Scanner;
import com.white.stratego.stratego.market.model.MarketUnit;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="game")
public class Game extends MarketUnit{
    @OneToOne
    private Board board = new Board();
    private boolean humanWin = false;
    private boolean compWin = false;
    private boolean humanTurn = true;
    @OneToOne
    private Board initialBoard;


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
}
