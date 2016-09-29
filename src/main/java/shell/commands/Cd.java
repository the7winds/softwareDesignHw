package shell.commands;

import java.io.File;
import java.io.IOException;

/**
 * Created by Anastasia on 24.09.2016.
 */

public class Cd extends Command {

    public Cd(String[] args) throws IOException {
        super(args);
    }

    /**
     * goes into directory in first arg
     * @throws Exception
     */

    @Override
    void execute() throws Exception {
        String directoryName = args[1];

        File directory;       // Desired current working directory

        directory = new File(directoryName).getAbsoluteFile();
        if (directory.exists())
        {
            System.setProperty("user.dir", directory.getAbsolutePath());
        }

        outputStream.write((System.getProperty("user.dir") + "\n").getBytes());
    }
}
