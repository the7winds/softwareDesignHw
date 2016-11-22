import org.junit.Test;
import shell.syntax.CommandNode;
import shell.syntax.Parser;
import shell.syntax.Token;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by belaevstanislav on 23.11.16.
 */

public class LsCdTest {
    @Test
    public void testLs() throws Exception {
        List<CommandNode> commandNodes1
                = Parser.parse(Arrays.asList(new Token("ls", Token.Type.CMD), new Token(".", Token.Type.ARG)));
        assertArrayEquals(new String[]{"ls", "."}, commandNodes1.get(0).getArgs());

        List<CommandNode> commandNodes2
                = Parser.parse(Arrays.asList(new Token("cd", Token.Type.CMD), new Token("..", Token.Type.ARG)));
        assertArrayEquals(new String[]{"cd", ".."}, commandNodes2.get(0).getArgs());
    }
}
