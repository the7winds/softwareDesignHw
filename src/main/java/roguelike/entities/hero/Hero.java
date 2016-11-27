package roguelike.entities.hero;

import roguelike.entities.Unit;
import roguelike.map.Position;

import java.util.Random;

/**
 * Created by the7winds on 27.11.16.
 */
public class Hero extends Unit {

    private static final int MAX_HEALTH = 100;
    private static final int MIN_STRENGTH = 5;
    private static final int MIN_LUCK = 5;

    private int health = MAX_HEALTH;
    private int strength = MIN_STRENGTH;
    private int luck = MIN_LUCK;
    private Position position;
    private Ammunition ammunition;

    Hero(Position position) {
        this.position = position;
    }

    public void attacked(int damage) {
        int realDamage = damage - (new Random()).nextInt(luck);
        health -= realDamage > 0 ? realDamage : 0;
    }

    public int evalDamage() {
        return strength + (new Random()).nextInt(luck);
    }

    public boolean putOn(Wear wear) {
        return ammunition.putOn(wear);
    }

    public void putOff(Wear wear) {
        ammunition.putOff(wear);
    }

    public Ammunition getAmmunition() {
        return ammunition;
    }
}
