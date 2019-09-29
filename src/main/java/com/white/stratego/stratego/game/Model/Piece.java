package com.white.stratego.stratego.game.Model;

import java.io.Serializable;

public class Piece implements Comparable<Piece> , Serializable {

    private int rank;
    private boolean visible;
    private boolean isFlag;
    private boolean isBomb;
    private boolean movable;


    /**
     * if the user choose put chess by themselves
     * or implement a random method for user choose random mode later
     */
    // Empty Piece
    public Piece(){
        this.rank = 0;
        this.visible = false;
        this.movable = false;
        this.isBomb = false;
        this.isFlag = false;
    }

    public Piece(int rank, boolean visible, boolean movable, boolean isBomb, boolean isFlag){
        this.rank = rank;
        this.visible = visible;
        this.movable = movable;
        this.isBomb = isBomb;
        this.isFlag = isFlag;
    }

    public Piece(boolean visible, boolean isFlag){
        this.rank = 0;
        this.visible = visible;
        this.isFlag = isFlag;
    }

    public Piece(boolean visible, boolean isBomb, boolean isFlag){
        this.rank = 0;
        this.visible = visible;
        this.isFlag = isFlag;
        this.isBomb = isBomb;
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



    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String skill(){
        String skill = "";
        switch(Math.abs(rank)){
            case 0:
                skill = "Empty field.";
                break;
            case 1:
                skill = "Spy(1). He can capture Marshal(10) if attacks first.";
                break;
            case 2:
                skill = "Scout(2). Can move an unlimited distance and attack on the same turn.";
                break;
            case 3:
                skill = "Miners(3). Can disarm Bomb pieces.";
                break;
            case 4:
                skill = "Sergeant(4).";
                break;
            case 5:
                skill = "Lieutenant(5).";
                break;
            case 6:
                skill = "Captain(6).";
                break;
            case 7:
                skill = "Majors(7).";
                break;
            case 8:
                skill = "Colonels(8).";
                break;
            case 9:
                skill = "General(9).";
                break;
            case 10:
                skill = "Marshall(10).";
                break;
            case 11:
                skill = "Flag.";
                break;
            case 13:
                skill = "Bomb.";
                break;
        }
        return skill;
    }

    public int attack(Piece o){
        System.out.printf("%d attacks %d\n",this.rank,o.rank);
        if(!(this.isFlag || this.isBomb)) {
            // if current player attacks a bomb - current player wins,
            // return +/-11 will be a signal of the end of the game
            if (Math.abs(o.rank) == 11) {
//                int w = this.rank > 0 ? 11 : -11;
                return 1;
            } else if (Math.abs(this.rank) == 1 && Math.abs(o.rank) == 10) {
                return 1;
            } else if (Math.abs(this.rank) == 3 && Math.abs(o.rank) == 13) {
                return 1;
            }
            int fight = compareTo(o);
            if (fight == 0) {
                this.setVisible(true);
                o.setVisible(true);
                return 0;
            } else if (fight > 0) {
                // attacker won the fight
                this.setVisible(true);
                return 1;
            } else {
                // attacker lost the fight
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

    public Piece clone() {
        Piece p = new Piece();
        p.setRank(this.rank);
        p.setVisible(this.visible);
        p.setIsFlag(this.isFlag);
        p.setIsBomb(this.isBomb);
        p.setMovable(this.movable);
        return p;
    }
}
