package shell;

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

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                System.out.print(PROMPT);
                String rawInput = bufferedReader.readLine();
                if (rawInput.equals("exit")) {
                    return;
                } else {
                    process(rawInput);
                }
            } catch (SyntaxException e) {
                System.out.println("syntax error");
            }
        }
    }

    private static void process(String rawInput) throws SyntaxException {
        List<RawToken> rawTokens = Tokeniser.rawTokenise(rawInput);
        List<Token> tokens = Tokeniser.tokenise(rawTokens);
        List<CommandNode> commandNodes = Parser.parse(tokens);
        // Chain chain = new Chain(commandNodes);
        // chain.run();
    }
}
