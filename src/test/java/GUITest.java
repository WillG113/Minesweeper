import org.junit.jupiter.api.*;

import java.awt.*;

public class GUITest {

    GUI test = new GUI(3,3);

    @Test
    public void testGUI() {
        //Test that arrays are initialised
        Assertions.assertEquals(3, test.getGameBoard().getBoard().length);
        Assertions.assertEquals(3, test.getButtonArray().length);
    }

}
