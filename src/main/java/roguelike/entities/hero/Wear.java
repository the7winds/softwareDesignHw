package roguelike.entities.hero;

import java.util.Random;

/**
 * Created by the7winds on 27.11.16.
 */
public interface Wear {

    int MAX_LUCK_BONUS = 10;
    int MAX_STRENGTH_BONUS = 20;

    int getLuckBonus();
    int getStrengthBonus();

    static Wear generateRandomWear() {
        Random random = new Random();

        int luck = random.nextInt(MAX_LUCK_BONUS);
        int strength = random.nextInt(MAX_STRENGTH_BONUS);

        return new Wear() {
            @Override
            public int getLuckBonus() {
                return luck;
            }

            @Override
            public int getStrengthBonus() {
                return strength;
            }
        };
    }
}
