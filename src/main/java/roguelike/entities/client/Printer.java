package roguelike.entities.client;

import roguelike.entities.EmptyBlock;
import roguelike.entities.Visitor;
import roguelike.entities.WallBlock;
import roguelike.entities.WearBlock;
import roguelike.entities.enemy.Enemy;
import roguelike.entities.hero.Ammunition;
import roguelike.entities.hero.Hero;
import roguelike.entities.hero.Wear;
import roguelike.map.WorldMap;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by the7winds on 29.11.16.
 */

/**
 * some-kind of TUI-driver
 */
public class Printer {

    private PrintStream printStream;

    /**
     * Strongly platform depend part, but it's keep my term clear :)
     */
    private static final String CLEAR = "\000\033[H\033[2J";
    private static final String CUP = "\000\033[1;1H";
    private static final String DL1 = "\000\033[1M";
    private static final int MAX_WIDTH = 100;
    private static final int MAX_HEIGHT = 50;

    public Printer(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void listInventory(Hero hero) {
        Ammunition ammunition = hero.getAmmunition();
        printStream.printf("--AMMUNITION--\n");
        printStream.printf("#\tS\tL\tO\n");
        int cnt = 0;
        for (Wear w : ammunition.getWears()) {
            printStream.printf("%d\t%d\t%d\t%s\n"
                    , cnt++
                    , w.getStrengthBonus()
                    , w.getLuckBonus()
                    , ammunition.isOn(w) ? "x" : " ");
        }
    }

    public void printHeroCharacter(Hero hero) {
        printStream.printf("Health: %d\tStrength: %d\tLuck: %d\n", hero.getHealth(), hero.getStrength(), hero.getLuck());
    }

    /**
     * to notify if player do something wrong
     */
    public void gameNotify(String message) {
        printStream.printf("GAME: %s", message);
    }

    /**
     * to display fatal errors
     */
    public void log(String message) {
        printStream.printf("LOG: %s", message);
    }

    public void drawMap(WorldMap worldMap) throws IOException {
        for (int i = 0; i < worldMap.getHeight(); ++i) {
            for (int j = 0; j < worldMap.getWidth(); ++j) {

                boolean visible = worldMap.isVisible(j, i);
                worldMap.getGameObject(j, i)
                        .accept(new Visitor() {

                            static final String INVISIBLE = ".";

                            @Override
                            public void visit(Enemy enemy) {
                                printStream.print(visible ? "E" : INVISIBLE);
                            }

                            @Override
                            public void visit(Hero hero) {
                                printStream.print("H");
                            }

                            @Override
                            public void visit(WearBlock wearBlock) {
                                printStream.print(visible ? "I" : INVISIBLE);
                            }

                            @Override
                            public void visit(EmptyBlock emptyBlock) {
                                printStream.print(visible ? " " : INVISIBLE);
                            }

                            @Override
                            public void visit(WallBlock wallBlock) {
                                printStream.print(visible ? "#" : INVISIBLE);
                            }
                        });
            }
            printStream.println();
        }
    }

    /**
     * calls once before running, because some terms interprets it like scroll
     */
    public void initialRender(Hero hero, WorldMap worldMap) throws IOException {
        printStream.print(CUP);
        printStream.print(CLEAR);
        printHeroCharacter(hero);
        drawMap(worldMap);
    }

    public int getScreenWidth() {
        return MAX_WIDTH;
    }

    public int getScreenHeight() {
        return MAX_HEIGHT;
    }

    public void clear() {
        printStream.print(CUP);
        for (int i = 0; i < MAX_HEIGHT; ++i) {
            printStream.print(DL1);
        }
    }
}
