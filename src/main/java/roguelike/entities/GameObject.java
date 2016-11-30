package roguelike.entities;

import roguelike.logic.World;
import roguelike.map.Position;

/**
 * Created by the7winds on 27.11.16.
 */
public abstract class GameObject {

    private final World world;
    private Position position;

    public abstract void accept(Visitor visitor);

    public GameObject(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    /**
     * called only by Position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

}
