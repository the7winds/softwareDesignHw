package shell.syntax;

import shell.Environment;

import java.util.*;

/**
 * Created by the7winds on 10.09.16.
 */

/**
 * provides methods to split input string into token entities
 */

public class Tokenizer {

    static final char END = '#';

    /**
     * gets string and separates it by '|' or ' ' if it wasn't quoted
     * @throws SyntaxException when it has problems with quotation
     */
    public static List<String> rawTokenize(String rawInput) throws SyntaxException {
        List<String> rawTokens = new LinkedList<>();

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
                        rawTokens.add(rawInput.substring(start, end));
                    }
                    if (rawInput.charAt(end) == '|') {
                        rawTokens.add("|");
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
            rawTokens.add(rawInput.substring(start, end - 1));
        }

        if (quoted != 0) {
            throw new SyntaxException();
        }

        return rawTokens;
    }

    /**
     * gets splited input and interpreters it like commands, args, pipes and assignment
     */
    public static List<Token> tokenize(List<String> rawTokens) {
        List<Token> tokens = new LinkedList<>();

        Token.Type type = Token.Type.CMD;

        for (String rawToken : rawTokens) {
            String string = rawToken;

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
                case ASSIGN:
                    type = Token.Type.CMD;
            }
        }

        return tokens;
    }

    /**
     * checks input whether it is an assignment
     */
    public static boolean isAssignment(String string) {
        int eqIndex = string.indexOf('=');
        return eqIndex != -1 && string.substring(0, eqIndex).chars().allMatch(Character::isLetterOrDigit);
    }

    /**
     * substitutes variable value to string if input is an assignment,
     * changes only value part else changes all
     * @throws SyntaxException when there are problems with quotation
     */
    public static String substitution(String string) throws SyntaxException {
        if (!isAssignment(string)) {
            string = rawSubstitution(string);
        } else {
            int eqIdx = string.indexOf('=');
            string = string.substring(0, eqIdx) + "=" + rawSubstitution(string.substring(eqIdx + 1));
        }
        return string;
    }

    /**
     * substitutes variable values to string without any string checking
     * @throws SyntaxException when quoting isn't finished
     */
    public static String rawSubstitution(String string) throws SyntaxException {

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
                        isQuoted = true;
                        isStrong = true;
                        start = end++;
                    } else if (string.charAt(end) == weak) {
                        stringBuilder.append(string.substring(start, end));
                        isQuoted = !isQuoted;
                        start = end++;
                    } else if (string.charAt(end) == '$') {
                        isVariable = true;
                        stringBuilder.append(string.substring(start, end));
                        start = ++end;
                    } else {
                        end++;
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
                    isQuoted = false;
                    isStrong = false;
                    stringBuilder.append(string.substring(start, end));
                    start = end++;
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

    /**
     * removes quotes from string if it wasn't quoted
     * @throws SyntaxException - when quotation isn't finished
     */
    public static String noQuotes(String string) throws SyntaxException {

        final Set<Character> quotes = new HashSet<>(Arrays.asList('\'', '\"'));

        char isQuoted = 0;

        StringBuilder stringBuilder = new StringBuilder();

        int start = 0;
        int end = 0;

        while (end < string.length()) {
            if (isQuoted == 0) {
                if (quotes.contains(string.charAt(end))) {
                    stringBuilder.append(string.substring(start, end));
                    isQuoted = string.charAt(end);
                    start = ++end;
                } else {
                    ++end;
                }
            } else {
                if (isQuoted == string.charAt(end)) {
                    stringBuilder.append(string.substring(start, end));
                    isQuoted = 0;
                    start = ++end;
                } else {
                    ++end;
                }
            }
        }

        stringBuilder.append(string.substring(start, end));

        if (isQuoted != 0) {
            throw new SyntaxException();
        }

        return stringBuilder.toString();
    }

    /**
     * makes first tokenization, then substitutes, then makes second tokenization and removes quotes
     * @throws SyntaxException - when it contains some syntax mistakes like unfinished quotation
     */
    public static List<String> tokenize2level(String rawInput) throws SyntaxException {
        List<String> rawTokens1 = Tokenizer.rawTokenize(rawInput);
        StringJoiner stringJoiner = new StringJoiner(" ");

        for (String rawToken : rawTokens1) {
            stringJoiner.add(Tokenizer.substitution(rawToken));
        }

        List<String> tmp = Tokenizer.rawTokenize(stringJoiner.toString());
        List<String> rawTokens2 = new LinkedList<>();

        for (String rawToken : tmp) {
            rawTokens2.add(Tokenizer.noQuotes(rawToken));
        }

        return rawTokens2;
    }
}
