package roguelike.entities;

import roguelike.logic.World;

/**
 * Created by the7winds on 29.11.16.
 */
public class WallBlock extends GameObject {

    public WallBlock(World world) {
        super(world);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
