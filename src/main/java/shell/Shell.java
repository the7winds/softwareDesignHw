package shell;

import shell.commands.Chain;
import shell.syntax.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by the7winds on 07.09.16.
 */

public class Shell {

    private static final String PROMPT = "#";

    /**
     * reads input from keyboard and calls functions to handle it
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                System.out.print(PROMPT);
                String rawInput = bufferedReader.readLine();
                if (rawInput.length() > 0) {
                    if (rawInput.matches("\\s*exit\\s*")) {
                        return;
                    } else {
                        process(rawInput);
                    }
                }
            } catch (SyntaxException e) {
                System.out.println("syntax error");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * handles raw input: parses then builds chain of commands then runs it
     * @param rawInput
     * @throws Exception
     */
    private static void process(String rawInput) throws Exception {
        List<String> rawTokens = Tokenizer.tokenize2level(rawInput);
        List<Token> tokens = Tokenizer.tokenize(rawTokens);
        List<CommandNode> commandNodes = Parser.parse(tokens);
        Chain chain = new Chain(commandNodes);

        chain.run();
    }
}
