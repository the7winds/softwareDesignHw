package roguelike;

import roguelike.gameObject.Hero;
import roguelike.map.Map;

/**
 * Created by the7winds on 27.11.16.
 */
public class World {

    private Map map;
    private Hero hero;

    private World() {
        map = Map.randomMap();
    }

    World generateWorld() {
        throw new UnsupportedOperationException();
    }
}
