package roguelike.logic;

import roguelike.entities.Unit;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by the7winds on 27.11.16.
 */
public class Game {

    private World world;
    private Client client;
    private boolean finished = false;

    public Game(Random random, Client client) {
        this.client = client;
        int x = client.getPrinter().getScreenWidth() / 2;
        int y = client.getPrinter().getScreenHeight() / 2;
        world = World.generateWorld(random, x, y);
        world.getHero().bindToClient(client);
    }

    public void play() {
        try {
            client.getPrinter().initialRender(world.getHero(), world.getWorldMap());
            while (!isFinished()) {
                List<Unit> units = world.getUnits();
                for (Unit unit : units) {
                    UnitScript unitScript = unit.getUnitScript();
                    unitScript.execute(this);
                }
            }
        } catch (IOException e) {
            client.getPrinter().log(e.getMessage());
        }
    }

    private boolean isFinished() {
        return finished || !world.getHero().isAlive();
    }

    public World getWorld() {
        return world;
    }

    public Client getClient() {
        return client;
    }

    public void finish() {
        finished = true;
    }
}
