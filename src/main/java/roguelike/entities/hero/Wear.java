package roguelike.entities.hero;

import java.util.Random;

/**
 * Created by the7winds on 27.11.16.
 */

/**
 * some kind of inventory
 */
public class Wear {

    private static final int MAX_LUCK_BONUS = 10;
    private static final int MAX_STRENGTH_BONUS = 20;
    private final int luckBonus;
    private final int strengthBonus;

    private Wear(int luckBonus, int strengthBonus) {
        this.luckBonus = luckBonus;
        this.strengthBonus = strengthBonus;
    }

    public static Wear generateWear(Random random) {
        return new Wear(random.nextInt(MAX_LUCK_BONUS), random.nextInt(MAX_STRENGTH_BONUS));
    }

    public int getLuckBonus() {
        return luckBonus;
    }

    public int getStrengthBonus() {
        return strengthBonus;
    }
}
