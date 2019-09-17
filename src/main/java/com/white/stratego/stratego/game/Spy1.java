public class Spy1 extends Piece {

    /**
     * if the user choose put chess by themselves
     * or implement a random method for user choose random mode later
     *
     * @param rank
     */
    public Spy1(int rank, boolean visible, boolean dead, boolean moveable, boolean isBomb, boolean isFlag) {
        super(rank, visible, dead, moveable, isBomb, isFlag);
    }

    /**
     * spy can attack Marshal
     */
    public void skill(){

    }
}
