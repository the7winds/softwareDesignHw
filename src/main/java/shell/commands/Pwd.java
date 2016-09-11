package shell.commands;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by the7winds on 11.09.16.
 */
public class Pwd extends Command {

    public Pwd(String[] args) throws IOException {
        super(args);
    }

    @Override
    void execute() throws Exception {
        outputStream.write((Paths.get(".").toAbsolutePath().toString() + "\n").getBytes());
    }
}
