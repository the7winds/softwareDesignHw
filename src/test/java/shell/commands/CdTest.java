package shell.commands;

import org.junit.*;
import shell.*;

import java.nio.file.*;

import static org.junit.Assert.*;

public class CdTest {
    private static final String RESOURCES_PATH = "src/test/resources";

    @Test
    public void cdWithFewLevels() throws Exception {
        Path oldPath = Environment.getInstance().getCurrentPath();
        Cd cd = new Cd(new String[] {"cd", RESOURCES_PATH + "/dir1/innerDir"});
        cd.execute();
        Ls ls = new Ls(new String[] {"ls"});
        ls.execute();
        byte[] b = new byte[ls.getOutputStreamOnRead().available()];
        ls.getOutputStreamOnRead().read(b, 0, ls.getOutputStreamOnRead().available());
        assertArrayEquals("text2.txt\n".getBytes(), b);
        ls.getOutputStreamOnRead().close();
        Environment.getInstance().setCurrentPath(oldPath);
    }
}
