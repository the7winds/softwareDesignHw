package roguelike.entities;

import roguelike.entities.hero.Wear;
import roguelike.logic.Direction;
import roguelike.logic.UnitScript;
import roguelike.logic.World;

import java.util.Random;

/**
 * Created by the7winds on 27.11.16.
 */

/**
 * Just enemy, it has fixed health and strength
 * Also when it dead it drops loot
 */
public class Enemy extends Unit {

    private static final int MAX_HEALTH = 60;
    private static final int MIN_STRENGTH = 5;

    private Wear loot;

    private Enemy(World world, Wear loot) {
        super(world, MAX_HEALTH);
        this.loot = loot;
        world.allocatePosition().setGameObject(this);
    }

    @Override
    public void attacked(int damage) {
        health -= damage;
        if (!isAlive()) {
            getPosition().setGameObject(new WearBlock(getWorld(), loot));
            getWorld().getEnemies().remove(this);
        }
    }

    @Override
    public int evalDamage() {
        return MIN_STRENGTH;
    }

    @Override
    public UnitScript getUnitScript() {
        return world -> {
            Direction values[] = Direction.values();
            move(values[new Random().nextInt(values.length)]);
        };
    }

    public static Enemy generateEnemy(World world) {
        Wear loot = Wear.generateWear(world.getRandom());
        return new Enemy(world, loot);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
