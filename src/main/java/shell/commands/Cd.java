package shell.commands;

import shell.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Change current working directory of shell interpreter.
 * Works similar to original "cd" command.
 *
 * @author belaevstanislav
 */
public class Cd extends Command {
    public Cd(String[] args) throws IOException {
        super(args);
    }

    /**
     * @see Command#execute()
     */
    @Override
    void execute() throws Exception {
        if (args.length > 1) {
            final Path pathToChange = Environment.getInstance().getCurDir().resolve(args[1]);
            if (Files.exists(pathToChange)) {
                Environment.getInstance().changeDir(args[1]);
            } else {
                throw new Exception("No such file or directory");
            }
        }
    }
}
