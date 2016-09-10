package shell.syntax;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by the7winds on 10.09.16.
 */

public class TokeniserTest {

    @org.junit.Test
    public void rawTokeniseTestSize() throws Exception {
        List<RawToken> rawTokenList;

        rawTokenList = Tokeniser.rawTokenise("echo a");
        assertEquals(2, rawTokenList.size());

        rawTokenList = Tokeniser.rawTokenise("ec\"ho\" a");
        assertEquals(2, rawTokenList.size());

        rawTokenList = Tokeniser.rawTokenise("ec\"ho\" \'a b c\'");
        assertEquals(2, rawTokenList.size());

        rawTokenList = Tokeniser.rawTokenise("ec\"ho\" a b    c");
        assertEquals(4, rawTokenList.size());

        rawTokenList = Tokeniser.rawTokenise("ec\"ho\" a|cat");
        assertEquals(4, rawTokenList.size());

        rawTokenList = Tokeniser.rawTokenise("ec\"ho\" a|c'a't");
        assertEquals(4, rawTokenList.size());
    }
}