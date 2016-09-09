package shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by the7winds on 10.09.16.
 */

class Pipe extends Command {

    Pipe(InputStream inputStream, OutputStream outputStream) throws IOException {
        super(new String[0]);
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    void execute() throws Exception {
        byte[] buffer = new byte[1 << 12];

        while (inputStream.available() > 0) {
            int read = inputStream.read(buffer);
            outputStream.write(buffer, 0, read);
        }
    }
}
