package roguelike.entities.hero;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by the7winds on 27.11.16.
 */
public class Ammunition {

    private static final int ON_LIMIT = 2;

    private List<Wear> wears = new ArrayList<>();
    private List<Wear> on = new ArrayList<>();

    public List<Wear> getWears() {
        return wears;
    }

    public boolean isOn(Wear wear) {
        return on.contains(wear);
    }

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

    public Wear getWearByNumber(int index) {
        return wears.get(index);
    }

    public void add(Wear wear) {
        wears.add(wear);
    }
}
