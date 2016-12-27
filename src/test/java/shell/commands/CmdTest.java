package shell.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import shell.Shell;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

/**
 * Created by liza on 27.12.16.
 */
public class CmdTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testCdLs() throws Exception {
        Shell.runOneCmd("cd .");
        Shell.runOneCmd("ls");
        Shell.runOneCmd("cd ..");
    }

    @Test
    public void testCdLs1() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        Shell.runOneCmd("cd " + folder.getRoot().getAbsolutePath());
        Shell.runOneCmd("ls");
        File file1 = folder.newFolder("meowFile1");
        Shell.runOneCmd("ls");
        Shell.runOneCmd("cd ./meowFile1");
        Shell.runOneCmd("cd fkjhkjhk");

        assertEquals(file1.getAbsolutePath() + "\n" +
                "./meowFile1 is not a directory\n" +
                "fkjhkjhk is not a directory\n", out.toString());
    }
}