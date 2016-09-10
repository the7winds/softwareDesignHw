package shell;

import shell.commands.*;
import shell.syntax.CommandNode;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by the7winds on 07.09.16.
 */
public class Environment {

    private static final Environment INSTANCE = new Environment();

    private Map<String, String> variables = new Hashtable<>();
    private Map<String, Class<?>> commands = new Hashtable<>();

    public Environment() {
        commands.put("$=", Assignment.class);
        commands.put("echo", Echo.class);
        commands.put("cat", Cat.class);
        commands.put("wc", Wc.class);
    }

    public static Environment getInstance() {
        return INSTANCE;
    }

    public void variableAssignment(String variable, String value) {
        variables.put(variable, value);
    }

    public String getValue(String variable) {
        return variables.getOrDefault(variable, "");
    }

    public Command getCommand(CommandNode commandNode) throws IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        if (commands.containsKey(commandNode.getArgs()[0])) {
            return (Command) commands.get(commandNode.getArgs()[0]).getConstructors()[0].newInstance((Object) commandNode.getArgs());
        } else {
            return new Other(commandNode.getArgs());
        }
    }
}
