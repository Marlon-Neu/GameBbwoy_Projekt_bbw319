package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;
import ch.bbw.gamebbwoy.utils.DeltaTime;
import ch.bbw.gamebbwoy.utils.Limit;

public class MovingGameSquare implements PhysicsObject,GameObject, PixelDrawing {
    private final Point position;
    private final Point linePoint;
    private final GravityPoint gravityPoint;
    private StaticGameSquare square;
    private double speed;
    private final double unitX;
    private final double unitY;
    private final double deltaTime = DeltaTime.dT();
    private double referenceTime = 1;
    private boolean goingForward = true;
    private Bounds bounds = null;

    private MovingGameSquare(Point position, Point linePoint, double speed){
        this.position = position;
        this.linePoint = linePoint;
        this.speed = speed;
        gravityPoint = new GravityPoint(position);
        double deltaX = position.x-linePoint.x;
        double deltaY = position.y-linePoint.y;
        double distance = Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2));
        unitX = deltaX/distance;
        unitY = deltaY/distance;
    }
    public MovingGameSquare(Point position, Point linePoint, double speed,double angleInRadians,int size) {
        this(position,linePoint,speed);
        square = new StaticGameSquare(gravityPoint,angleInRadians,size);
    }
    public MovingGameSquare(Point position, Point linePoint, double speed,double angleInRadians,int size,double turnSpeed) {
        this(position,linePoint,speed);
        square = new StaticGameSquare(gravityPoint,angleInRadians,size,turnSpeed);
    }
    public MovingGameSquare(Point position, Point linePoint, double speed,double angleInRadians,int size,int turnSpeedRatio) {
        this(position,linePoint,speed);
        square = new StaticGameSquare(gravityPoint,angleInRadians,size,turnSpeedRatio);
    }

    @Override
    public void update() {
        double xSpeed;
        double ySpeed;
        if(goingForward){
            xSpeed = unitX*speed;
            ySpeed = unitY*speed;
        }
        else {
            xSpeed = -unitX*speed;
            ySpeed = -unitY*speed;
        }
        gravityPoint.x += xSpeed *referenceTime*deltaTime*1.6;
        gravityPoint.y += ySpeed *referenceTime*deltaTime*1.6;
        if(Math.abs(unitY)<Math.abs(unitX)){
            double lesser = Math.min(linePoint.x,2*position.x - linePoint.x);
            double greater = Math.max(linePoint.x,2*position.x - linePoint.x);
            if(gravityPoint.x<lesser){
                goingForward = !goingForward;
            }
            if(gravityPoint.x > greater){
                goingForward = !goingForward;
            }
        }
        else {
            double lesser = Math.min(linePoint.y,2*position.y - linePoint.y);
            double greater = Math.max(linePoint.y,2*position.y - linePoint.y);
            if(gravityPoint.y<lesser){
                goingForward = !goingForward;
            }
            if(gravityPoint.y > greater){
                goingForward = !goingForward;
            }
        }
        square.update();
    }

    @Override
    public void update(PlayerSquare player) {
        GravityPoint gravityPoint = player.getCenterOfGravity();
        this.gravityPoint.setTimePerDeltaTime(gravityPoint.getTimePerDeltaTime());
        square.setReferenceTime(gravityPoint.getTimePerDeltaTime());
        setReferenceTime(gravityPoint.getTimePerDeltaTime());
        double deltaX = gravityPoint.getXSpeed() * gravityPoint.getTimePerDeltaTime() * deltaTime;
        double deltaY = gravityPoint.getYSpeed() * gravityPoint.getTimePerDeltaTime() * deltaTime;
        this.gravityPoint.x -= deltaX;
        this.gravityPoint.y -= deltaY;
        position.x -= deltaX;
        position.y -= deltaY;
        linePoint.x -= deltaX;
        linePoint.y -= deltaY;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public double getSpeed() {
        return speed;
    }

    public void setReferenceTime(double timePerDeltaTime) {
        this.referenceTime = square.getTimePerDeltaTime()/Limit.minDouble(timePerDeltaTime, DeltaTime.minTpDT());
    }

    @Override
    public Point getPosition() {
        return gravityPoint;
    }
    @Override
    public double getTimePerDeltaTime() {
        return square.getTimePerDeltaTime();
    }



    @Override
    public int getSize() {
        return square.getSize();
    }

    @Override
    public boolean signPositive() {
        return square.signPositive();
    }

    @Override
    public void interact(PhysicsObject obj) {
        gravityPoint.setTimePerDeltaTime(getTimePerDeltaTime());
        gravityPoint.setSignPositive(signPositive());
        obj.react(gravityPoint);
    }

    @Override
    public void react(PhysicsObject obj) {
    }

    @Override
    public void tick(PixelDisplay graphic) {
        if(bounds == null) {
            new Line(new Point(linePoint.x, linePoint.y),
                    new Point(2 * position.x - linePoint.x, 2 * position.y - linePoint.y),
                    PixelColor.LOW).tick(graphic);
        }
        else {
            new BoundedLine(new Point(linePoint.x, linePoint.y),
                    new Point(2 * position.x - linePoint.x, 2 * position.y - linePoint.y),
                    PixelColor.LOW, bounds).tick(graphic);
        }
        square.tick(graphic);
    }
}
