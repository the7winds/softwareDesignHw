package roguelike.logic;

import roguelike.entities.hero.Ammunition;
import roguelike.entities.hero.Wear;

import java.io.IOException;

/**
 * Created by the7winds on 03.12.16.
 */
public abstract class Command {

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
