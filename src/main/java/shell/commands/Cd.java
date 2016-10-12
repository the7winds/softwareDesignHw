package shell.commands;

import shell.*;

import java.io.*;
import java.nio.file.*;

/**
 * Implements <i> cd </i> command.
 */

public class Cd extends Command {
    public Cd(String[] args) throws IOException {
        super(args);
    }

    /**
     * Change current directory to directory given in args (works and for relative, and for absolute paths).
     */
    @Override
    void execute() throws Exception {
        if (args.length > 1) {
            String resolvePath = args[1];
            Environment environment = Environment.getInstance();
            Path currentPath = environment.getCurrentPath();
            File resolveFile = new File(resolvePath);
            Path newPath;
            if (resolveFile.isAbsolute()) {
                newPath = resolveFile.toPath();
            } else {
                newPath = currentPath.resolve(resolvePath);
            }
            if (Files.exists(newPath) && Files.isDirectory(newPath)) {
                environment.setCurrentPath(newPath);
            }
        }
    }
}
