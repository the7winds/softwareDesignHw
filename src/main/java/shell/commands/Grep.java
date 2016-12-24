package shell.commands;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import shell.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

/**
 * Created by the7winds on 17.09.16.
 */

public class Grep extends Command {

    private PrintStream printStream;

    public Grep(String[] args) throws IOException {
        super(args);
        printStream = new PrintStream(outputStream);
    }

    @Parameter(names = "-i")
    private boolean ignoreCase = false;

    @Parameter(names = "-w")
    private boolean searchWholeWord = false;

    @Parameter(names = "-A")
    private int nStringsAfterMatch = 0;

    @Parameter
    private List<String> other = new LinkedList<>();

    /**
     * handles parses grep args and calls doGrep to search
     *
     * @throws Exception - exactly FileNotFoundException
     */

    @Override
    void execute() throws FileNotFoundException {
        new JCommander(this, args);

        String pattern = other.get(1);
        List<String> filenames = other.subList(2, other.size());

        Map<String, Scanner> files = new HashMap<>();
        for (String filename : filenames) {
            final File file = Environment.getInstance().getCurDir().resolve(filename).toFile();
            files.put(filename, new Scanner(file));
        }

        files.put("stdin", new Scanner(inputStream));
        for (Map.Entry<String, Scanner> entry : files.entrySet()) {
            doGrep(pattern, entry.getKey(), entry.getValue());
        }
    }

    /**
     * search pattern
     *
     * @param inputPattern user's input pattern
     * @param scanner      scanner associated with one of input files or standard input
     */
    private void doGrep(String inputPattern, String filename, Scanner scanner) {
        if (ignoreCase) {
            StringBuilder chars = new StringBuilder();
            boolean f = false;
            for (char c : inputPattern.toCharArray()) {
                if (f) {
                    f = false;
                } else {
                    chars.append(Character.toLowerCase(c));
                    f = c == '\\';
                }
            }
            inputPattern = chars.toString();
        }
        if (searchWholeWord) {
            inputPattern = "\\b" + inputPattern + "\\b";
        }
        String inlinePattern = ".*" + inputPattern + ".*";

        int lcnt = 0;
        int cnt = nStringsAfterMatch;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (ignoreCase) {
                line.toLowerCase();
            }
            if (line.matches(inlinePattern)) {
                cnt = nStringsAfterMatch;
                printStream.printf("%s(%d):%s\n", filename, lcnt, line);
            } else {
                if (cnt > 0) {
                    printStream.printf("%s(%d):%s\n", filename, lcnt, line);
                }
                cnt--;
            }
            ++lcnt;
        }
    }
}
