package roguelike.entities;

import org.junit.Test;
import roguelike.logic.UnitScript;
import roguelike.map.Position;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by the7winds on 03.12.16.
 */
public class UnitTest {

    private Unit unit = new Unit(null, 0) {
        @Override
        public void attacked(int damage) {

        }

        @Override
        public int evalDamage() {
            return 0;
        }

        @Override
        public UnitScript getUnitScript() throws IOException {
            return null;
        }

        @Override
        public void accept(Visitor visitor) {

        }
    };

    @Test
    public void touchEmptyBlockTest() throws Exception {
        Position x0y0 = new Position(0, 0);
        x0y0.setGameObject(unit);

        EmptyBlock emptyBlock = new EmptyBlock(null);
        Position x0y1 = new Position(0, 1);
        x0y1.setGameObject(emptyBlock);

        unit.touch(emptyBlock);

        assertEquals(x0y1, unit.getPosition());
        assertEquals(unit, x0y1.getGameObject());
        assertTrue(x0y0.getGameObject() instanceof EmptyBlock);
    }

    @Test
    public void touchWallBlockTest() throws Exception {
        Position x0y0 = new Position(0, 0);
        x0y0.setGameObject(unit);

        WallBlock wallBlock = new WallBlock(null);
        Position x0y1 = new Position(0, 1);
        x0y1.setGameObject(wallBlock);

        unit.touch(wallBlock);

        assertEquals(x0y0, unit.getPosition());
        assertEquals(unit, x0y0.getGameObject());
        assertEquals(x0y1, wallBlock.getPosition());
        assertEquals(wallBlock, x0y1.getGameObject());
    }
}