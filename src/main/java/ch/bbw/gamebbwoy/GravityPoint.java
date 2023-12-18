package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.utils.DeltaTime;
import ch.bbw.gamebbwoy.utils.Limit;

public class GravityPoint extends Point implements PhysicsObject,GameObject{

    private double xSpeed=0;
    private double ySpeed=0;
    private double xAccel=0;
    private double yAccel=0;
    private final double minTimePerDeltaTime = DeltaTime.minTpDT();
    private final double maxSpeed = 36;
    private final double deltaTime = DeltaTime.dT();
    private double timePerDeltaTime = 1;
    private double referenceTime = 1;

    private boolean signPositive = true;
    public GravityPoint(double x, double y) {
        super(x, y);
    }
    public GravityPoint(Point point){super(point.x, point.y);}

    public void limitSpeed(){
        xSpeed = Limit.maxDouble(xSpeed,maxSpeed);
        ySpeed = Limit.maxDouble(ySpeed,maxSpeed);
    }

    @Override
    public void update(){
        xSpeed = Limit.maxDouble(xSpeed+xAccel*deltaTime,maxSpeed);
        ySpeed = Limit.maxDouble(ySpeed+yAccel*deltaTime,maxSpeed);

        x += xSpeed*referenceTime*deltaTime;
        y += ySpeed*referenceTime*deltaTime;
    }


    @Override
    public void update(PlayerSquare player) {
        GravityPoint gravityPoint = player.getCenterOfGravity();
        setReferenceTime(gravityPoint.getTimePerDeltaTime());
        x -= gravityPoint.getXSpeed() * gravityPoint.getTimePerDeltaTime() * deltaTime;
        y -= gravityPoint.getYSpeed() * gravityPoint.getTimePerDeltaTime() * deltaTime;
    }



    @Override
    public double getTimePerDeltaTime() {
        return timePerDeltaTime;
    }

    public double gravitate(PhysicsObject obj) {
        Point objPos = obj.getPosition();
        double deltaX = x-objPos.x;
        double deltaY = y-objPos.y;
        double distanceSquare = Math.pow(deltaX,2)+Math.pow(deltaY,2);
        double gravStrength = 1.25;
        if(signPositive()!=obj.signPositive()){
            gravStrength = -1.25;
        }
        if(distanceSquare>=.1) {
            gravStrength *= Math.pow(obj.getTimePerDeltaTime()-timePerDeltaTime,2)/distanceSquare;
            xSpeed -= deltaX * gravStrength* deltaTime;
            ySpeed -= deltaY * gravStrength* deltaTime;
        }
        return gravStrength;
    }

    public void setXAccel(double xAccel) {
        this.xAccel = xAccel;
    }

    public void setYAccel(double yAccel) {
        this.yAccel = yAccel;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void setTimePerDeltaTime(double timePerDeltaTime) {
        this.timePerDeltaTime = Limit.minDouble(timePerDeltaTime,minTimePerDeltaTime);
    }

    public void setReferenceTime(double timePerDeltaTime) {
        this.referenceTime = this.timePerDeltaTime /Limit.minDouble(timePerDeltaTime,minTimePerDeltaTime);
    }

    @Override
    public Point getPosition() {
        return this;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public void interact(PhysicsObject obj) {
        gravitate(obj);
        obj.react(this);
    }

    @Override
    public boolean signPositive() {
        return signPositive;
    }

    public void setSignPositive(boolean signPositive){
        this.signPositive = signPositive;
    }

    @Override
    public void react(PhysicsObject obj) {
        gravitate(obj);
    }
}
