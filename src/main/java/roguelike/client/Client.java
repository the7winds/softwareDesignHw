package roguelike.client;

import roguelike.logic.UnitScript;

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

            game.getClient()
                    .getPrinter()
                    .gameNotify("Unknown command\n");
        };
    }

    public Printer getPrinter() {
        return printer;
    }
}
