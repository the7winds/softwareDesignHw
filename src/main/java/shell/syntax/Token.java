package shell.syntax;

import shell.Environment;

/**
 * Created by the7winds on 10.09.16.
 */

public class Token {

    public enum Type {
        CMD,
        ARG,
        PIPE,
        ASSIGN
    }

    private String token;
    private final Type type;

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }

    public void expand() {
        if (type == Type.ARG) {
            if (token.charAt(0) == '\"') {
                token = replaceDollar(token.substring(1, token.length() - 1));
            }
            if (token.charAt(0) == '\'') {
                token = token.substring(1, token.length() - 1);
            }
        }
    }

    private String replaceDollar(String token) {
        for (int start = 0, end = 0; start < token.length();) {
            if (token.charAt(start) == '$') {
                end = start + 1;
                while (end < token.length() && Character.isLetterOrDigit(token.charAt(end))) {
                    end++;
                }
                String variable = token.substring(start + 1, end);
                token = token.substring(0, start)
                        + Environment.getInstance().getValue(variable)
                        + token.substring(end);
            }
        }

        return token;
    }
}
