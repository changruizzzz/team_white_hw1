package com.white.stratego.stratego.game.Model;

import java.io.Serializable;

public class Movement implements Serializable {

   private int[] coordinate;

   public Movement() {
      this.coordinate = new int[4];
   }

   public Movement(int[] coordinate) {
      this.coordinate = coordinate;
   }

   public Movement(int x1, int y1, int x2, int y2) {
      int[] array = {x1, y1, x2, y2};
      this.coordinate = array;
   }

   public int[] getCoordinate() {
      return coordinate;
   }

   public void setCoordinate(int[] coordinate) {
      this.coordinate = coordinate;
   }

   public int getX1() {
      return coordinate[0];
   }

   public int getY1() {
      return coordinate[1];
   }

   public int getX2() {
      return coordinate[2];
   }

   public int getY2() {
      return coordinate[3];
   }

}
