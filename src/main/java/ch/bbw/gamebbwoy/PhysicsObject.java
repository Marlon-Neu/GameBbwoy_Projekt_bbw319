package ch.bbw.gamebbwoy;

public interface PhysicsObject {
    public Point getPosition();
    public double getTimePerDeltaTime();
    public int getSize();
    public boolean signPositive();
    public void interact(PhysicsObject obj);
    public void react(PhysicsObject obj);
}
