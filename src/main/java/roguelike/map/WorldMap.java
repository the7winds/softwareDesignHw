package roguelike.map;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by the7winds on 27.11.16.
 */
public class WorldMap {

    private static final int MAX_WIDTH = 100;
    private static final int MAX_HEIGHT = 50;
    private static final int MAX_DISPERSION = 30;
    private static final int MAX_ROOMS = 20;

    private Position map[][] = new Position[MAX_WIDTH][MAX_HEIGHT];

    ArrayList<Position> positions = new ArrayList<>();
    private int top = 0;

    private WorldMap(int seed, int roomsNumber) {
        Random random = new Random(seed);

        for (int i = 0; i < roomsNumber; ++i) {
            int x = 1 + random.nextInt(MAX_WIDTH - 1);
            int y = 1 + random.nextInt(MAX_HEIGHT - 1);
            int n = random.nextInt(MAX_DISPERSION * MAX_DISPERSION);
            int d = 1 + random.nextInt(MAX_DISPERSION - 1);

            map[x][y] = new Position(x, y);
            positions.add(map[x][y]);

            for (int j = 0; j < n; ++j) {
                int x0 = (int) (random.nextGaussian() * d + x);
                int y0 = (int) (random.nextGaussian() * d + y);

                if (0 <= x0 && x0 < MAX_WIDTH && 0 <= y0 && y0 < MAX_HEIGHT && map[x0][y0] == null) {
                    map[x0][y0] = new Position(x0, y0);
                    positions.add(map[x0][y0]);
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

    public static WorldMap generateMap(int seed, int roomsNumber) {
        return new WorldMap(seed, roomsNumber);

    }

    public static WorldMap randomMap() {
        int seed0 = (int) System.currentTimeMillis();
        int seed1 = (int) System.currentTimeMillis();
        int rooms = new Random(seed1).nextInt(MAX_ROOMS);
        return generateMap(seed0, rooms);
    }

    public void print(PrintStream printStream) {
        for (int j = 0; j < MAX_HEIGHT; ++j) {
            for (int i = 0; i < MAX_WIDTH; ++i) {
                printStream.print(map[i][j] != null ? ' ' : '#');
            }
            printStream.println();
        }
    }

    public Position allocatePosition() {
        return positions.get(top++);
    }

    public void freePosition(Position position) {
        top--;
    }
}
