package shell.commands;

import shell.Environment;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by liza on 04.12.16.
 */

/**
 * Prints all the entries in the current directory.
 */
public class Ls extends Command {

    public Ls(String[] args) throws IOException {
        super(args);
    }

    @Override
    void execute() throws Exception {
        Environment env = Environment.getInstance();
        Path p = env.getPwd();

        if(!Files.isDirectory(p)) {
            return;
        }

        PrintStream output = new PrintStream(outputStream);
        Files.list(p).forEach(entry -> output.println(entry.toString()));
        output.flush();
    }
}
