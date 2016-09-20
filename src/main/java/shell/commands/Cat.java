package shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by the7winds on 08.09.16.
 */

public class Cat extends Command {

    public Cat(String args[]) throws IOException {
        super(args);
    }

    @Override
    public void execute() throws IOException {
        try (PrintStream printStream = new PrintStream(outputStream)) {
            if (args.length == 1) {
                byte[] buffer = new byte[1024];
                while (inputStream.available() > 0) {
                    int read = inputStream.read(buffer);
                    printStream.write(buffer, 0, read);
                }
            } else {
                for (int i = 1; i < args.length; i++) {
                    try (Scanner scanner = new Scanner(new File(args[i]))) {
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            printStream.println(line);
                        }
                    }
                }
            }
        }
    }
}
