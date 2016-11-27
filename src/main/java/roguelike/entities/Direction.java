package roguelike.entities;

/**
 * Created by the7winds on 27.11.16.
 */
public enum Direction {

    NORTH,
    SOUTH,
    WEST,
    EAST;

    boolean move(Unit unit) {
        throw new UnsupportedOperationException();
    }
}
