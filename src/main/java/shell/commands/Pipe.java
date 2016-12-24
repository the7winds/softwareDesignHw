package shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by the7winds on 10.09.16.
 */

/**
 * class to help with transferring output one program to another
 */

class Pipe extends Command {

    private final boolean close;

    Pipe(boolean close, InputStream inputStream) throws IOException {
        super(new String[0]);
        this.close = close;
        this.inputStream = inputStream;
    }

    Pipe(boolean close, OutputStream outputStream) throws IOException {
        super(new String[0]);
        this.close = close;
        this.outputStream = outputStream;
    }

    Pipe(boolean close, InputStream inputStream, OutputStream outputStream) throws IOException {
        super(new String[0]);
        this.close = close;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    /**
     * transfers bytes from one command to another
     *
     * @throws Exception
     */
    @Override
    void execute() throws Exception {
        byte[] buffer = new byte[1 << 12];

        while (inputStream.available() > 0) {
            int read = inputStream.read(buffer);
            outputStream.write(buffer, 0, read);
        }

        if (close) {
            outputStream.close();
        }
    }
}
