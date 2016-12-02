package roguelike.entities.enemy;

import roguelike.entities.Unit;
import roguelike.entities.Visitor;
import roguelike.entities.WearBlock;
import roguelike.entities.hero.Wear;
import roguelike.logic.UnitScript;
import roguelike.logic.Utils;
import roguelike.logic.World;

import java.util.Random;

/**
 * Created by the7winds on 27.11.16.
 */
public class Enemy extends Unit {

    private static final int MAX_HEALTH = 60;
    private static final int MIN_STRENGTH = 5;

    private Wear loot;

    private Enemy(World world, Wear loot) {
        super(world);
        this.loot = loot;
        super.health = MAX_HEALTH;
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
            Utils.Direction values[] = Utils.Direction.values();
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
