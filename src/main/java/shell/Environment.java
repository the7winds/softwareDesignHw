package shell;

import shell.commands.*;
import shell.syntax.CommandNode;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private Path curDir = Paths.get(System.getProperty("user.dir"));

    /**
     * inits map: command name -> command class
     */
    public Environment() {
        commands.put("grep", Grep.class);
        commands.put("$=", Assignment.class);
        commands.put("echo", Echo.class);
        commands.put("cat", Cat.class);
        commands.put("pwd", Pwd.class);
        commands.put("wc", Wc.class);
        commands.put("ls", Ls.class);
        commands.put("cd", Cd.class);
    }

    public static Environment getInstance() {
        return INSTANCE;
    }

    /**
     * getter for current shell path
     *
     * @return current path
     */
    public Path getCurDir() {
        return curDir;
    }

    /**
     * change current path with new suffix
     *
     * @param changeDir
     */
    public void changeDir(String changeDir) {
        curDir = curDir.resolve(changeDir);
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
