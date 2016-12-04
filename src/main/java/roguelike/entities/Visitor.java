package roguelike.entities;

import roguelike.entities.hero.Hero;

/**
 * Created by the7winds on 27.11.16.
 */

/**
 * extends GameObject's heirs functionality (it's convenient way to display them)
 */
public class Visitor {

    public void visit(Enemy enemy) {}

    public void visit(Hero hero) {}

    public void visit(WearBlock wearBlock) {}

    public void visit(EmptyBlock emptyBlock) {}

    public void visit(WallBlock wallBlock) {}
}