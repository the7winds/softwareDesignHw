package shell.commands;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.File;
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
     * @throws Exception
     */

    @Override
    void execute() throws Exception {
        new JCommander(this, args);

        String pattern = other.get(1);
        List<String> filenames = other.subList(2, other.size());

        Map<String, Scanner> files = new HashMap<>();
        for (String filename : filenames) {
            files.put(filename, new Scanner(new File(filename)));
        }

        files.put("stdin", new Scanner(inputStream));
        for (Map.Entry<String, Scanner> entry : files.entrySet()) {
            doGrep(pattern, entry.getKey(), entry.getValue());
        }
    }

    /**
     * search pattern
     * @param pattern
     * @param scanner
     */
    private void doGrep(String pattern, String filename, Scanner scanner) {
        if (ignoreCase) {
            StringBuilder chars = new StringBuilder();
            boolean f = false;
            for (char c : pattern.toCharArray()) {
                if (f) {
                    f = false;
                } else {
                    chars.append(Character.toLowerCase(c));
                    f = c == '\\';
                }
            }
            pattern = chars.toString();
        }
        if (searchWholeWord) {
            pattern = "\\b" + pattern + "\\b";
        }
        pattern = ".*" + pattern + ".*";

        int lcnt = 0;
        int cnt = nStringsAfterMatch;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (ignoreCase) {
                line.toLowerCase();
            }
            if (line.matches(pattern)) {
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
