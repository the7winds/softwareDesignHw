package roguelike.client;

import roguelike.entities.hero.Hero;
import roguelike.logic.UnitScript;
import roguelike.map.WorldMap;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by the7winds on 27.11.16.
 */

/**
 * Some kind of connection with player
 */
public class Client {

    private String rawCommand;
    private DataInput dataInput;
    private Printer printer;

    public Client(InputStream inputStream, PrintStream printStream) throws IOException {
        dataInput = new DataInputStream(inputStream);
        printer = new Printer(printStream);
    }

    public final void waitForAction() throws IOException {
        rawCommand = dataInput.readLine();
    }

    /**
     * player's actions are hidden by this UnitScript
     */
    public UnitScript getUnitScript() {
        return game -> {
            Command command;
            for (Class<?> clazz : Command.class.getClasses()) {
                try {
                    command = (Command) clazz.getConstructors()[0].newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    return;
                }

                if (command.deserialize(rawCommand)) {
                    command.execute(game);
                    return;
                }
            }

            printer.gameNotify("Unknown command\n");
        };
    }

    public int getScreenWidth() {
        return printer.getScreenWidth();
    }

    public int getScreenHeight() {
        return printer.getScreenHeight();
    }

    public void log(String message) {
        printer.log(message);
    }

    public void initialRender(Hero hero, WorldMap worldMap) throws IOException {
        printer.initialRender(hero, worldMap);
    }

    Printer getPrinter() {
        return printer;
    }
}
