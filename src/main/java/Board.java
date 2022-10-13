import java.util.Random;

public class Board {

    private boolean gameOver = false;
    private int bombsFlagged = 0;
    private int tilesCleared = 0;
    private final int x;
    private final int y;
    private Tile[][] gameBoard;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public Board(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void createBoard() {

        this.gameBoard = new Tile[y][x];

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                this.gameBoard[i][j] = new Tile(i, j);
            }
        }

        //For each square, set touching - if null skip
        for(Tile[] row : this.gameBoard){
            for(Tile tile : row){
                int tileX = tile.getX();
                int tileY = tile.getY();

                if (checkValue(tileX-1, tileY-1)) { tile.setUpleft(this.gameBoard[tileX-1][tileY-1]); }
                if (checkValue(tileX, tileY-1)) { tile.setUp(this.gameBoard[tileX][tileY-1]); }
                if (checkValue(tileX+1, tileY-1)) { tile.setUpright(this.gameBoard[tileX+1][tileY-1]); }
                if (checkValue(tileX-1, tileY)) { tile.setLeft(this.gameBoard[tileX-1][tileY]); }
                if (checkValue(tileX+1, tileY)) { tile.setRight(this.gameBoard[tileX+1][tileY]); }
                if (checkValue(tileX-1, tileY+1)) { tile.setDownleft(this.gameBoard[tileX-1][tileY+1]); }
                if (checkValue(tileX, tileY+1)) { tile.setDown(this.gameBoard[tileX][tileY+1]); }
                if (checkValue(tileX+1, tileY+1)) { tile.setDownright(this.gameBoard[tileX+1][tileY+1]); }
            }
        }

        Random randomGenerator = new Random();

        for (int i = 0; i <= ((x * y) / 8); i++) {
            boolean placed;
            do {
                placed = setBomb(this.gameBoard[randomGenerator.nextInt(x - 1)][randomGenerator.nextInt(x - 1)]);
            }
            while (!placed);
        }
    }

    public boolean checkValue(int tileX, int tileY) {
        int max = this.x - 1;
        int min = 0;

        return tileX >= min && tileX <= max && tileY >= min && tileY <= max;
    }

    public void hit(int x, int y, boolean bulk) {
        if(this.gameBoard[x][y].isClear()){
            if(!bulk) {
                System.out.println("Already clear");
            }
        } else if (this.gameBoard[x][y].isFlagged()){
            if(!bulk) {
                System.out.println("Cannot clear a flag");
            }
        } else if (this.gameBoard[x][y].isBomb()){
            this.setGameOver();
        } else if (this.gameBoard[x][y].getTouching() > 0) {
            tilesCleared++;
            this.gameBoard[x][y].setClear();
            checkVictory();
        } else if (this.gameBoard[x][y].getTouching() == 0) {
            this.gameBoard[x][y].setClear();
            tilesCleared++;
            traverseTile(this.gameBoard[x][y]);
            checkVictory();
        }
    }

    public void bulk(int x, int y) {
        if (this.gameBoard[x][y].getUpleft() != null) { this.hit(this.gameBoard[x][y].getUpleft().getX(), this.gameBoard[x][y].getUpleft().getY(), true); }
        if (this.gameBoard[x][y].getUp() != null) { this.hit(this.gameBoard[x][y].getUp().getX(), this.gameBoard[x][y].getUp().getY(), true); }
        if (this.gameBoard[x][y].getUpright() != null) { this.hit(this.gameBoard[x][y].getUpright().getX(), this.gameBoard[x][y].getUpright().getY(), true); }

        if (this.gameBoard[x][y].getLeft() != null) { this.hit(this.gameBoard[x][y].getLeft().getX(), this.gameBoard[x][y].getLeft().getY(), true); }
        if (this.gameBoard[x][y].getRight() != null) { this.hit(this.gameBoard[x][y].getRight().getX(), this.gameBoard[x][y].getRight().getY(), true); }

        if (this.gameBoard[x][y].getDownleft() != null) { this.hit(this.gameBoard[x][y].getDownleft().getX(), this.gameBoard[x][y].getDownleft().getY(), true); }
        if (this.gameBoard[x][y].getDown() != null) { this.hit(this.gameBoard[x][y].getDown().getX(), this.gameBoard[x][y].getDown().getY(), true); }
        if (this.gameBoard[x][y].getDownright() != null) { this.hit(this.gameBoard[x][y].getDownright().getX(), this.gameBoard[x][y].getDownright().getY(), true); }
    }

    public void autoClear(int x, int y) {
        if (!this.gameBoard[x][y].isFlagged() && !this.gameBoard[x][y].checkBomb() && this.gameBoard[x][y].getTouching() > 0) {
            this.gameBoard[x][y].setClear();
            tilesCleared++;
            checkVictory();
        } else if (!this.gameBoard[x][y].isFlagged() && !this.gameBoard[x][y].checkBomb() && this.gameBoard[x][y].getTouching() == 0) {
            this.gameBoard[x][y].setClear();
            tilesCleared++;
            checkVictory();
            traverseTile(this.gameBoard[x][y]);
        }
    }

    public boolean setBomb(Tile target) {

        if(!target.checkBomb()) {

            Tile[] neighbours = target.setBomb();
            for (Tile tile : neighbours) {
                if (tile != null) {
                    tile.addTouching();
                }
            }
            //System.out.println("Bomb placed at : " + (target.getX() + 1) + " " + (target.getY() + 1));
            return true;
        } else {
            return false;
        }
    }

    public void traverseTile(Tile target) {
        if (target.getUpleft() != null) { checkTouching(target.getUpleft()); }
        if (target.getUp() != null) { checkTouching(target.getUp()); }
        if (target.getUpright() != null) { checkTouching(target.getUpright()); }
        if (target.getLeft() != null) { checkTouching(target.getLeft()); }
        if (target.getRight() != null) { checkTouching(target.getRight()); }
        if (target.getDownleft() != null) { checkTouching(target.getDownleft()); }
        if (target.getDown() != null) { checkTouching(target.getDown()); }
        if (target.getDownright() != null) { checkTouching(target.getDownright()); }
    }

    public void checkTouching(Tile target) {
        if(target.checkTile()){
            autoClear(target.getX(), target.getY());
        }
    }

    public void setFlag(Tile target){
        if(!target.isClear()){
            if(!target.isFlagged()){
                target.setFlagged();
                if (target.checkBomb()) {
                    bombsFlagged++;
                    checkVictory();
                }
            } else {
                target.setFlagged();
                if (target.checkBomb()) {
                    bombsFlagged--;
                }
            }
        } else {
            System.out.println("Already cleared");
        }
    }

    public void checkVictory() {
        if(bombsFlagged == ((x*y)/8) + 1 || tilesCleared == ((x*y) - ((x*y)/8)) - 1) {
           this.gameOver = true;
        }
    }

    public void setGameOver(){
        this.gameOver = true;
    }

    public Tile[][] getBoard() {
        return this.gameBoard;
    }

    public int getX() {
        return x;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public int victoryState() {
        if(gameOver && this.bombsFlagged == (x*y)/8 + 1){
            return 1;
        } else if (gameOver && tilesCleared == ((x*y) - ((x*y)/8)) - 1) {
            return 2;
        } else {
            return 3;
        }
    }

    public void printBoard() {

        System.out.println("\n   -------------------------------  ");
        System.out.println("        M I N E S W E E P E R   ");
        System.out.println("   -------------------------------  ");

        //Outputs the top numbering
        System.out.print("\n  ");
        int num = 1;
        do {
            if(num < 10){
                System.out.print(" " + 0 + num);
            } else {
                System.out.print(" " + num);
            }
            num++;
        }
        while(num <= x);

        // Outputs the board (with each row getting a numerical indicator on the side)
        System.out.print("\n");
        num = 1;
        for (Tile[] j : this.gameBoard) {

            if(num < 10){
                System.out.print(0);
            }
            System.out.print(num + "  ");

            //outputting the tile value e.g. number, f, x
            for (Tile i : j) {
                switch (i.getValue()) {
                    case "F" -> System.out.print(ANSI_YELLOW + i.getValue() + "  " + "" + ANSI_RESET);
                    case "X" -> System.out.print(ANSI_RED + i.getValue() + "  " + "" + ANSI_RESET);
                    case "." -> System.out.print(ANSI_GREEN + i.getValue() + "  " + "" + ANSI_RESET);
                    default -> System.out.print(i.getValue() + "  " + "");
                }
            }
            if(num < 10){
                System.out.print(0);
            }
            System.out.print(num);
            num++;
            System.out.print("\n");
        }

        //Outputs the bottom numbering
        System.out.print("  ");
        num = 1;
        do {
            if(num < 10){
                System.out.print(" " + 0 + num);
            } else {
                System.out.print(" " + num);
            }
            num++;
        }
        while(num <= x);
        System.out.print("\n");
    }

    public void flagAllBombs() {
        for(Tile[] row : this.gameBoard) {
            for (Tile tile : row) {
                if (tile.checkBomb()) {
                    tile.setValue("X");
                }
            }
        }

        printBoard();
    }


}
