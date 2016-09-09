package shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by the7winds on 07.09.16.
 */

public class Shell {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String rawInput = bufferedReader.readLine();
        while (!rawInput.equals("exit")) {
            // parse raw input
            rawInput = bufferedReader.readLine();
        }
    }
}
