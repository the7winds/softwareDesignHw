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

/**
 * Contains all shell data like variables and command's name mapping.
 * Provides methods to assign variables and get command class by name.
 */

public class Environment {

    private static final Environment INSTANCE = new Environment();

    private Map<String, String> variables = new Hashtable<>();
    private Map<String, Class<?>> commands = new Hashtable<>();

    /**
     * inits map: command name -> command class
     */
    public Environment() {
        commands.put("$=", Assignment.class);
        commands.put("echo", Echo.class);
        commands.put("cat", Cat.class);
        commands.put("pwd", Pwd.class);
        commands.put("wc", Wc.class);
    }

    public static Environment getInstance() {
        return INSTANCE;
    }

    /**
     * modifies variable's value
     */
    public void variableAssignment(String variable, String value) {
        variables.put(variable, value);
    }

    /**
     * returns value of variable, empty string if variable don't existed
     */
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
