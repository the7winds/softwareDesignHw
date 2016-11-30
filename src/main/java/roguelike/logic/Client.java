package roguelike.logic;

import roguelike.printer.Printer;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by the7winds on 27.11.16.
 */
public class Client {

    protected String rawCommand;
    private DataInput dataInput;
    private Printer printer;

    public Client(InputStream inputStream, PrintStream printStream) throws IOException, InterruptedException {
        dataInput = new DataInputStream(inputStream);
        printer = new Printer(printStream);
    }

    public final void waitForAction() throws IOException {
        rawCommand = dataInput.readLine();
    }

    public UnitScript getUnitScript() {
        return game -> {
            Utils.Command command;
            for (Class<?> clazz : Utils.Command.class.getClasses()) {
                try {
                    command = (Utils.Command) clazz.getConstructors()[0].newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    return;
                }

                if (command.deserialize(rawCommand)) {
                    command.execute(game);
                    return;
                }
            }
        };
    }

    public Printer getPrinter() {
        return printer;
    }

    // create notify method to provide err message info during the game
}
