package roguelike.entities;

import roguelike.entities.hero.Hero;
import roguelike.logic.Direction;
import roguelike.logic.UnitScript;
import roguelike.logic.World;

import java.io.IOException;

/**
 * Created by the7winds on 27.11.16.
 */

/**
 * describes alive entities like hero and enemies
 */
public abstract class Unit extends GameObject {

    protected int health;

    public Unit(World world, int health) {
        super(world);
        this.health = health;
    }

    private void attack(Unit enemy) {
        enemy.attacked(evalDamage());
    }

    /**
     * applying damage to this
     */
    public abstract void attacked(int damage);

    /**
     * returns real damage
     */
    public abstract int evalDamage();

    public abstract UnitScript getUnitScript() throws IOException;

    public boolean isAlive() {
        return health > 0;
    }

    /**
     * delegate movement to Direction object to make switch
     * disappear it implicit invoke touch method, so as a
     * result move if it can or react with the neighbour block
     */
    public void move(Direction direction) {
        direction.move(this, getWorld());
    }

    /**
     * move if it can or react with the neighbour block
     */
    public void touch(GameObject gameObject) {
        gameObject.accept(new TouchVisitor());
    }

    public int getHealth() {
        return health;
    }

    /**
     * to simplify reaction between two blocks
     */
    class TouchVisitor extends Visitor {

        @Override
        public void visit(Enemy enemy) {
            Unit.this.attack(enemy);
        }

        @Override
        public void visit(Hero hero) {
            Unit.this.attack(hero);
        }

        @Override
        public void visit(WearBlock wearBlock) {
            if (Unit.this instanceof Hero) {
                Hero hero = (Hero) Unit.this;
                hero.getAmmunition().add(wearBlock.getWear());
            }
            wearBlock.getPosition().setGameObject(Unit.this);
        }

        @Override
        public void visit(EmptyBlock emptyBlock) {
            emptyBlock.getPosition().setGameObject(Unit.this);
        }
    }
}
