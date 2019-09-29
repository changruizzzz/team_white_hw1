package com.white.stratego.stratego.game;

public class Movement {
   int[] xy1;
   int[] xy2;

    public int[] getXy1() {
        return xy1;
    }

    public void setXy1(int x1, int y1) {
        xy1[0] = x1;
        xy1[1] = y1;
    }

    public int[] getXy2() {
        return xy2;
    }

    public void setXy2(int x2, int y2) {
        xy2[0] = x2;
        xy2[1] = y2;
    }

    public Movement() {
        xy1 = new int[2];
        xy2 = new int[2];
        xy1[0] = -1;
        xy1[1] = -1;
        xy2[0] = -1;
        xy2[1] = -1;
    }
   public Movement(int x, int y, int x_end, int y_end) {
       xy1 = new int[2];
       xy2 = new int[2];
       xy1[0] = x;
       xy1[1] = y;
       xy2[0] = x_end;
       xy2[1] = y_end;
   }
   public int[] moveLeft(int x, int y) {
       xy2[0] = x;
       xy2[1] = y - 1;
       return xy2;
   }

    public int[] moveRight(int x, int y) {
        xy2[0] = x;
        xy2[1] = y + 1;
        return xy2;
    }

    public int[] moveUp(int x, int y) {
        xy2[0] = x - 1;
        xy2[1] = y;
        return xy2;
    }

    public int[] moveDown(int x, int y) {
        xy2[0] = x + 1;
        xy2[1] = y;
        return xy2;
    }

}
