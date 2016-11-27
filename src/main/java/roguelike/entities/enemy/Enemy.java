package roguelike.entities.enemy;

import roguelike.entities.Unit;
import roguelike.entities.hero.Wear;
import roguelike.map.Position;

/**
 * Created by the7winds on 27.11.16.
 */
public class Enemy extends Unit {

    private static final int MAX_HEALTH = 20;
    private static final int MIN_STRENGTH = 2;

    private int health = MAX_HEALTH;
    private int strength = MIN_STRENGTH;
    private Position position;

    private Enemy(Wear loot, Position position) {
        this.loot = loot;
        this.position = position;
    }

    private Wear loot;

    public void attacked(int damage) {
        health -= damage;
    }

    public int evalDamage() {
        return strength;
    }

    Enemy generateEnemy(Position position) {
        Wear loot = Wear.generateRandomWear();
        return new Enemy(loot, position);
    }
}
