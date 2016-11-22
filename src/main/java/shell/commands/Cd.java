package shell.commands;

import shell.Environment;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by belaevstanislav on 23.11.16.
 */

public class Cd extends Command {
    public Cd(String[] args) throws IOException {
        super(args);
    }

    @Override
    void execute() throws Exception {
        try (PrintStream printStream = new PrintStream(outputStream)) {
            if (args.length > 1) {
                Environment.getInstance().changeDir(args[1]);
            }
        }
    }
}
