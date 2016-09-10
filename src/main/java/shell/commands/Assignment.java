package shell.commands;

import shell.Environment;

import java.io.IOException;

/**
 * Created by the7winds on 10.09.16.
 */

public class Assignment extends Command {

    public static final String TAG = "$=";

    public Assignment(String[] args) throws IOException {
        super(args);
    }

    @Override
    void execute() throws Exception {
        Environment.getInstance().variableAssignment(args[1], args[2]);
    }
}
