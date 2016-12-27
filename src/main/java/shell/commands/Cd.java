package shell.commands;

import shell.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class for command "cd" with 0 or 1 arguments
 */
public class Cd extends Command {

    public Cd(String[] args) throws IOException {
        super(args);
    }

    @Override
    void execute() throws Exception {
        Environment environment = Environment.getInstance();
        if (args.length == 1) {
            environment.setCurrentPath(Paths.get("").toAbsolutePath());
        } else {
            Path newPath;
            Path currentPath = environment.getCurrentPath();
            String newPathValue = args[1];
            File newFile = new File(newPathValue);
            if (newFile.isAbsolute()) {
                newPath = newFile.toPath();
            } else {
                newPath = currentPath.resolve(newPathValue);
            }

            if (Files.exists(newPath) && Files.isDirectory(newPath)) {
                environment.setCurrentPath(newPath);
            }
        }
    }
}
