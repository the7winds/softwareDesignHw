package shell.syntax;

import shell.Environment;

import java.util.*;

/**
 * Created by the7winds on 10.09.16.
 */

public class Tokenizer {

    static final char END = '#';

    public static List<RawToken> rawTokenize(String rawInput) throws SyntaxException {
        List<RawToken> rawTokens = new LinkedList<>();

        final Set<Character> separator = new HashSet<>(Arrays.asList('|', ' '));
        final Set<Character> quotes = new HashSet<>(Arrays.asList('\'', '\"'));

        char quoted = 0;

        rawInput += END;

        int start = 0;
        int end = 0;

        while (end < rawInput.length()) {
            if (quoted == 0) {
                if (!separator.contains(rawInput.charAt(end))) {
                    if (quotes.contains(rawInput.charAt(end))) {
                        quoted = rawInput.charAt(end);
                    }
                    end++;
                } else {
                    if (start != end) {
                        rawTokens.add(new RawToken(rawInput.substring(start, end)));
                    }
                    if (rawInput.charAt(end) == '|') {
                        rawTokens.add(new RawToken("|"));
                    }
                    start = ++end;
                }
            } else {
                if (quoted == rawInput.charAt(end)) {
                    quoted = 0;
                }
                end++;
            }
        }

        if (start != end - 1) {
            rawTokens.add(new RawToken(rawInput.substring(start, end - 1)));
        }

        if (quoted != 0) {
            throw new SyntaxException();
        }

        return rawTokens;
    }

    public static List<Token> tokenize(List<RawToken> rawTokens) throws SyntaxException {
        List<Token> tokens = new LinkedList<>();

        Token.Type type = Token.Type.CMD;

        for (RawToken rawToken : rawTokens) {
            String string = rawToken.getString();

            if (type == Token.Type.CMD && isAssignment(string)) {
                type = Token.Type.ASSIGN;
            }

            if (string.equals("|")) {
                type = Token.Type.PIPE;
            }

            if (!(string.equals("") && type == Token.Type.ARG)) {
                tokens.add(new Token(string, type));
            }

            switch (type) {
                case CMD:
                    type = Token.Type.ARG;
                    break;
                case ARG:
                    type = Token.Type.ARG;
                    break;
                case PIPE:
                    type = Token.Type.CMD;
                    break;
            }
        }

        return tokens;
    }

    public static boolean isAssignment(String string) {
        int eqIndex = string.indexOf('=');
        return eqIndex != -1 && string.substring(0, eqIndex).chars().allMatch(Character::isLetterOrDigit);
    }

    private static String rawExpand(String string) throws SyntaxException {

        final char strong = '\'';
        final char weak = '\"';

        boolean isVariable = false;
        boolean isQuoted = false;
        boolean isStrong = false;

        StringBuilder stringBuilder = new StringBuilder();

        string += END;

        int start = 0;
        int end = 0;

        while (end < string.length()) {
            if (!isStrong) {
                if (!isVariable) {
                    if (!isQuoted && string.charAt(end) == strong) {
                        stringBuilder.append(string.substring(start, end));
                        isQuoted = isStrong = true;
                        start = ++end;
                    } else if (string.charAt(end) == weak) {
                        stringBuilder.append(string.substring(start, end));
                        isQuoted = !isQuoted;
                        start = ++end;
                    } else if (string.charAt(end) == '$') {
                        isVariable = true;
                        stringBuilder.append(string.substring(start, end));
                        start = ++end;
                    } else {
                        ++end;
                    }
                } else {
                    if (Character.isLetterOrDigit(string.charAt(end))) {
                        end++;
                    } else {
                        stringBuilder.append(Environment.getInstance().getValue(string.substring(start, end)));
                        isVariable = false;
                        start = end;
                    }
                }
            } else {
                if (string.charAt(end) == strong) {
                    isQuoted = isStrong = false;
                    stringBuilder.append(string.substring(start, end));
                    start = ++end;
                } else {
                    end++;
                }
            }
        }

        stringBuilder.append(string.substring(start, end));

        if (isQuoted) {
            throw new SyntaxException();
        }

        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    public static String expand(String string) throws SyntaxException {
        if (!isAssignment(string)) {
            string = rawExpand(string);
        } else {
            int eqIdx = string.indexOf('=');
            string = string.substring(0, eqIdx) + "=" + expand(string.substring(eqIdx + 1));
        }
        return string;
    }

    public static List<RawToken> tokenize2level(String rawInput) throws SyntaxException {
        List<RawToken> rawTokens = Tokenizer.rawTokenize(rawInput);
        StringJoiner stringJoiner = new StringJoiner(" ");

        for (RawToken rawToken : rawTokens) {
            stringJoiner.add(Tokenizer.expand(rawToken.getString()));
        }

        rawTokens = Tokenizer.rawTokenize(stringJoiner.toString());

        return rawTokens;
    }
}
