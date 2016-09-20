package shell.syntax;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by the7winds on 10.09.16.
 */

public class ParserTest {

    @Test
    public void commandWithOneArgTest() throws Exception {
        List<CommandNode> commandNodes = Parser.parse(Arrays.asList(new Token("echo", Token.Type.CMD), new Token("42", Token.Type.ARG)));
        assertArrayEquals(new String[]{"echo", "42"}, commandNodes.get(0).getArgs());
    }

    @Test
    public void commandSequenceTest() throws SyntaxException {
        List<CommandNode> commandNodes = Parser.parse(Arrays.asList(new Token("echo", Token.Type.CMD), new Token("42", Token.Type.ARG),
                new Token("|", Token.Type.PIPE), new Token("cat", Token.Type.CMD)));
        assertArrayEquals(new String[] { "echo", "42" },  commandNodes.get(0).getArgs());
        assertArrayEquals(new String[] { "cat" },  commandNodes.get(1).getArgs());
    }

    @Test
    public void assignmentWithSpacesTest() throws Exception {
        List<String> rawTokens = Tokenizer.tokenize2level("x=echo 1");
        List<Token> tokens = Tokenizer.tokenize(rawTokens);
        List<CommandNode> commandNodes = Parser.parse(tokens);
    }
}
