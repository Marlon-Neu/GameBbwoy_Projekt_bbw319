package ch.bbw.gamebbwoy;

public interface GameObject {
    public Point getPosition();
    public default boolean inBounds(Bounds bounds){
        Point pos = getPosition();
        return (bounds.left()<=pos.x&&pos.x<= bounds.right())&&(bounds.top()<=pos.y&&pos.y<= bounds.bottom());
    }
    public void update();
    public void update(PlayerSquare player);
}
