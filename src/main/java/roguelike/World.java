package roguelike;

import roguelike.entities.Unit;
import roguelike.entities.hero.Hero;
import roguelike.map.WorldMap;

import java.util.HashSet;

/**
 * Created by the7winds on 27.11.16.
 */
public class World {

    private WorldMap worldMap;
    private HashSet<Unit> enemies;
    private Hero hero;

    private World() {
        worldMap = WorldMap.randomMap();
    }

    World generateWorld() {
        throw new UnsupportedOperationException();
    }

    public void nextQuantum() {

    }
}
