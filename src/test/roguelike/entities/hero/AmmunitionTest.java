package roguelike.entities.hero;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by the7winds on 02.12.16.
 */
public class AmmunitionTest {

    @Test
    public void initialStateTest() {
        Ammunition ammunition = new Ammunition();

        assertEquals(0, ammunition.getWears().size());
        assertEquals(0, ammunition.getLuckBonus());
        assertEquals(0, ammunition.getStrengthBonus());
    }

    @Test
    public void getBonusTest() {
        Ammunition ammunition = new Ammunition();
        Random random = new Random(0);

        Wear wear0 = Wear.generateWear(random);
        Wear wear1 = Wear.generateWear(random);

        ammunition.add(wear0);
        ammunition.add(wear1);

        assertEquals(0, ammunition.getLuckBonus());
        assertEquals(0, ammunition.getStrengthBonus());

        ammunition.putOn(wear0);
        ammunition.putOn(wear1);

        assertEquals(wear0.getLuckBonus() + wear1.getLuckBonus(), ammunition.getLuckBonus());
        assertEquals(wear0.getStrengthBonus() + wear1.getStrengthBonus(), ammunition.getStrengthBonus());

        ammunition.putOff(wear0);

        assertEquals(wear1.getLuckBonus(), ammunition.getLuckBonus());
        assertEquals(wear1.getStrengthBonus(), ammunition.getStrengthBonus());
    }

}