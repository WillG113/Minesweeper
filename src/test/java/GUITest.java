import org.junit.jupiter.api.*;
import javax.swing.*;
import java.awt.*;


public class GUITest {



    GUI test = new GUI(3,3);



    @Test
    public void testGUI() {
        test.startGame();

        //Test that arrays are initialised

        Assertions.assertEquals(3, test.getGameBoard().getBoard().length);
        Assertions.assertEquals(3, test.getButtonArray().length);
    }

    @Test
    public void testHit() {
        test.startGame();

        JButton testButton = test.getButtonArray()[0][0];

        if(test.getGameBoard().getBoard()[0][0].checkBomb()) {
            test.getGameBoard().getBoard()[0][0].setBomb();
        }

        Assertions.assertFalse(test.getGameBoard().getBoard()[0][0].checkBomb());
        test.hit(testButton, 0, 0, test.getGameBoard(), test.getButtonArray());
        Assertions.assertTrue(test.getGameBoard().getBoard()[0][0].isClear());

        Assertions.assertSame(test.getButtonArray()[0][0].getBackground(), Color.GRAY);
        Assertions.assertSame(test.getButtonArray()[0][0].getForeground(), Color.BLACK);
    }

    @Test
    public void testHit2() {
        test.startGame();

        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (test.getGameBoard().getBoard()[i][j].checkBomb()) {
                    test.getGameBoard().getBoard()[i][j].setBomb();
                }
                test.getGameBoard().getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(test.getGameBoard().getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        test.hit(test.getButtonArray()[0][0], 0, 0, test.getGameBoard(), test.getButtonArray());
        Assertions.assertSame("", test.getButtonArray()[0][0].getText());
        Assertions.assertTrue(test.getGameBoard().getBoard()[0][0].isClear());
    }

    @Test
    public void testHit3() {
        test.startGame();

        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (test.getGameBoard().getBoard()[i][j].checkBomb()) {
                    test.getGameBoard().getBoard()[i][j].setBomb();
                }
                test.getGameBoard().getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(test.getGameBoard().getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        test.getGameBoard().setBomb(test.getGameBoard().getBoard()[0][0]);

        test.hit(test.getButtonArray()[1][1], 1, 1, test.getGameBoard(), test.getButtonArray());

        Assertions.assertEquals(1, Integer.parseInt(test.getButtonArray()[1][1].getText()));
        Assertions.assertTrue(test.getGameBoard().getBoard()[1][1].isClear());
    }

    @Test
    public void testHit4() {
        test.startGame();

        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (test.getGameBoard().getBoard()[i][j].checkBomb()) {
                    test.getGameBoard().getBoard()[i][j].setBomb();
                }
                test.getGameBoard().getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(test.getGameBoard().getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        test.getGameBoard().setBomb(test.getGameBoard().getBoard()[0][0]);

        test.hit(test.getButtonArray()[0][0], 0, 0, test.getGameBoard(), test.getButtonArray());

        Assertions.assertSame(Color.RED, test.getButtonArray()[0][0].getBackground());
        Assertions.assertSame(Color.BLACK, test.getButtonArray()[0][0].getForeground());
    }

    @Test
    public void testFlag() {
        test.startGame();
        JButton testButton = test.getButtonArray()[0][0];
        test.flag(testButton, 0, 0);
        Assertions.assertTrue(test.getGameBoard().getBoard()[0][0].isFlagged());
        Assertions.assertSame(Color.YELLOW, test.getButtonArray()[0][0].getBackground());
        Assertions.assertSame(Color.BLACK, test.getButtonArray()[0][0].getForeground());
    }

    @Test
    public void testFlag2() {
        test.startGame();
        JButton testButton = test.getButtonArray()[0][0];
        test.flag(testButton, 0, 0);
        Assertions.assertTrue(test.getGameBoard().getBoard()[0][0].isFlagged());
        test.flag(testButton, 0, 0);
        Assertions.assertFalse(test.getGameBoard().getBoard()[0][0].isFlagged());
        Assertions.assertSame(Color.BLACK, test.getButtonArray()[0][0].getBackground());
        Assertions.assertSame(Color.WHITE, test.getButtonArray()[0][0].getForeground());
    }

    @Test
    public void testFlagAllBombs() {
        test.startGame();

        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (test.getGameBoard().getBoard()[i][j].checkBomb()) {
                    test.getGameBoard().getBoard()[i][j].setBomb();
                }
                test.getGameBoard().getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(test.getGameBoard().getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        test.getGameBoard().setBomb(test.getGameBoard().getBoard()[0][0]);
        test.getGameBoard().setBomb(test.getGameBoard().getBoard()[1][0]);
        test.getGameBoard().setBomb(test.getGameBoard().getBoard()[2][0]);

        test.flagAllBombs(test.getButtonArray(), test.getGameBoard());
        Assertions.assertSame(Color.RED, test.getButtonArray()[0][0].getBackground());
        Assertions.assertSame(Color.BLACK, test.getButtonArray()[0][0].getForeground());

        test.flagAllBombs(test.getButtonArray(), test.getGameBoard());
        Assertions.assertSame(Color.RED, test.getButtonArray()[1][0].getBackground());
        Assertions.assertSame(Color.BLACK, test.getButtonArray()[1][0].getForeground());

        test.flagAllBombs(test.getButtonArray(), test.getGameBoard());
        Assertions.assertSame(Color.RED, test.getButtonArray()[2][0].getBackground());
        Assertions.assertSame(Color.BLACK, test.getButtonArray()[2][0].getForeground());
    }

    @Test
    public void testCheckVictory() {
        test.startGame();

        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (test.getGameBoard().getBoard()[i][j].checkBomb()) {
                    test.getGameBoard().getBoard()[i][j].setBomb();
                }
                test.getGameBoard().getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(test.getGameBoard().getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        test.getGameBoard().setBomb(test.getGameBoard().getBoard()[0][0]);
        test.getGameBoard().setBomb(test.getGameBoard().getBoard()[0][1]);
        test.flag(test.getButtonArray()[0][0], 0, 0);
        test.flag(test.getButtonArray()[0][0], 0, 1);
        Assertions.assertTrue(test.getGameBoard().getGameOver());
        Assertions.assertEquals("YOU WON! YOU FLAGGED THE MINES", test.getWinLabel().getText());
    }

    @Test
    public void testCheckVictory2() {
        test.startGame();

        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (test.getGameBoard().getBoard()[i][j].checkBomb()) {
                    test.getGameBoard().getBoard()[i][j].setBomb();
                }
                test.getGameBoard().getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(test.getGameBoard().getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        test.getGameBoard().setBomb(test.getGameBoard().getBoard()[0][0]);
        test.getGameBoard().setBomb(test.getGameBoard().getBoard()[1][0]);

        test.hit(test.getButtonArray()[0][0], 2, 2, test.getGameBoard(), test.getButtonArray());
        test.hit(test.getButtonArray()[0][0], 0, 1, test.getGameBoard(), test.getButtonArray());
        test.hit(test.getButtonArray()[0][0], 1, 1, test.getGameBoard(), test.getButtonArray());
        test.hit(test.getButtonArray()[0][0], 2, 0, test.getGameBoard(), test.getButtonArray());
        test.hit(test.getButtonArray()[0][0], 0, 2, test.getGameBoard(), test.getButtonArray());

        Assertions.assertTrue(test.getGameBoard().getGameOver());
        Assertions.assertEquals("YOU WON! YOU CLEARED ALL THE TILES", test.getWinLabel().getText());
    }

    @Test
    public void testCheckVictory3() {
        test.startGame();

        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (test.getGameBoard().getBoard()[i][j].checkBomb()) {
                    test.getGameBoard().getBoard()[i][j].setBomb();
                }
                test.getGameBoard().getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(test.getGameBoard().getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        test.getGameBoard().setBomb(test.getGameBoard().getBoard()[0][0]);
        test.hit(test.getButtonArray()[0][0], 0, 0, test.getGameBoard(), test.getButtonArray());
        Assertions.assertTrue(test.getGameBoard().getGameOver());
        Assertions.assertEquals("YOU LOST!", test.getWinLabel().getText());
    }

    @Test
    public void testBulk() {
        test.startGame();

        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (test.getGameBoard().getBoard()[i][j].checkBomb()) {
                    test.getGameBoard().getBoard()[i][j].setBomb();
                }
                test.getGameBoard().getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(test.getGameBoard().getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }

        test.bulkHit(1,1, test.getButtonArray(), test.getGameBoard());
        Assertions.assertTrue(test.getGameBoard().getBoard()[0][0].isClear());
        Assertions.assertTrue(test.getGameBoard().getBoard()[2][2].isClear());

    }

    @Test
    public void testBulk2() {
        test.startGame();

        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (test.getGameBoard().getBoard()[i][j].checkBomb()) {
                    test.getGameBoard().getBoard()[i][j].setBomb();
                }
                test.getGameBoard().getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(test.getGameBoard().getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }
        test.flag(test.getButtonArray()[0][0], 0, 0);
        test.bulkHit(1,1, test.getButtonArray(), test.getGameBoard());
        Assertions.assertFalse(test.getGameBoard().getBoard()[0][0].isClear());
        Assertions.assertTrue(test.getGameBoard().getBoard()[2][2].isClear());

    }

    @Test
    public void testBulk3() {
        test.startGame();

        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++) {
                if (test.getGameBoard().getBoard()[i][j].checkBomb()) {
                    test.getGameBoard().getBoard()[i][j].setBomb();
                }
                test.getGameBoard().getBoard()[i][j].setTouching(0);
                Assertions.assertFalse(test.getGameBoard().getBoard()[i][j].checkBomb(), "no bombs should be set");
            }
        }
        test.getGameBoard().setBomb(test.getGameBoard().getBoard()[0][0]);
        test.bulkHit(1,1, test.getButtonArray(), test.getGameBoard());
        Assertions.assertTrue(test.getGameBoard().getGameOver());

    }
}
