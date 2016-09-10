package shell;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by the7winds on 07.09.16.
 */
public class Environment {

    private static final Environment INSTANCE = new Environment();

    private Map<String, String> variables = new Hashtable<>();

    public static Environment getInstance() {
        return INSTANCE;
    }

    public void variableAssignment(String variable, String value) {
        variables.put(variable, value);
    }

    public String getValue(String variable) {
        return variables.getOrDefault(variable, "");
    }
}
