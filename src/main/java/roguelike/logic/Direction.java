package roguelike.logic;

import roguelike.entities.GameObject;
import roguelike.entities.Unit;
import roguelike.map.Position;

/**
 * Created by the7winds on 27.11.16.
 */
public enum Direction {

    NORTH(0, -1),
    SOUTH(0, 1),
    WEST(-1, 0),
    EAST(1, 0),
    NONE(0, 0);

    private int dx;
    private int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void move(Unit unit, World world) {
        if (equals(NONE)) {
            return;
        }

        Position newPosition = unit.getPosition().addShift(dx, dy);
        GameObject gameObject = world.getWorldMap().getGameObject(newPosition);
        unit.touch(gameObject);
    }
}
