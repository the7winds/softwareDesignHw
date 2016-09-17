package shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by the7winds on 08.09.16.
 */

public class Other extends Command {

    private final ProcessBuilder processBuilder;
    private final Process process;

    public Other(String[] args) throws IOException {
        super(args);
        processBuilder = new ProcessBuilder(args);
        process = processBuilder.start();
    }

    @Override
    public InputStream getOutputStreamOnRead() {
        return process.getInputStream();
    }

    @Override
    public OutputStream getInputStreamOnWrite() {
        return process.getOutputStream();
    }

    /**
     * executes external command
     * @throws Exception
     */
    @Override
    public void execute() throws Exception {
        process.waitFor();
    }
}
