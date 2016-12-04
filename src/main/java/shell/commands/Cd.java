package shell.commands;

import shell.Environment;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by liza on 04.12.16.
 */

/**
 * Changes the current directory.
 */
public class Cd extends Command {

    public Cd(String[] args) throws IOException {
        super(args);
    }

    @Override
    void execute() throws Exception {
        Path p = Paths.get(args[1]);
        Environment env = Environment.getInstance();

        if(!Files.isDirectory(p)) {
            PrintStream output = new PrintStream(outputStream);
            output.printf("%s is not a directory\n", p.toString());
            output.flush();
            return;
        }

        if(p.isAbsolute()) {
            env.setPwd(p);
        } else {
            env.setPwd(Paths.get(env.getPwd().toString(), p.toString()));
        }
    }
}
