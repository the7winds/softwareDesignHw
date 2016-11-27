package roguelike.entities;

import roguelike.map.Position;

/**
 * Created by the7winds on 27.11.16.
 */
public abstract class Unit {

    private int health;
    private Position position;

    public void attack(Unit enemy) {
        enemy.attacked(evalDamage());
    }

    public abstract void attacked(int damage);

    public abstract int evalDamage();

    public boolean isAlive() {
        return health > 0;
    }

    public boolean move(Direction direction) {
        return direction.move(this);
    }
}
