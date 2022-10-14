import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Objects;

public class BoardTest {

    Board testBoard = new Board(3,3);

    @Test
    public void testBoardConstructor(){

        Assertions.assertEquals(3, testBoard.getX(), "X value incorrectly initialised");

    }

    @Test
    public void testCreateBoard() {

        testBoard.createBoard();
        Tile[][] testGameBoard = testBoard.getBoard();
        Assertions.assertEquals(3, testGameBoard.length, "Board size incorrect");

    }

    @Test
    public void testCreateBoard2() {

        testBoard.createBoard();

        Assertions.assertNull(testBoard.getBoard()[0][0].getUpleft(), "Neighbour incorrectly set");
        Assertions.assertNull(testBoard.getBoard()[0][0].getUp(), "Neighbour incorrectly set");
        Assertions.assertNull(testBoard.getBoard()[0][0].getUpright(), "Neighbour incorrectly set");
        Assertions.assertNull(testBoard.getBoard()[0][0].getLeft(), "Neighbour incorrectly set");
        Assertions.assertNotNull(testBoard.getBoard()[0][0].getRight(), "Neighbour incorrectly set");
        Assertions.assertNull(testBoard.getBoard()[0][0].getDownleft(), "Neighbour incorrectly set");
        Assertions.assertNotNull(testBoard.getBoard()[0][0].getDown(), "Neighbour incorrectly set");
        Assertions.assertNotNull(testBoard.getBoard()[0][0].getDownright(), "Neighbour incorrectly set");

    }

    @Test
    public void testCheckValue() {

        Assertions.assertTrue(testBoard.checkValue(2,2), "value that should exist does not exist");
        Assertions.assertFalse(testBoard.checkValue(4,4), "value that shouldn't exist does exist");

    }

    @Test
    public void testHit() {
        //HITTING SAFE TILE

        testBoard.createBoard();
        if(testBoard.getBoard()[2][2].checkBomb()) {
            testBoard.getBoard()[2][2].setBomb();
        }
        Assertions.assertFalse(testBoard.getBoard()[2][2].isClear(), "tile not clear by default");
        testBoard.hit(2,2, false);
        Assertions.assertTrue(testBoard.getBoard()[2][2].isClear(), "tile not cleared after being hit");

    }

    @Test
    public void testHit2() {
        //HITTING BOMB

        testBoard.createBoard();
        if(!testBoard.getBoard()[1][1].checkBomb()) {
            testBoard.getBoard()[1][1].setBomb();
        }
        Assertions.assertTrue(testBoard.getBoard()[1][1].checkBomb(), "Tile should be bomb");
        testBoard.hit(1,1, false);
        Assertions.assertTrue(testBoard.getGameOver(), "Hitting bomb should lead to game over");

    }

    @Test
    public void testHit3() {
        //HITTING FLAG

        testBoard.createBoard();
        testBoard.getBoard()[1][1].setFlagged();
        Assertions.assertTrue(testBoard.getBoard()[1][1].isFlagged());
        testBoard.hit(1,1, false);
        Assertions.assertFalse(testBoard.getBoard()[1][1].isClear());

    }

    @Test
    public void testBulk() {
        // No bombs
        testBoard.createBoard();
        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (testBoard.getBoard()[i][j].checkBomb()) {
                    testBoard.getBoard()[i][j].setBomb();
                }
                Assertions.assertFalse(testBoard.getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        testBoard.bulk(1,1);

        Assertions.assertTrue(testBoard.getBoard()[0][0].isClear(), "tile should be cleared in the bulk");
        Assertions.assertTrue(testBoard.getBoard()[0][1].isClear(), "tile should be cleared in the bulk");
        Assertions.assertTrue(testBoard.getBoard()[0][2].isClear(), "tile should be cleared in the bulk");

        Assertions.assertTrue(testBoard.getBoard()[1][0].isClear(), "tile should be cleared in the bulk");
        Assertions.assertTrue(testBoard.getBoard()[1][1].isClear(), "tile should be cleared in the bulk");
        Assertions.assertTrue(testBoard.getBoard()[1][2].isClear(), "tile should be cleared in the bulk");

        Assertions.assertTrue(testBoard.getBoard()[2][0].isClear(), "tile should be cleared in the bulk");
        Assertions.assertTrue(testBoard.getBoard()[2][1].isClear(), "tile should be cleared in the bulk");
        Assertions.assertTrue(testBoard.getBoard()[2][2].isClear(), "tile should be cleared in the bulk");
    }

    @Test
    public void testBulk2() {
        // No bombs
        testBoard.createBoard();
        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (testBoard.getBoard()[i][j].checkBomb()) {
                    testBoard.getBoard()[i][j].setBomb();
                }
                Assertions.assertFalse(testBoard.getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        testBoard.setFlag(testBoard.getBoard()[0][0]);
        testBoard.setFlag(testBoard.getBoard()[0][1]);
        testBoard.setFlag(testBoard.getBoard()[0][2]);
        testBoard.setFlag(testBoard.getBoard()[1][0]);
        testBoard.setFlag(testBoard.getBoard()[1][1]);
        testBoard.setFlag(testBoard.getBoard()[1][2]);
        testBoard.setFlag(testBoard.getBoard()[2][0]);
        testBoard.setFlag(testBoard.getBoard()[2][1]);
        testBoard.setFlag(testBoard.getBoard()[2][2]);

        testBoard.bulk(1,1);

        Assertions.assertFalse(testBoard.getBoard()[0][0].isClear(), "tile should be cleared in the bulk");
        Assertions.assertFalse(testBoard.getBoard()[0][1].isClear(), "tile should be cleared in the bulk");
        Assertions.assertFalse(testBoard.getBoard()[0][2].isClear(), "tile should be cleared in the bulk");

        Assertions.assertFalse(testBoard.getBoard()[1][0].isClear(), "tile should be cleared in the bulk");
        Assertions.assertFalse(testBoard.getBoard()[1][1].isClear(), "tile should be cleared in the bulk");
        Assertions.assertFalse(testBoard.getBoard()[1][2].isClear(), "tile should be cleared in the bulk");

        Assertions.assertFalse(testBoard.getBoard()[2][0].isClear(), "tile should be cleared in the bulk");
        Assertions.assertFalse(testBoard.getBoard()[2][1].isClear(), "tile should be cleared in the bulk");
        Assertions.assertFalse(testBoard.getBoard()[2][2].isClear(), "tile should be cleared in the bulk");
    }

    @Test
    public void testBulk3() {
        // with bombs
        testBoard.createBoard();
        Assertions.assertFalse(testBoard.getGameOver());
        testBoard.bulk(1,1);
        Assertions.assertTrue(testBoard.getGameOver());

    }

    @Test
    public void testBulk4() {
        // with bombs
        testBoard.createBoard();
        testBoard.setFlag(testBoard.getBoard()[0][0]);
        testBoard.bulk(1,1);
        Assertions.assertFalse(testBoard.getBoard()[0][0].isClear());

    }

    @Test
    public void testAutoClear() {
        // clearing a board with no bombs

        testBoard.createBoard();
        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (testBoard.getBoard()[i][j].checkBomb()) {
                    testBoard.getBoard()[i][j].setBomb();
                }
                testBoard.getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(testBoard.getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        testBoard.hit(1,1,false);
        Assertions.assertTrue(testBoard.getBoard()[0][0].isClear(),"tile should have been auto cleared");

    }

    @Test
    public void testAutoClear2() {
        // clearing a board with flag in top left

        testBoard.createBoard();
        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (testBoard.getBoard()[i][j].checkBomb()) {
                    testBoard.getBoard()[i][j].setBomb();
                }
                testBoard.getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(testBoard.getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        testBoard.getBoard()[0][0].setFlagged();
        testBoard.hit(1,1,false);
        Assertions.assertFalse(testBoard.getBoard()[0][0].isClear(),"tile should not have been auto cleared");

    }

    @Test
    public void testAutoClear3() {
        // if bomb is in top left and hitting bottom middle
        // B 1 0
        // 1 1 0
        // 0 0 0

        testBoard.createBoard();
        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (testBoard.getBoard()[i][j].checkBomb()) {
                    testBoard.getBoard()[i][j].setBomb();
                }
                testBoard.getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(testBoard.getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        testBoard.setBomb(testBoard.getBoard()[0][0]);
        testBoard.hit(1,2,false);
        Assertions.assertFalse(testBoard.getBoard()[0][0].isClear(),"tile should not have been auto cleared");
        Assertions.assertTrue(testBoard.getBoard()[0][1].isClear(),"tile should have been auto cleared");
    }

    @Test
    public void testAutoClear4() {
        // if bomb is in top left and hitting middle
        // B 1 0
        // 1 1 0
        // 0 0 0

        testBoard.createBoard();
        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (testBoard.getBoard()[i][j].checkBomb()) {
                    testBoard.getBoard()[i][j].setBomb();
                }
                testBoard.getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(testBoard.getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        testBoard.setBomb(testBoard.getBoard()[0][0]);
        testBoard.hit(1,1,false);
        Assertions.assertFalse(testBoard.getBoard()[0][0].isClear(),"tile should not have been auto cleared");
        Assertions.assertFalse(testBoard.getBoard()[0][1].isClear(),"tile should have been auto cleared");
    }

    @Test
    public void testSetBomb() {
        //Creates a board and clears all bombs

        testBoard.createBoard();

        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (testBoard.getBoard()[i][j].checkBomb()) {
                    testBoard.getBoard()[i][j].setBomb();
                }
                testBoard.getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(testBoard.getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        //Setting a bomb in middle of board

        testBoard.setBomb(testBoard.getBoard()[1][1]);
        Assertions.assertEquals(1, testBoard.getBoard()[0][0].getTouching());
    }

    @Test
    public void testSetBomb2() {
        //Creates a board and clears all bombs

        testBoard.createBoard();

        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (testBoard.getBoard()[i][j].checkBomb()) {
                    testBoard.getBoard()[i][j].setBomb();
                }
                testBoard.getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(testBoard.getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        //Setting a bomb in middle of board

        testBoard.setBomb(testBoard.getBoard()[0][0]);
        Assertions.assertEquals(0, testBoard.getBoard()[0][0].getTouching());
        Assertions.assertEquals(1, testBoard.getBoard()[1][0].getTouching());
        Assertions.assertEquals(0, testBoard.getBoard()[2][2].getTouching());
    }

    @Test
    public void testSetFlag() {
        testBoard.createBoard();

        Assertions.assertFalse(testBoard.getBoard()[0][0].isFlagged(), "tile should not be flagged");
        testBoard.getBoard()[0][0].setFlagged();
        Assertions.assertTrue(testBoard.getBoard()[0][0].isFlagged(), "tile should be flagged");
        testBoard.getBoard()[0][0].setFlagged();
        Assertions.assertFalse(testBoard.getBoard()[0][0].isFlagged(), "tile should not be flagged");
    }


    @Test
    public void testSetFlag2() {
        testBoard.createBoard();
        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (testBoard.getBoard()[i][j].checkBomb()) {
                    testBoard.getBoard()[i][j].setBomb();
                }
                testBoard.getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(testBoard.getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }
        testBoard.hit(0,0, false);
        testBoard.setFlag(testBoard.getBoard()[0][0]);
        Assertions.assertFalse(testBoard.getBoard()[0][0].isFlagged());
    }
    @Test
    public void testCheckVictory() {

        testBoard.createBoard();

        //Removes all bombs
        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (testBoard.getBoard()[i][j].checkBomb()) {
                    testBoard.getBoard()[i][j].setBomb();
                }
                testBoard.getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(testBoard.getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        testBoard.setBomb(testBoard.getBoard()[0][0]);
        testBoard.setBomb(testBoard.getBoard()[0][1]);
        Assertions.assertFalse(testBoard.getGameOver(), "Game should not be over");

        testBoard.setFlag(testBoard.getBoard()[0][0]);
        testBoard.checkVictory();
        Assertions.assertFalse(testBoard.getGameOver(), "game should not be over as only 1 bomb flagged");

        testBoard.setFlag(testBoard.getBoard()[0][1]);
        testBoard.checkVictory();
        Assertions.assertTrue(testBoard.getGameOver(), "game should be over as both bombs flagged");

    }

    @Test
    public void testFlagAllBombs() {

        testBoard.createBoard();
        //Removes all bombs
        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (testBoard.getBoard()[i][j].checkBomb()) {
                    testBoard.getBoard()[i][j].setBomb();
                }
                testBoard.getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(testBoard.getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        testBoard.setBomb(testBoard.getBoard()[0][0]);
        testBoard.setBomb(testBoard.getBoard()[2][2]);

        Assertions.assertEquals(".", testBoard.getBoard()[0][0].getValue(), "Value should not have been set");
        Assertions.assertEquals(".", testBoard.getBoard()[0][0].getValue(), "Value should not have been set");


        testBoard.flagAllBombs();

        Assertions.assertEquals("X", testBoard.getBoard()[0][0].getValue(), "Value should've been set");
        Assertions.assertEquals("X", testBoard.getBoard()[2][2].getValue(), "Value should've been set");


    }



}
