package shell.commands;

import java.io.File;
import java.io.IOException;

/**
 * Created by Anastasia on 23.09.2016.
 */

public class Ls extends Command {
    public Ls(String[] args) throws IOException {
        super(args);
    }

    /**
     * prints files in current directory
     * @throws Exception
     */
    @Override
    void execute() throws Exception {
        String result = "";
        String directory = System.getProperty("user.dir");

        for (String file : (new File(directory)).list())
            result += file + "\n";

        outputStream.write((result).getBytes());
    }
}
