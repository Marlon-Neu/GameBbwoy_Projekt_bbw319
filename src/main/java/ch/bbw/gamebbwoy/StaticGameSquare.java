package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.utils.DeltaTime;
import ch.bbw.gamebbwoy.utils.Limit;

public class StaticGameSquare extends CollidingSquare implements PhysicsObject,GameObject {

    private final GravityPoint centerOfGravity;
    private final Square staticIndicator;
    private double turnSpeed=0;
    private final double maxTurnSpeed =Math.PI/12;
    private final double deltaTime = DeltaTime.dT();
    private double timePerDeltaTime =1;
    private double referenceTime=1;
    private double angleInRadians;
    public StaticGameSquare(Point position, double angleInRadians, int size) {
        super(position, angleInRadians, size);
        staticIndicator = new Square(position,Math.PI/4,1);
        this.angleInRadians = angleInRadians;
        centerOfGravity = new GravityPoint(position);
    }

    public StaticGameSquare(Point position, double angleInRadians, int size,double turnSpeed) {
        this(position,angleInRadians,size);
        if(Math.abs(turnSpeed)>=maxTurnSpeed){
            this.turnSpeed = maxTurnSpeed;
        }
        else {
            this.turnSpeed = turnSpeed;
        }
    }

    public StaticGameSquare(Point position, double angleInRadians, int size,int turnSpeedRatio) {
        this(position,angleInRadians,size);
        if(turnSpeedRatio==0){
            this.turnSpeed = maxTurnSpeed;
        }
        else{
            this.turnSpeed = maxTurnSpeed/turnSpeedRatio;
        }
    }

    @Override
    public void update() {
        angleInRadians += turnSpeed*referenceTime*deltaTime*32;
        if(angleInRadians<0){
            angleInRadians += 2*Math.PI;
        }
        if(angleInRadians>=2*Math.PI){
            angleInRadians -= 2*Math.PI;
        }
        super.setAngleInRadians(angleInRadians);
    }

    @Override
    public void update(PlayerSquare player) {
        Point position = getPosition();
        setReferenceTime(player.getTimePerDeltaTime());
        centerOfGravity.setTimePerDeltaTime(timePerDeltaTime);
        centerOfGravity.update(player);
        position.x = centerOfGravity.x;
        position.y = centerOfGravity.y;
    }

    public GravityPoint getCenterOfGravity() {
        return centerOfGravity;
    }

    @Override
    public double getTimePerDeltaTime() {
        double minTurnSpeed = maxTurnSpeed / 10;
        return maxTurnSpeed/Math.min(Math.abs(turnSpeed),minTurnSpeed);
    }

    @Override
    public boolean signPositive() {
        return turnSpeed >= 0;
    }

    public void setReferenceTime(double timePerDeltaTime) {
        this.referenceTime = this.timePerDeltaTime/Limit.minDouble(timePerDeltaTime, DeltaTime.minTpDT());
    }

    public void setStaticIndicatorColor(PixelColor color){
        staticIndicator.setColor(color);
    }

    @Override
    public void interact(PhysicsObject obj) {
        centerOfGravity.setTimePerDeltaTime(getTimePerDeltaTime());
        centerOfGravity.setSignPositive(signPositive());
        obj.react(centerOfGravity);
    }

    @Override
    public void react(PhysicsObject obj) {
    }

    @Override
    public void tick(PixelDisplay graphic) {
        super.tick(graphic);
        staticIndicator.tick(graphic);
    }
}
