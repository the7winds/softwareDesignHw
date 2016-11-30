package roguelike.logic;

import java.io.IOException;

/**
 * Created by the7winds on 27.11.16.
 */
public interface UnitScript {

    void execute(Game game) throws IOException;
}
