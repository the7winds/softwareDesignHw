package shell.commands;

import shell.*;

import java.io.*;

/**
 * Created by the7winds on 11.09.16.
 */
public class Pwd extends Command {

    public Pwd(String[] args) throws IOException {
        super(args);
    }

    /**
     * prints current directory
     * @throws Exception
     */
    @Override
    void execute() throws Exception {
        outputStream.write((Environment.getInstance().getCurrentPath().toString() + "\n").getBytes());
    }
}
