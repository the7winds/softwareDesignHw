package shell.commands;

import org.junit.Assert;
import org.junit.Test;

import shell.*;
import shell.syntax.CommandNode;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class CommandsTest {

    @Test

    public void testLsAndCd1() throws Exception {

        Cd cd = new Cd(new String[]{"cd", "src"});
        Ls ls = new Ls(new String[]{"ls"});
        cd.execute();
        ls.execute();
        byte[] result = new byte[ls.getOutputStreamOnRead().available()];
        ls.getOutputStreamOnRead().read(result, 0, ls.getOutputStreamOnRead().available());
        assertArrayEquals(result, ("main\ntest\n".getBytes()));

    }
    
    @Test
    public void testLsAndCd2() throws Exception {

        Cd cd1 = new Cd(new String[]{"cd", "src/main"});
        Cd cd2 = new Cd(new String[]{"cd", "src/main"});
        Cd cd3 = new Cd(new String[]{"cd", "src/"});
        Ls ls = new Ls(new String[]{"ls"});
        cd1.execute();
        cd2.execute();
        cd3.execute();
        ls.execute();
        byte[] result = new byte[ls.getOutputStreamOnRead().available()];
        ls.getOutputStreamOnRead().read(result, 0, ls.getOutputStreamOnRead().available());
        assertArrayEquals(result, ("main\ntest\n".getBytes()));
    }
}
