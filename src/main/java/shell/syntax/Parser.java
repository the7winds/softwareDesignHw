package shell.syntax;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by the7winds on 08.09.16.
 */

public class Parser {

    public static List<CommandNode> parse(List<Token> tokens) {
        List<CommandNode> commandNodes = new LinkedList<>();
        List<String> argsList = new LinkedList<>();

        for (Token token : tokens) {
            if (token.getType() == Token.Type.PIPE) {
                commandNodes.add(new CommandNode(argsList.toArray(new String[argsList.size()])));
                argsList = new LinkedList<>();
            } else {
                argsList.add(token.getToken());
            }
        }

        if (!argsList.isEmpty()) {
            commandNodes.add(new CommandNode(argsList.toArray(new String[argsList.size()])));
        }

        return commandNodes;
    }
}
