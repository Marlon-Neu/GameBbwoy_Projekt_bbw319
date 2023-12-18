package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;

public class ForcesUp extends StaticGameSquare{

    private final GravityPoint centerOfGravity;

    @Override
    public void update() {
        super.update();
        centerOfGravity.update();
        getPosition().x = centerOfGravity.x;
        getPosition().y = centerOfGravity.y;
    }

    public ForcesUp(Point position, double angleInRadians, int size) {
        super(position, angleInRadians, size);
        super.setColor(PixelColor.LOW);
        setStaticIndicatorColor(PixelColor.HIGH);
        centerOfGravity = getCenterOfGravity();
    }

    public ForcesUp(Point position, double angleInRadians, int size,int turnSpeedRatio) {
        super(position, angleInRadians, size,turnSpeedRatio);
        super.setColor(PixelColor.LOW);
        setStaticIndicatorColor(PixelColor.HIGH);
        centerOfGravity = getCenterOfGravity();
    }

    public void interact(PhysicsObject obj) {
        centerOfGravity.setTimePerDeltaTime(getTimePerDeltaTime());
        centerOfGravity.setSignPositive(signPositive());
        centerOfGravity.interact(obj);
        obj.react(centerOfGravity);
    }

    @Override
    public void react(PhysicsObject obj) {
        centerOfGravity.setTimePerDeltaTime(getTimePerDeltaTime());
        centerOfGravity.setSignPositive(signPositive());
        centerOfGravity.react(obj);
    }
}
