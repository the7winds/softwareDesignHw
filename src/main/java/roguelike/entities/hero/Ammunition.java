package roguelike.entities.hero;

import java.util.List;

/**
 * Created by the7winds on 27.11.16.
 */
public class Ammunition {

    private static final int ON_LIMIT = 2;

    private List<Wear> wears;
    private List<Wear> on;

    public boolean putOn(Wear wear) {
        if (on.size() < ON_LIMIT) {
            on.add(wear);
            return true;
        }

        return false;
    }

    public void putOff(Wear wear) {
        on.remove(wear);
    }
}
