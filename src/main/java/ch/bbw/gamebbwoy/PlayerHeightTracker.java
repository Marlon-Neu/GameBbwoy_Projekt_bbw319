package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.utils.DeltaTime;

public class PlayerHeightTracker implements GameObject{
    private final Point position = new Point(-1,0);
    @Override
    public boolean inBounds(Bounds bounds) {
        return true;
    }
    @Override
    public void update() {
    }
    @Override
    public void update(PlayerSquare player) {
        position.y -= player.getCenterOfGravity().getYSpeed() * player.getTimePerDeltaTime()* DeltaTime.dT();
    }
    @Override
    public Point getPosition() {
        return position;
    }
    public double getPlayerHeight() {
        return position.y;
    }
}
