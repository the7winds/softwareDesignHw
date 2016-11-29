package roguelike.logic;

import roguelike.entities.Unit;
import roguelike.entities.enemy.Enemy;
import roguelike.entities.hero.Hero;
import roguelike.map.Position;
import roguelike.map.WorldMap;

import java.util.*;

/**
 * Created by the7winds on 27.11.16.
 */
public class World {

    private static final int MAX_ENEMIES = 100;

    private WorldMap worldMap;
    private Set<Unit> enemies;
    private Hero hero;

    private World(Random random) {
        worldMap = WorldMap.generateMap(this, random);
        hero =  Hero.generateHero(this);
        worldMap.setVisible(hero.getPosition());

        int n = random.nextInt(MAX_ENEMIES);
        enemies = new HashSet<>();
        for (int i = 0; i < n; ++i) {
            enemies.add(Enemy.generateEnemy(this));
        }
    }

    public static World generateWorld(Random random) {
        return new World(random);
    }

    public Hero getHero() {
        return hero;
    }

    public Set<Unit> getEnemies() {
        return enemies;
    }

    public List<Unit> getUnits() {
        List<Unit> units = new LinkedList<>();
        units.add(hero);
        units.addAll(enemies);
        return units;
    }

    public Position allocatePosition() {
        return worldMap.allocatePosition();
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }
}
