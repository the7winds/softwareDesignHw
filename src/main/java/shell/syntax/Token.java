package shell.syntax;

/**
 * Created by the7winds on 10.09.16.
 */

/**
 * Contains string with type of one syntax entity: CMD, ARG, PIP, ASSIGN
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
}
