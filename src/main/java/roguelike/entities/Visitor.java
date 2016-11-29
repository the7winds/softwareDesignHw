package roguelike.entities;

import roguelike.entities.enemy.Enemy;
import roguelike.entities.hero.Hero;

/**
 * Created by the7winds on 27.11.16.
 */
public class Visitor {

    public void visit(Enemy enemy) {}

    public void visit(Hero hero) {}

    public void visit(WearBlock wearBlock) {}

    public void visit(EmptyBlock emptyBlock) {}

    public void visit(WallBlock wallBlock) {}
}