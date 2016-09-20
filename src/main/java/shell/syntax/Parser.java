package shell.syntax;

import shell.commands.Assignment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by the7winds on 08.09.16.
 */

/**
 * provide method to get AST tree from  user's input
 */

public class Parser {

    /**
     * create some kind of AST: groups command with it's args
     * @throws SyntaxException often when it has problems with quotes
     */
    public static List<CommandNode> parse(List<Token> tokens) throws SyntaxException {
        List<CommandNode> commandNodes = new LinkedList<>();
        List<String> argsList = new LinkedList<>();

        for (Token token : tokens) {
            if (token.getType() == Token.Type.PIPE) {
                commandNodes.add(new CommandNode(argsList.toArray(new String[argsList.size()])));
                argsList = new LinkedList<>();
            } else if (token.getType() == Token.Type.ASSIGN) {
                argsList.add(Assignment.TAG);
                int eqIdx = token.getToken().indexOf('=');
                argsList.add(token.getToken().substring(0, eqIdx));
                argsList.add(token.getToken().substring(eqIdx + 1));
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
