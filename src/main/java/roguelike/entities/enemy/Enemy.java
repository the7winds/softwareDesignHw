package roguelike.entities.enemy;

import roguelike.entities.EmptyBlock;
import roguelike.entities.Unit;
import roguelike.entities.Visitor;
import roguelike.entities.hero.Wear;
import roguelike.logic.UnitScript;
import roguelike.logic.World;

/**
 * Created by the7winds on 27.11.16.
 */
public class Enemy extends Unit {

    private static final int MAX_HEALTH = 20;
    private static final int MIN_STRENGTH = 2;

    private int strength = MIN_STRENGTH;
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
            getPosition().setGameObject(new EmptyBlock(getWorld()));
            getWorld().getEnemies().remove(this);
        }
    }

    @Override
    public int evalDamage() {
        return strength;
    }

    @Override
    public UnitScript getUnitScript() {
        return world -> {
            // TODO: make random turn;
        };
    }

    public static Enemy generateEnemy(World world) {
        Wear loot = Wear.generateRandomWear();
        return new Enemy(world, loot);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
