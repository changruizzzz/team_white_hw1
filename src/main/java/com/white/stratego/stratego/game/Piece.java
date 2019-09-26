package com.white.stratego.stratego.game;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class Piece implements Comparable<Piece> , Serializable {

    private int rank;
    private boolean visible;
    private int x;
    private int y;
    private boolean isFlag;
    private boolean isBomb;
    private boolean dead;
    private boolean movable;


    /**
     * if the user choose put chess by themselves
     * or implement a random method for user choose random mode later
     */
    // Empty Piece
    public Piece(){
        this.rank = 0;
        this.visible = false;
        x = -1;
        y = -1;
        this.dead = false;
        this.movable = false;
        this.isBomb = false;
        this.isFlag = false;
    }

    public Piece(int rank, boolean visible, boolean dead, boolean moveable, boolean isBomb, boolean isFlag){
        this.rank = rank;
        this.visible = visible;
        x = -1;
        y = -1;
        this.dead = dead;
        this.movable = moveable;
        this.isBomb = isBomb;
        this.isFlag = isFlag;
    }

    public Piece(boolean visible, boolean dead, boolean isFlag){
        this.rank = 0;
        this.visible = visible;
        x = -1;
        y = -1;
        this.dead = dead;
        this.isFlag = isFlag;
    }

    public Piece(boolean visible, boolean dead, boolean isBomb, boolean isFlag){
        this.rank = 0;
        this.visible = visible;
        x = -1;
        y = -1;
        this.dead = dead;
        this.isFlag = isFlag;
        this.isBomb = isBomb;
    }
    public Piece emptyPiece() {
        Piece emptyP = new Piece(0, false, false, false, false, false);
        return emptyP;
    }
    public boolean getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(boolean flag) {
        isFlag = flag;
    }

    public boolean getIsBomb() {
        return isBomb;
    }

    public void setIsBomb(boolean bomb) {
        isBomb = bomb;
    }

    public boolean getMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public int getRank() {
        return rank;
    }

    public boolean getDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }


    public void moveUp(){
        //changeBoard();
    }

    public void moveLeft(){

    }

    public void moveRight(){

    }
    public void moveDown() {

    }

    public void skill(){

    }

    public int attack(Piece o){
        System.out.printf("%d attacks %d\n",this.rank,o.rank);
        if(!(this.isFlag || this.isBomb)) {
            // if current player attacks a bomb - current player wins,
            // return +/-11 will be a signal of the end of the game
            if (Math.abs(o.rank) == 11) {
                int w = this.rank > 0 ? 11 : -11;
                return w;
            } else if (Math.abs(this.rank) == 1 && Math.abs(o.rank) == 10) {
                return 1;
            } else if (Math.abs(this.rank) == 3 && Math.abs(o.rank) == 13) {
                return 1;
            }
            int fight = compareTo(o);
            if (fight == 0) {
                o.setDead(true);
                this.setDead(true);
                this.setVisible(true);
                o.setVisible(true);
                return 0;
            } else if (fight > 0) {
                // attacker won the fight
                this.x = o.x;
                this.y = o.y;
                o.setDead(true);
                this.setVisible(true);
                return 1;
            } else {
                // attacker lost the fight
                this.setDead(true);
                o.setVisible(true);
                return -1;
            }
        }
        return 0;
    }

    //   @Override
//    public int compareTo(Piece o) {
//        return this.rank - o.rank;
//    }
    // if rank of comp is negative and player is positive - compare must be written like this
    @Override
    public int compareTo(Piece o) {
        return Math.abs(this.rank) - Math.abs(o.rank);
    }
    public String topLine() {
        String hum_com = this.rank > 0 ? " " : this.rank == 0 ? " " : "-";
        int r = Math.abs(this.rank);
        String rank = r == 0 ? "  " : r < 10 ?  (r + " ") : r == 10 ? "10" : r == 11 ? "F " : r == 13 ? "B " : "  ";
        String top = " " + hum_com + rank+" ";
        return top;
    }
    public String bottomLine() {
        //make variable that shows if the piece has been moved
        String v = this.visible ? "v" : " ";
        String m = this.movable ? "m" : " ";
        String bottom = " " + v + m + "  ";
        return bottom;
    }
    @Override
    public String toString() {
        return topLine() + "\n" + bottomLine();
    }
}
