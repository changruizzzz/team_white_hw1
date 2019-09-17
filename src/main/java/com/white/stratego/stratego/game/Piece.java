
public class Piece implements Comparable<Piece> {
    private int rank;
    private boolean visible;
    private int x;
    private int y;
    private boolean isFlag;
    private boolean isBomb;
    private boolean dead;
    private boolean moveable;
    private Movement movement;

    /**
     * if the user choose put chess by themselves
     * or implement a random method for user choose random mode later
     */
    public Piece(int rank, boolean visible, boolean dead, boolean moveable, boolean isBomb, boolean isFlag){
        this.rank = rank;
        this.visible = false;
        x = -1;
        y = -1;
        this.visible = false;
        this.dead = false;
        this.moveable = true;
        this.isBomb = false;
        this.isFlag = false;
    }

    public Piece(boolean visible, boolean dead, boolean isFlag){
        this.rank = rank;
        this.visible = false;
        x = -1;
        y = -1;
        this.visible = false;
        this.dead = false;
        this.moveable = false;
        this.isFlag = true;
        this.isBomb = false;
    }

    public Piece(boolean visible, boolean dead, boolean isBomb, boolean isFlag){
        this.rank = rank;
        this.visible = false;
        x = -1;
        y = -1;
        this.visible = false;
        this.dead = false;
        this.moveable = false;
        this.isFlag = false;
        this.isBomb = true;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public int getRank() {
        return rank;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isVisible() {
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

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public void moveUp(){

    }

    public void moveLeft(){

    }

    public void moveRight(){

    }


    public void skill(){

    }

    public void attack(Piece o){
        if(!(this.isFlag || this.isBomb)) {
            if (compareTo(o) == 0) {
                o.setDead(true);
                this.setDead(true);
                this.setVisible(true);
                o.setVisible(true);
            } else if (compareTo(o) > 0) {
                this.x = o.x;
                this.y = o.y;
                o.setDead(true);
                this.setVisible(true);

            } else {
                o.x = this.x;
                o.y = this.y;
                o.setDead(true);
                o.setVisible(true);
            }
        }
    }

    @Override
    public int compareTo(Piece o) {
        return this.rank - o.rank;
    }
}
