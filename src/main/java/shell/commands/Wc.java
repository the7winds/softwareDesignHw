package shell.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by the7winds on 08.09.16.
 */

public class Wc extends Command {

    public Wc(String[] args) throws IOException {
        super(args);
    }

    @Override
    public void execute() throws FileNotFoundException {
        try (PrintStream printStream = new PrintStream(outputStream)) {
            if (args.length == 1) {
                Scanner scanner = new Scanner(inputStream);
                String line = scanner.nextLine();
                printStream.printf("%d\t%d\t%d\n"
                        , 1
                        , Arrays.stream(line.split("\\s+")).filter(s -> s.length() > 0).count()
                        , line.getBytes().length);
            } else {
                for (int i = 1; i < args.length; i++) {
                    File file = new File(args[i]);
                    try (Scanner scanner = new Scanner(file)) {
                        int lines = 0;
                        int words = 0;
                        long bytes = file.length();

                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            lines++;
                            words += Arrays.stream(line.split("\\s+")).filter(s -> s.length() > 0).count();
                        }

                        printStream.printf("%d\t%d\t%d\t%s\n", lines, words, bytes, args[i]);
                    }
                }
            }
        }
    }
}
