package roguelike.map;

import roguelike.entities.EmptyBlock;
import roguelike.entities.GameObject;
import roguelike.entities.WallBlock;
import roguelike.logic.World;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by the7winds on 27.11.16.
 */
public class WorldMap {

    public static final int MAX_WIDTH = 100;
    public static final int MAX_HEIGHT = 50;
    private static final int MAX_DISPERSION = 30;
    private static final int MAX_ROOMS = 20;

    private Position map[][] = new Position[MAX_WIDTH][MAX_HEIGHT];

    ArrayList<Position> positions = new ArrayList<>();
    private int top = 0;

    private WorldMap(World world, Random random) {

        for (int i = 0; i < MAX_HEIGHT; ++i) {
            for (int j = 0; j < MAX_WIDTH; ++j) {
                map[j][i] = new Position(j, i);
                map[j][i].setGameObject(new WallBlock(world));
            }
        }

        int roomsNumber = 1 + random.nextInt(MAX_ROOMS - 1);
        for (int i = 0; i < roomsNumber; ++i) {
            int x = 1 + random.nextInt(MAX_WIDTH - 1);
            int y = 1 + random.nextInt(MAX_HEIGHT - 1);
            int n = random.nextInt(MAX_DISPERSION * MAX_DISPERSION);
            int d = 1 + random.nextInt(MAX_DISPERSION - 1);

            positions.add(map[x][y]);

            for (int j = 0; j < n; ++j) {
                int x0 = (int) (random.nextGaussian() * d + x);
                int y0 = (int) (random.nextGaussian() * d + y);

                if (0 < x0 && x0 < MAX_WIDTH - 1
                        && 0 < y0 && y0 < MAX_HEIGHT - 1
                        && map[x0][y0].getGameObject() instanceof WallBlock) {
                    positions.add(map[x0][y0]);
                    map[x0][y0].setGameObject(new EmptyBlock(world));
                }
            }
        }

        for (int i = 0; i < positions.size(); ++i) {
            int j = random.nextInt(positions.size());
            Position pi = positions.get(i);
            Position pj = positions.get(j);
            positions.set(i, pj);
            positions.set(j, pi);
        }
    }

    public static WorldMap generateMap( World world, Random random) {
        return new WorldMap(world, random);
    }

    public Position allocatePosition() {
        return positions.get(top++);
    }

    public GameObject getGameObject(Position position) {
        return map[position.getX()][position.getY()].getGameObject();
    }

    public GameObject getGameObject(int x, int y) {
        return map[x][y].getGameObject();
    }

    public void setVisible(Position position) {
        int x = position.getX();
        int y = position.getY();

        if (x > 0) {
            map[x - 1][y].visible = true;
        }
        if (x + 1 < MAX_WIDTH) {
            map[x + 1][y].visible = true;
        }
        if (y > 0) {
            map[x][y - 1].visible = true;
        }
        if (y + 1 < MAX_HEIGHT) {
            map[x][y + 1].visible = true;
        }

    }

    public boolean isVisible(int x, int y) {
        return map[x][y].isVisible();
    }
}
