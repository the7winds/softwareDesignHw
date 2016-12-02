package roguelike.map;

import org.junit.Test;
import roguelike.entities.EmptyBlock;
import roguelike.entities.WallBlock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by the7winds on 03.12.16.
 */
public class PositionTest {

    @Test
    public void setGameObjectTest() throws Exception {
        WallBlock wallBlock = new WallBlock(null);
        Position x0y0 = new Position(0, 0);
        x0y0.setGameObject(wallBlock);

        EmptyBlock emptyBlock = new EmptyBlock(null);
        Position x0y1 = new Position(0, 1);
        x0y1.setGameObject(emptyBlock);

        assertEquals(x0y0, wallBlock.getPosition());
        assertEquals(wallBlock, x0y0.getGameObject());

        assertEquals(x0y1, emptyBlock.getPosition());
        assertEquals(emptyBlock, x0y1.getGameObject());

        x0y1.setGameObject(wallBlock);

        assertEquals(x0y1, wallBlock.getPosition());
        assertEquals(wallBlock, x0y1.getGameObject());
        assertTrue(x0y0.getGameObject() instanceof EmptyBlock);
    }
}