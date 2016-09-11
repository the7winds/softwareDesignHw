package shell.syntax;

import org.junit.Test;
import shell.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by the7winds on 10.09.16.
 */

public class TokenizerTest {

    @Test
    public void substitution() throws Exception {
        Environment.getInstance().variableAssignment("a", "42");
        assertEquals("\'$a\'", Tokenizer.rawSubstitution("\'$a\'"));
        assertEquals("\"42\"", Tokenizer.rawSubstitution("\"$a\""));
        assertEquals("42", Tokenizer.rawSubstitution("$a"));
        assertEquals("a=\"echo \"42\"\"", Tokenizer.rawSubstitution("a=\"echo \"$a\"\""));
        assertEquals("a=\"echo \'42\'\"", Tokenizer.rawSubstitution("a=\"echo \'$a\'\""));
    }

    @Test
    public void rawSubstitution() throws Exception {
        Environment.getInstance().variableAssignment("a", "42");
        assertEquals("\'$a\'", Tokenizer.rawSubstitution("\'$a\'"));
        assertEquals("\"42\"", Tokenizer.rawSubstitution("\"$a\""));
        assertEquals("42", Tokenizer.rawSubstitution("$a"));
    }

    @Test
    public void noQuotes() throws Exception {
        assertEquals("as\'dasd", Tokenizer.noQuotes("\"as\'d\"asd"));
        assertEquals("asdasd", Tokenizer.noQuotes("\"as\"da\'sd\'"));
        assertEquals("as\'das\'d", Tokenizer.noQuotes("\"as\'\"d\"as\'d\""));
    }

    @Test
    public void tokenize2level() throws Exception {
        Environment.getInstance().variableAssignment("a", "42");
        Environment.getInstance().variableAssignment("b", "echo $a");

        List<String> tokens = Arrays.asList("echo", "$a", "|", "cat");
        List<String> rawTokens = Tokenizer.tokenize2level("$b | cat");

        assertEquals(tokens, rawTokens);
    }

    @Test
    public void summarizeTest() throws SyntaxException {
        Environment.getInstance().variableAssignment("a", "4");
        Environment.getInstance().variableAssignment("b", "2");
        Environment.getInstance().variableAssignment("c", "42");

        List<Token> tokens = Tokenizer.tokenize(Tokenizer.rawTokenize("echo a$b | cat | echo $c'$c' $c | wc"));

        assertEquals(Arrays.asList(Token.Type.CMD, Token.Type.ARG,
                Token.Type.PIPE, Token.Type.CMD,
                Token.Type.PIPE, Token.Type.CMD, Token.Type.ARG, Token.Type.ARG,
                Token.Type.PIPE, Token.Type.CMD),
                tokens.stream()
                        .map(Token::getType)
                        .collect(Collectors.toList()));
    }

    @Test(expected = SyntaxException.class)
    public void failTest() throws SyntaxException {
        Environment.getInstance().variableAssignment("a", "4");
        Environment.getInstance().variableAssignment("b", "2");
        Environment.getInstance().variableAssignment("c", "42");

        Tokenizer.tokenize(Tokenizer.rawTokenize("echo \'a\"$b | cat | echo $c'$c' $c | wc"));
    }

    @Test
    public void tokenize() throws Exception {
        Environment.getInstance().variableAssignment("a", "42");

        assertEquals(Arrays.asList(Token.Type.CMD, Token.Type.ARG),
                Tokenizer.tokenize(Arrays.asList("echo", "$a"))
                        .stream()
                        .map(Token::getType)
                        .collect(Collectors.toList()));

        assertEquals(Arrays.asList(Token.Type.CMD, Token.Type.ARG, Token.Type.PIPE, Token.Type.CMD),
                Tokenizer.tokenize(Arrays.asList("echo", "$a", "|", "cat"))
                        .stream()
                        .map(Token::getType)
                        .collect(Collectors.toList()));
    }

    @Test
    public void rawTokenize() throws Exception {
        List<String> rawTokens;
        List<String> tokens;

        rawTokens = Tokenizer.rawTokenize("echo a");
        tokens = Arrays.asList("echo", "a");
        assertEquals(tokens, rawTokens);

        rawTokens = Tokenizer.rawTokenize("ec\"ho\" a");
        tokens = Arrays.asList("ec\"ho\"", "a");
        assertEquals(tokens, rawTokens);

        rawTokens = Tokenizer.rawTokenize("ec\"ho\" \'a b c\'");
        tokens = Arrays.asList("ec\"ho\"", "\'a b c\'");
        assertEquals(tokens, rawTokens);

        rawTokens = Tokenizer.rawTokenize("ec\"ho\" a b    c");
        tokens = Arrays.asList("ec\"ho\"", "a", "b", "c");
        assertEquals(tokens, rawTokens);

        rawTokens = Tokenizer.rawTokenize("ec\"ho\" a|cat");
        tokens = Arrays.asList("ec\"ho\"",  "a", "|", "cat");
        assertEquals(tokens, rawTokens);

        rawTokens = Tokenizer.rawTokenize("ec\"ho\" a|c'a't");
        tokens = Arrays.asList("ec\"ho\"", "a", "|", "c'a't");
        assertEquals(tokens, rawTokens);
    }
}