package roguelike.entities;

import roguelike.logic.World;

/**
 * Created by the7winds on 27.11.16.
 */
public class EmptyBlock extends GameObject {

    public EmptyBlock(World world) {
        super(world);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
