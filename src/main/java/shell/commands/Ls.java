package shell.commands;


import shell.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Class for command "ls" without arguments
 */
public class Ls extends Command {

    public Ls(String[] args) throws IOException {
        super(args);
    }

    @Override
    void execute() throws Exception {

        File curFolder = Environment.getInstance().getCurrentPath().toFile();
        File[] filesList = curFolder.listFiles();

        for (File f : filesList) {
            if (f.isFile() || f.isDirectory()) {
                outputStream.write((f.getName() + '\n').getBytes());
            }
        }
    }
}
