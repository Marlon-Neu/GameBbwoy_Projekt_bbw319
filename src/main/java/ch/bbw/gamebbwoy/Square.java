package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;

public class Square implements PixelDrawing{
    private Point position;
    private double angleInRadians;
    private int size;
    private PixelColor color=PixelColor.BLACK;
    private final Point[] points = new Point[4];

    public Square(Point position, double angleInRadians, int size) {
        this.position = position;
        this.angleInRadians = angleInRadians;
        this.size = size;
        for(int i = 0;i<points.length;i++){
            points[i] = new Point(0,0);
        }
        updatePoints();
    }
    public void setPosition(Point position) {
        this.position.x = position.x;
        this.position.y = position.y;
        updatePoints();
    }
    public void setAngleInRadians(double angleInRadians) {
        this.angleInRadians = angleInRadians;
        updatePoints();
    }
    public void setSize(int size) {
        this.size = size;
        updatePoints();
    }

    public void setColor(PixelColor color) {
        this.color = color;
    }

    public double getAngleInRadians() {
        return angleInRadians;
    }

    public int getSize() {
        return size;
    }

    public Point getPosition() {
        return position;
    }

    public Point[] getPoints() {
        return points;
    }

    private void updatePoints(){
        for(int i=0;i<points.length;i++){
            int signX= i%3==0?1:-1;
            int signY= i<2?1:-1;
            Point newPoint = pointRotate(new Point(signX*size,signY*size));
            points[i].x = newPoint.x;
            points[i].y = newPoint.y;
        }
    }

    private Point pointRotate(Point point){
        double newX = point.x*Math.cos(angleInRadians) - point.y*Math.sin(angleInRadians);
        double newY = point.x*Math.sin(angleInRadians) + point.y*Math.cos(angleInRadians);
        return new Point(newX + position.x,newY + position.y);
    }

    @Override
    public void tick(PixelDisplay graphic) {
        updatePoints();
        for(int i=0;i<3;i++){
            new Line(points[i],points[i+1],color).tick(graphic);
        }
        new Line(points[3],points[0],color).tick(graphic);
    }
}
