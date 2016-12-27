package shell.commands;

import org.junit.Test;
import shell.Shell;

import static org.junit.Assert.*;

/**
 * Created by liza on 27.12.16.
 */
public class CmdTest {
    @Test
    public void testCdLs() throws Exception {
        Shell.runOneCmd("cd .");
        Shell.runOneCmd("ls");
        Shell.runOneCmd("cd ..");
    }

}