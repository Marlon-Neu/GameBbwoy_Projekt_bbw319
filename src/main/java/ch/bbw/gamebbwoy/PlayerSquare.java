package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.ButtonListener.GameButton;
import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.utils.DeltaTime;
import ch.bbw.gamebbwoy.utils.Limit;;import java.util.ArrayList;
import java.util.List;

public class PlayerSquare extends CollidingSquare implements PhysicsObject,GameObject{

    private final Square indicator;
    private final BoundedLine connector;
    private final GravityPoint centerOfGravity;
    private final double maxTurnSpeed =Math.PI/12;
    private double pullSpeed=0;
    private double pushSpeed=0;
    private final int maxNumberOfForces = 5;
    private int numberOfForces = maxNumberOfForces;
    private final double minTurnSpeed = maxTurnSpeed/6;
    private double angleInRadians;
    private double turnSpeed=maxTurnSpeed/2;
    private double clockwiseAccel=0;
    private double counterclockwiseAccel=0;
    private final List<GameObject> gameObjects = new ArrayList<>();
    private double strongestGravityStrength = 0;

    public PlayerSquare(Point position, double angleInRadians, int size,Bounds drawingBounds) {
        super(position, angleInRadians, size);
        this.angleInRadians=angleInRadians;
        this.centerOfGravity = new GravityPoint(position);
        indicator = new Square(position,angleInRadians,3);
        indicator.setColor(PixelColor.HIGH);
        connector = new BoundedLine(getPosition(),getPosition(),PixelColor.LOW,drawingBounds);
    }

    public void onButton(GameButton button, boolean isDown) {
        switch (button){
            case RIGHT -> clockwiseAccel = isDown?Math.PI/600:0;
            case LEFT -> counterclockwiseAccel = isDown?-Math.PI/600:0;
            case UP -> pull(isDown);
            case DOWN -> push(isDown);
        }
    }

    public void addGameObject(GameObject gameObject){
        gameObjects.add(gameObject);
    }
    public void removeGameObject(GameObject gameObject){
        gameObjects.remove(gameObject);
        if(gameObject.getPosition().equals(connector.getPoint1())){
            connector.setPoint1(getPosition());
        }
    }
    public double getTurnSpeed() {
        return turnSpeed;
    }

    public int getNumberOfForces() {
        return numberOfForces;
    }

    public GravityPoint getCenterOfGravity() {
        return centerOfGravity;
    }

    @Override
    public void update(){
        if(pushSpeed>0||pullSpeed>0) {
            double distance = connector.getDistance();
            double unitX = connector.getDeltaX() / distance;
            double unitY = connector.getDeltaY() / distance;
            double newXSpeed = centerOfGravity.getXSpeed() + unitX*(pushSpeed-pullSpeed)*DeltaTime.dT()*3;
            double newYSpeed = centerOfGravity.getYSpeed() + unitY*(pushSpeed-pullSpeed)*DeltaTime.dT()*3;
            centerOfGravity.setXSpeed(newXSpeed);
            centerOfGravity.setYSpeed(newYSpeed);
            pushSpeed -= pushSpeed*DeltaTime.dT()*2;
            pullSpeed -= pullSpeed*DeltaTime.dT()*2;
            if(pushSpeed < 0.5){
                pushSpeed = 0;
            }
            if(pullSpeed < 0.5){
                pullSpeed = 0;
            }
        }


        turnSpeed = Limit.maxDouble(turnSpeed+clockwiseAccel+counterclockwiseAccel,maxTurnSpeed);
        turnSpeed = Limit.minDouble(turnSpeed,minTurnSpeed);
        if(turnSpeed==minTurnSpeed&&counterclockwiseAccel!=0){
            turnSpeed = -minTurnSpeed;
        }
        if(turnSpeed==-minTurnSpeed&&clockwiseAccel!=0){
            turnSpeed = minTurnSpeed;
        }
        angleInRadians += turnSpeed;
        if(angleInRadians<0){
            angleInRadians += 2*Math.PI;
        }
        if(angleInRadians>=2*Math.PI){
            angleInRadians -= 2*Math.PI;
        }
        for (GameObject gameObject:gameObjects){
            gameObject.update(this);
        }

        super.setAngleInRadians(angleInRadians);
        indicator.setAngleInRadians(angleInRadians);
    }
    private void pull(boolean isDown){
        if(isDown&&numberOfForces>0&&pullSpeed<0.2){
            pullSpeed = 16;
            numberOfForces--;
        }
    }

    private void push(boolean isDown){
        if(isDown&&numberOfForces>0&&pushSpeed<0.2){
            pushSpeed = 16;
            numberOfForces--;
        }
    }

    private void addForces(){
        if(numberOfForces<maxNumberOfForces){
            numberOfForces++;
        }
    }

    public void update(PlayerSquare playerSquare){
    }

    @Override
    public void tick(PixelDisplay graphic) {
        indicator.tick(graphic);
        super.tick(graphic);
        connector.tick(graphic);
        strongestGravityStrength = 0;
    }

    @Override
    public boolean inBounds(Bounds bounds) {
        return true;
    }

    @Override
    public double getTimePerDeltaTime() {
        return maxTurnSpeed/Math.max(maxTurnSpeed-Math.abs(turnSpeed),minTurnSpeed);
    }

    @Override
    public boolean signPositive() {
        return turnSpeed>=0;
    }

    @Override
    public void interact(PhysicsObject obj) {
        centerOfGravity.setTimePerDeltaTime(getTimePerDeltaTime());
        centerOfGravity.setSignPositive(signPositive());
        double gravityStrength = Math.abs(
                centerOfGravity.gravitate(obj));
        double centeringX = 0.99995;
        double centeringY = 0.99995;
        if(Math.abs(centerOfGravity.getYSpeed())>3) {
            centeringX = 0.995;
        }
        centerOfGravity.setXSpeed(centerOfGravity.getXSpeed()*centeringX);
        if(Math.abs(centerOfGravity.getYSpeed())>12){
            centeringY = 0.99;
        }
        centerOfGravity.setYSpeed(centerOfGravity.getYSpeed()*centeringY);
        if(gravityStrength> strongestGravityStrength){
            strongestGravityStrength = gravityStrength;
            connector.setPoint1(obj.getPosition());
        }
        obj.react(centerOfGravity);
    }
    @Override
    public void react(PhysicsObject obj) {
        centerOfGravity.gravitate(obj);
    }

    @Override
    public boolean collides(CollisionObject c) {
        boolean collides = super.collides(c);
        if(c instanceof ForcesUp){
            if(collides){
                addForces();
            }
        }
        return collides;
    }
}
