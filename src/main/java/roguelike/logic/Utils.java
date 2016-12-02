package roguelike.logic;

import roguelike.entities.GameObject;
import roguelike.entities.Unit;
import roguelike.entities.hero.Ammunition;
import roguelike.entities.hero.Wear;
import roguelike.map.Position;

import java.io.IOException;

/**
 * Created by the7winds on 29.11.16.
 */
public class Utils {
    /**
     * Created by the7winds on 27.11.16.
     */
    public enum Direction {

        NORTH(0, -1),
        SOUTH(0, 1),
        WEST(-1, 0),
        EAST(1, 0),
        NONE(0, 0);

        private int dx;
        private int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public void move(Unit unit, World world) {
            if (equals(NONE)) {
                return;
            }

            Position newPosition = unit.getPosition().addShift(dx, dy);
            GameObject gameObject = world.getWorldMap().getGameObject(newPosition);
            unit.touch(gameObject);
        }
    }

    public static abstract class Command {

        public abstract void execute(Game game) throws IOException;

        public abstract boolean deserialize(String string);

        public static class ListInvetory extends Command {

            private static final String tag = "li";

            @Override
            public void execute(Game game) throws IOException {
                basicScript(game);

                game.getClient()
                        .getPrinter()
                        .listInventory(
                                game.getWorld()
                                        .getHero()
                        );
            }

            @Override
            public boolean deserialize(String string) {
                return string.equals(tag);
            }
        }

        public static class PutOff extends Command {

            private static final String tag = "pf";
            private int index;

            @Override
            public void execute(Game game) throws IOException {
                Ammunition ammunition = game.getWorld().getHero().getAmmunition();
                Wear wear = ammunition.getWearByNumber(index);
                ammunition.putOff(wear);

                basicScript(game);
            }

            @Override
            public boolean deserialize(String string) {
                String[] tokens = string.split("\\s");

                try {
                    index = Integer.valueOf(tokens[1]);
                    return tokens[0].equals(tag);
                } catch (Exception e) {
                    return false;
                }
            }
        }

        public static class PutOn extends Command {

            private static final String tag = "pn";
            private int index;

            @Override
            public void execute(Game game) throws IOException {
                Ammunition ammunition = game.getWorld().getHero().getAmmunition();
                Wear wear = ammunition.getWearByNumber(index);
                ammunition.putOn(wear);

                basicScript(game);
            }

            @Override
            public boolean deserialize(String string) {
                String[] tokens = string.split("\\s");

                try {
                    index = Integer.valueOf(tokens[1]);
                    return tokens[0].equals(tag);
                } catch (Exception e) {
                    return false;
                }
            }
        }

        public static class Move extends Command {

            private static final String tag = "m";
            private Direction direction;

            @Override
            public void execute(Game game) throws IOException {
                game.getWorld()
                        .getHero()
                        .move(direction);

                basicScript(game);
            }

            @Override
            public boolean deserialize(String string) {
                String[] tokens = string.split("\\s");

                try {
                    switch (tokens[1]) {
                        case "u":
                            direction = Direction.NORTH;
                            break;
                        case "d":
                            direction = Direction.SOUTH;
                            break;
                        case "l":
                            direction = Direction.WEST;
                            break;
                        case "r":
                            direction = Direction.EAST;
                            break;
                        default:
                            return false;
                    }

                    return tokens[0].equals(tag);
                } catch (Exception e) {
                    return false;
                }
            }
        }

        public static class Quit extends Command {

            private static final String tag = "q";

            @Override
            public void execute(Game game) {
                game.finish();
            }

            @Override
            public boolean deserialize(String string) {
                return string.equals(tag);
            }
        }

        protected static void basicScript(Game game) throws IOException {
            game.getClient()
                    .getPrinter()
                    .clear();

            game.getClient()
                    .getPrinter()
                    .printHeroCharacter(
                            game.getWorld()
                                    .getHero()
                    );

            game.getWorld()
                    .getWorldMap()
                    .setVisible(
                            game.getWorld()
                                    .getHero()
                                    .getPosition()
                    );

            game.getClient()
                    .getPrinter()
                    .drawMap(
                            game.getWorld()
                                    .getWorldMap()
                    );
        }
    }
}
