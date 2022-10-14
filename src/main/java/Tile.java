public class Tile {

    private int x;
    private int y;
    private Tile left;
    private Tile right;
    private Tile up;
    private Tile down;
    private Tile upleft;
    private Tile upright;
    private Tile downleft;
    private Tile downright;

    private boolean isBomb;
    private boolean isFlagged = false;
    private boolean isClear;
    private int touching;
    private String value;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.value = ".";
    }

    public void addTouching() {
        touching++;
    }

    public void setTouching(int touching) {
        this.touching = touching;
    }

    public Tile[] setBomb(){
        this.isBomb = !this.isBomb;
        return new Tile[] {upleft, up, upright, left, right, downleft, down, downright};
    }

    public void setLeft(Tile left) {
        this.left = left;
    }

    public void setRight(Tile right) {
        this.right = right;
    }

    public void setUp(Tile up) {
        this.up = up;
    }

    public void setDown(Tile down) {
        this.down = down;
    }

    public void setUpleft(Tile upleft) {
        this.upleft = upleft;
    }

    public void setUpright(Tile upright) {
        this.upright = upright;
    }

    public void setDownleft(Tile downleft) {
        this.downleft = downleft;
    }

    public void setDownright(Tile downright) {
        this.downright = downright;
    }

    public Tile getLeft() {
        return left;
    }

    public Tile getRight() {
        return right;
    }

    public Tile getUp() {
        return up;
    }

    public Tile getDown() {
        return down;
    }

    public Tile getUpleft() {
        return upleft;
    }

    public Tile getUpright() {
        return upright;
    }

    public Tile getDownleft() {
        return downleft;
    }

    public Tile getDownright() {
        return downright;
    }

    public void setFlagged() {
        if(this.isFlagged){
            this.isFlagged = false;
            this.value = ".";
        } else {
            this.isFlagged = true;
            this.value = "F";
        }
    }

    public void setClear(){
        if(this.touching > 0) {
            this.value = String.valueOf(touching);
        } else {
            this.value = " ";
        }
        this.isClear = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTouching() {
        return touching;
    }

    public boolean isBomb() {
        this.value = "X";
        return isBomb;
    }

    public boolean isClear() {
        return isClear;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public String getValue(){
        return value;
    }

    public boolean checkBomb() {
        return isBomb;
    }

    public boolean checkTile() {
        return !isBomb && !isClear;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
