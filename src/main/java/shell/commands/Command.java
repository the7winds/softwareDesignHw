package shell.commands;

import java.io.*;

/**
 * Created by the7winds on 07.09.16.
 */

public abstract class Command {

    protected String args[];

    protected InputStream inputStream = new PipedInputStream();
    protected PipedOutputStream inputStreamOnWrite = new PipedOutputStream();

    protected OutputStream outputStream = new PipedOutputStream();
    protected PipedInputStream outputStreamOnRead = new PipedInputStream();

    public Command(String[] args) throws IOException {
        this.args = args;
        ((PipedInputStream) inputStream).connect(inputStreamOnWrite);
        ((PipedOutputStream) outputStream).connect(outputStreamOnRead);
    }

    public InputStream getOutputStreamOnRead() {
        return outputStreamOnRead;
    }

    public OutputStream getInputStreamOnWrite() {
        return inputStreamOnWrite;
    }

    abstract void execute() throws Exception;
}
