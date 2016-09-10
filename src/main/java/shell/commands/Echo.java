package shell.commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by the7winds on 08.09.16.
 */

public class Echo extends Command {

    public Echo(String[] args) throws IOException {
        super(args);
    }

    @Override
    public void execute() throws IOException {
        String echo = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
        outputStream.write(echo.getBytes());
        outputStream.write("\n".getBytes());
    }
}
