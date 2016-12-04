package roguelike.entities.hero;

import roguelike.entities.Unit;
import roguelike.entities.Visitor;
import roguelike.client.Client;
import roguelike.logic.UnitScript;
import roguelike.logic.World;

import java.io.IOException;
import java.util.Random;

/**
 * Created by the7winds on 27.11.16.
 */
public class Hero extends Unit {

    private static final int MAX_HEALTH = 100;
    private static final int MIN_STRENGTH = 5;
    private static final int MIN_LUCK = 5;

    private Client client;
    private Ammunition ammunition = new Ammunition();

    private Hero(World world) {
        super(world, MAX_HEALTH);
        world.allocatePosition().setGameObject(this);
    }

    @Override
    public void attacked(int damage) {
        int realDamage = damage - (new Random()).nextInt(getLuck());
        health -= realDamage > 0 ? realDamage : 0;
    }

    @Override
    public int evalDamage() {
        return getStrength() + (new Random()).nextInt(getLuck());
    }

    public Ammunition getAmmunition() {
        return ammunition;
    }

    @Override
    public UnitScript getUnitScript() throws IOException {
        client.waitForAction();
        return client.getUnitScript();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void bindToClient(Client client) {
        this.client = client;
    }

    public static Hero generateHero(World world) {
        return new Hero(world);
    }

    public int getStrength() {
        return MIN_STRENGTH + ammunition.getStrengthBonus();
    }

    public int getLuck() {
        return MIN_LUCK + ammunition.getLuckBonus();
    }
}