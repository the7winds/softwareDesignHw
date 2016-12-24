package shell.commands;

import shell.Environment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * List files and dirs of current working directory of shell interpreter.
 * Works similar to original "ls" command.
 *
 * @author belaevstanislav
 */
public class Ls extends Command {
    public Ls(String[] args) throws IOException {
        super(args);
    }

    private void doLs(final Path path, final PrintStream printStream) throws IOException {
        Files.walk(path, 1)
                .skip(1)
                .filter(p -> !Objects.equals(p.getFileName().toString(), ""))
                .filter(p -> !p.getFileName().toString().startsWith("."))
                .forEach(p -> printStream.println(p.getFileName().toString()));
    }

    /**
     * @throws Exception when exception occur while working with files
     * @see Command#execute()
     */
    @Override
    void execute() throws Exception {
        try (final PrintStream printStream = new PrintStream(outputStream)) {
            if (args.length == 1) {
                String result = new BufferedReader(new InputStreamReader(inputStream))
                        .lines().collect(Collectors.joining("\n"));
                if (!Objects.equals(result, "")) {
                    doLs(Paths.get(result), printStream);
                } else {
                    doLs(Environment.getInstance().getCurDir(), printStream);
                }
            } else {
                final Path path = Environment.getInstance().getCurDir().resolve(args[1]);
                doLs(path, printStream);
            }
        }
    }
}
