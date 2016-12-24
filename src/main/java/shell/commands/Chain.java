package shell.commands;

import shell.Environment;
import shell.syntax.CommandNode;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by the7winds on 08.09.16.
 */

/**
 * create commands list with connected input-output
 */

public class Chain {

    private final LinkedList<Command> commands = new LinkedList<>();

    public Chain(List<CommandNode> commandNodes) throws Exception {

        Pipe pipeInput = new Pipe(true, System.in);
        Command first = pipeInput;
        Command second = null;

        for (CommandNode commandNode : commandNodes) {
            second = Environment.getInstance().getCommand(commandNode);
            connect(first, second);
            first = second;
        }

        Pipe pipeOutput = new Pipe(false, System.out);
        connect(second, pipeOutput);
    }

    /**
     * connects output first command and input second command via Pipe class
     *
     * @throws IOException
     */
    private void connect(Command first, Command second) throws IOException {
        Pipe pipeCommand = new Pipe(true, first.getOutputStreamOnRead(), second.getInputStreamOnWrite());

        if (commands.isEmpty() || commands.getLast() != first) {
            commands.add(first);
        }
        commands.add(pipeCommand);
        commands.add(second);
    }

    public void run() throws Exception {
        for (Command command : commands) {
            command.execute();
        }
    }
}
