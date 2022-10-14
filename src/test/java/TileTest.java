import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TileTest {

    Tile testTile = new Tile(1, 1);

    @Test
    public void testSetTileLeft() {

        Assertions.assertNull(testTile.getLeft(), "left neighbour initialised incorrectly");
        Tile testNeighbour = new Tile(1,2);
        testTile.setLeft(testNeighbour);
        Assertions.assertEquals(testNeighbour, testTile.getLeft(), "left neighbour not updated");

    }

    @Test
    public void testSetTileRight() {

        Assertions.assertNull(testTile.getRight(), "right neighbour initialised incorrectly");
        Tile testNeighbour = new Tile(1,2);
        testTile.setRight(testNeighbour);
        Assertions.assertEquals(testNeighbour, testTile.getRight(), "right neighbour not updated");

    }

    @Test
    public void testSetTileUp() {

        Assertions.assertNull(testTile.getUp(), "up neighbour initialised incorrectly");
        Tile testNeighbour = new Tile(1,2);
        testTile.setUp(testNeighbour);
        Assertions.assertEquals(testNeighbour, testTile.getUp(), "Up neighbour not updated");

    }

    @Test
    public void testSetTileDown() {

        Assertions.assertNull(testTile.getDown(), "Down neighbour initialised incorrectly");
        Tile testNeighbour = new Tile(1,2);
        testTile.setDown(testNeighbour);
        Assertions.assertEquals(testNeighbour, testTile.getDown(), "Down neighbour not updated");

    }

    @Test
    public void testSetTileUpLeft() {

        Assertions.assertNull(testTile.getUpleft(), "up left neighbour initialised incorrectly");
        Tile testNeighbour = new Tile(1,2);
        testTile.setUpleft(testNeighbour);
        Assertions.assertEquals(testNeighbour, testTile.getUpleft(), "Up left neighbour not updated");

    }

    @Test
    public void testSetTileUpRight() {

        Assertions.assertNull(testTile.getUpright(), "up right neighbour initialised incorrectly");
        Tile testNeighbour = new Tile(1,2);
        testTile.setUpright(testNeighbour);
        Assertions.assertEquals(testNeighbour, testTile.getUpright(), "Up right neighbour not updated");

    }

    @Test
    public void testSetTileDownLeft() {

        Assertions.assertNull(testTile.getDownleft(), "down left neighbour initialised incorrectly");
        Tile testNeighbour = new Tile(1,2);
        testTile.setDownleft(testNeighbour);
        Assertions.assertEquals(testNeighbour, testTile.getDownleft(), "down left neighbour not updated");

    }

    @Test
    public void testSetTileDownRight() {

        Assertions.assertNull(testTile.getDownright(), "up neighbour initialised incorrectly");
        Tile testNeighbour = new Tile(1,2);
        testTile.setDownright(testNeighbour);
        Assertions.assertEquals(testNeighbour, testTile.getDownright(), "Up neighbour not updated");

    }

    /* ---- */

    @Test
    public void testSetFlagged() {

        Assertions.assertFalse(testTile.isFlagged(), "flag incorrectly initialised");
        testTile.setFlagged();
        Assertions.assertTrue(testTile.isFlagged(), "flag incorrectly set");

    }

    @Test
    public void testSetClear() {

        Assertions.assertFalse(testTile.isClear(), "clear state incorrectly initialised");
        testTile.setClear();
        Assertions.assertTrue(testTile.isClear(), "clear state incorrectly set");
    }

    @Test
    public void testTileXY() {

        Assertions.assertEquals(1, testTile.getX(), "Tile x coordinate not set correctly");
        Assertions.assertEquals(1, testTile.getY(), "Tile y coordinate not set correctly");

    }

    @Test
    public void testTileTouching() {

        Assertions.assertEquals(0, testTile.getTouching(), "value of touching not initialised correctly");
        testTile.addTouching();
        Assertions.assertEquals(1, testTile.getTouching(), "value of touching not added correctly");

    }

    @Test
    public void testSetBomb() {

        Assertions.assertFalse(testTile.checkBomb(), "Tile bomb state not initialised correctly");
        testTile.setBomb();
        Assertions.assertTrue(testTile.checkBomb(), "Tile bomb state not updated correctly");

    }

    @Test
    public void testSetValue() {

        Assertions.assertEquals(".", testTile.getValue(), "incorrect value initialised");
        testTile.setValue("A");
        Assertions.assertEquals("A", testTile.getValue(), "value not set properly");

    }

    @Test
    public void testCheckTile() {

        Assertions.assertTrue(testTile.checkTile(), "Tile states incorrectly set");
        testTile.setClear();
        Assertions.assertFalse(testTile.checkTile(), "Tile state not updated");

    }






}
