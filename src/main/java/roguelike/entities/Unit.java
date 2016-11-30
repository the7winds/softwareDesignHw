package roguelike.entities;

import roguelike.entities.enemy.Enemy;
import roguelike.entities.hero.Hero;
import roguelike.logic.UnitScript;
import roguelike.logic.Utils;
import roguelike.logic.World;

import java.io.IOException;

/**
 * Created by the7winds on 27.11.16.
 */
public abstract class Unit extends GameObject {

    protected int health;

    public Unit(World world) {
        super(world);
    }

    public void attack(Unit enemy) {
        enemy.attacked(evalDamage());
    }

    public abstract void attacked(int damage);

    public abstract int evalDamage();

    public abstract UnitScript getUnitScript() throws IOException;

    public boolean isAlive() {
        return health > 0;
    }

    public void move(Utils.Direction direction) {
        direction.move(this, getWorld());
    }

    public void touch(GameObject gameObject) {
        gameObject.accept(new TouchVisitor());
    }

    public int getHealth() {
        return health;
    }

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
