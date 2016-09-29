package shell.syntax;

/**
 * Created by the7winds on 10.09.16.
 */

/**
 * combines all command arguments (in C-style with command name)
 */

public class CommandNode {

    private final String[] args;

    public CommandNode(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }
}
