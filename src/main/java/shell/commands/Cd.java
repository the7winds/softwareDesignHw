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

    @Override
    void execute() throws Exception {
        String directory_name = args[1];

        File directory;       // Desired current working directory

        directory = new File(directory_name).getAbsoluteFile();
        if (directory.exists() || directory.mkdirs())
        {
            System.setProperty("user.dir", directory.getAbsolutePath());
        }

        outputStream.write((System.getProperty("user.dir") + "\n").getBytes());
    }
}
