package shell.syntax;

import java.util.*;

/**
 * Created by the7winds on 10.09.16.
 */

public class Tokeniser {

    public static List<RawToken> rawTokenise(String rawInput) {
        List<RawToken> rawTokens = new LinkedList<>();

        final Set<Character> separator = new HashSet<>(Arrays.asList('|', ' '));
        final Set<Character> quotes = new HashSet<>(Arrays.asList('\'', '\"'));

        boolean quoted = false;

        rawInput = rawInput + ' ';

        for (int start = 0, end = 0; end < rawInput.length();) {
            if (!quoted) {
                if (!separator.contains(rawInput.charAt(end))) {
                    if (quotes.contains(rawInput.charAt(end))) {
                        quoted = true;
                    }
                    end++;
                } else {
                    if (start != end) {
                        rawTokens.add(new RawToken(rawInput.substring(start, end)));
                        if (rawInput.charAt(end) == '|') {
                            rawTokens.add(new RawToken("|"));
                        }
                    }
                    start = ++end;
                }
            } else {
                if (quotes.contains(rawInput.charAt(end))) {
                    quoted = false;
                }
                end++;
            }
        }

        return rawTokens;
    }
}
