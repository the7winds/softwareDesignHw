package roguelike.map;

/**
 * Created by the7winds on 27.11.16.
 */
public final class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position p = (Position) obj;
            return (x == p.x && y == p.y);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
