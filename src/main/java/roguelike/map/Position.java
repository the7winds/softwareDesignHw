package roguelike.map;

import roguelike.entities.EmptyBlock;
import roguelike.entities.GameObject;

/**
 * Created by the7winds on 27.11.16.
 */
public final class Position {

    private final int x;
    private final int y;
    protected boolean visible;
    private GameObject gameObject;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position addShift(Position position) {
        return new Position(x + position.x, y + position.y);
    }

    public void setGameObject(GameObject gameObject) {
        if (this.gameObject != null) {
                this.gameObject.setPosition(null);
        }

        this.gameObject = gameObject;

        if (gameObject.getPosition() != null) {
            gameObject.getPosition().gameObject = new EmptyBlock(gameObject.getWorld());
            gameObject.getPosition().gameObject.setPosition(gameObject.getPosition());
        }

        gameObject.setPosition(this);
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public boolean isVisible() {
        return visible;
    }
}
