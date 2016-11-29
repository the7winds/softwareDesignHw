package roguelike.printer;

import roguelike.entities.EmptyBlock;
import roguelike.entities.Visitor;
import roguelike.entities.WallBlock;
import roguelike.entities.WearBlock;
import roguelike.entities.enemy.Enemy;
import roguelike.entities.hero.Ammunition;
import roguelike.entities.hero.Hero;
import roguelike.entities.hero.Wear;
import roguelike.logic.World;
import roguelike.map.WorldMap;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by the7winds on 29.11.16.
 */
public class Printer {

    private PrintStream printStream;

    public Printer(PrintStream printStream) {
        this.printStream = printStream;
    }

    public Visitor getListInventoryPrinter() {
        return new Visitor() {
            @Override
            public void visit(Hero hero) {
                Ammunition ammunition = hero.getAmmunition();
                printStream.printf("--AMMUNITION--\n");
                printStream.printf("#\tstrength bonus\tluck bonus\n");
                int cnt = 0;
                for (Wear w : ammunition.getWears()) {
                    if (ammunition.isOn(w)) {
                        printStream.printf("%d\t%d\t%d\n"
                                , cnt++
                                , w.getStrengthBonus()
                                , w.getLuckBonus());
                    }
                }
            }
        };
    }

    public void gameNotify(String message) {
        printStream.printf("GAME: %s", message);
    }

    public void log(String message) {
        printStream.printf("LOG: %s", message);
    }

    public void render(World world) throws IOException {
        for (int i = 0; i < WorldMap.MAX_HEIGHT; ++i) {
            for (int j = 0; j < WorldMap.MAX_WIDTH; ++j) {
                boolean visible = world.getWorldMap().isVisible(j, i);
                world.getWorldMap()
                        .getGameObject(j, i)
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
}
