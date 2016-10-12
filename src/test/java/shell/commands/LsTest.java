package shell.commands;

import org.junit.*;
import shell.*;

import java.nio.file.*;

import static org.junit.Assert.*;

public class LsTest {
    private static final String RESOURCES_PATH = "src/test/resources";

    @Test
    public void lsWithNoArgs() throws Exception {
        Environment environment = Environment.getInstance();
        Path oldPath = environment.getCurrentPath();
        environment.setCurrentPath(oldPath.resolve(RESOURCES_PATH));
        Ls ls = new Ls(new String[] {"ls"});
        ls.execute();
        byte[] b = new byte[ls.getOutputStreamOnRead().available()];
        ls.getOutputStreamOnRead().read(b, 0, ls.getOutputStreamOnRead().available());
        assertArrayEquals("dir1 dir2 text1.txt\n".getBytes(), b);
        ls.getOutputStreamOnRead().close();
        environment.setCurrentPath(oldPath);
    }

    @Test
    public void lsWithOneArg() throws Exception {
        Ls ls = new Ls(new String[] {"ls", RESOURCES_PATH + "/dir1"});
        ls.execute();
        byte[] b = new byte[ls.getOutputStreamOnRead().available()];
        ls.getOutputStreamOnRead().read(b, 0, ls.getOutputStreamOnRead().available());
        assertArrayEquals("innerDir\n".getBytes(), b);
        ls.getOutputStreamOnRead().close();
    }

    @Test
    public void lsWithManyArgs() throws Exception {
        Ls ls = new Ls(new String[] {"ls", RESOURCES_PATH + "/dir1", RESOURCES_PATH});
        ls.execute();
        byte[] b = new byte[ls.getOutputStreamOnRead().available()];
        ls.getOutputStreamOnRead().read(b, 0, ls.getOutputStreamOnRead().available());
        assertArrayEquals(
                (RESOURCES_PATH + "/dir1:\ninnerDir\n\n" + RESOURCES_PATH + ":\ndir1 dir2 text1.txt\n").getBytes(), b);
        ls.getOutputStreamOnRead().close();
    }
}
