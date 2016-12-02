package roguelike.logic;

import java.io.IOException;

/**
 * Created by the7winds on 27.11.16.
 */

/**
 * this is an action that we should apply to game, any
 * alive (Unit) object can influence on
 */
public interface UnitScript {

    void execute(Game game) throws IOException;
}
