package shell.commands;

import shell.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Implements <i> ls </i> command.
 */

public class Ls extends Command {
    public Ls(String[] args) throws IOException {
        super(args);
    }

    /**
     * If in args there were no directories, prints all files and subdirectories of current directory.
     * Otherwise prints content (files ans subdirectories) of all directories given in args.
     */
    @Override
    public void execute() throws Exception {
        Path currentPath = Environment.getInstance().getCurrentPath();
        if (args.length == 1) {
            outputStream.write((String.join(" ", Arrays.asList(currentPath.toFile().list())) + "\n").getBytes());
            return;
        }
        StringBuilder result = new StringBuilder();
        boolean toPrintDirectoryName = false;
        if (args.length != 2) {
            toPrintDirectoryName = true;
        }
        for (int i = 1; i < args.length; i++) {
            if (toPrintDirectoryName) {
                result.append(args[i] + ":\n");
            }
            File file = new File(args[i]);
            if (!file.isAbsolute()) {
                file = currentPath.resolve(args[i]).toFile();
            }
            if (file.isDirectory()) {
                result.append(String.join(" ", Arrays.asList(file.list())) + "\n");
            }
            if (i != args.length - 1) {
                result.append("\n");
            }
        }
        outputStream.write(result.toString().getBytes());
    }
}
