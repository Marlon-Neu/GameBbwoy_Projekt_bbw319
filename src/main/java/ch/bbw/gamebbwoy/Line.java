package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;

public class Line implements PixelDrawing {
    private Point point0;
    private Point point1;
    private PixelColor color;

    public Line(Point point0, Point point1) {
        this.point0 = point0;
        this.point1 = point1;
        color = PixelColor.BLACK;
    }

    public Line(Point point0, Point point1,PixelColor color) {
        this.point0 = point0;
        this.point1 = point1;
        this.color = color;
    }

    public void setPoint0(Point point0) {
        this.point0 = point0;
    }
    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint0() {
        return point0;
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoints(Point point0, Point point1) {
        this.point0 = point0;
        this.point1 = point1;
    }

    public void setColor(PixelColor color) {
        this.color = color;
    }

    public void drawLine(PixelDisplay graphic){
        int dx = (int)point1.x - (int)point0.x;
        int dy = (int)point1.y- (int)point0.y;
        if(Math.abs(dy) <  Math.abs(dx)){
            if(point0.x> point1.x){
                drawLineLow( (int)point1.x,(int)point1.y,(int)point0.x,(int)point0.y,graphic);
            }
            else {
                drawLineLow((int)point0.x,(int)point0.y, (int)point1.x,(int)point1.y,graphic);
            }
        }
        else {
            if(point0.y>point1.y){
                drawLineHigh( (int)point1.x,(int)point1.y,(int)point0.x,(int)point0.y,graphic);
            }
            else {
                drawLineHigh((int)point0.x,(int)point0.y, (int)point1.x,(int)point1.y,graphic);
            }
        }
    }
    public void drawLineLow(int x0,int y0, int x1, int y1, PixelDisplay graphic){
        int dx = x1-x0;
        int dy = y1-y0;
        int yi = 1;
        if(dy < 0){
            yi = -1;
            dy = -dy;
        }
        int D = 2*dy -dx;
        int y = y0;
        for(int x=x0;x<=x1;x++){
            if((0<=x&&x<graphic.getPixelWidth())&&(0<=y&&y<graphic.getPixelHeight())) {
                graphic.setPixel(x, y, color);
            }
            if(D>0){
                y += yi;
                D -= 2*dx;
            }
            D += 2*dy;
        }
    }
    public void drawLineHigh(int x0,int y0, int x1, int y1, PixelDisplay graphic){
        int dx = x1-x0;
        int dy = y1-y0;
        int xi = 1;
        if(dx < 0){
            xi = -1;
            dx = -dx;
        }
        int D = 2*dx-dy;
        int x = x0;
        for(int y=y0;y<=y1;y++){
            if((0<=x&&x<graphic.getPixelWidth())&&(0<=y&&y<graphic.getPixelHeight())){
                graphic.setPixel(x, y, color);
            }
            if(D>0){
                x += xi;
                D -= 2*dy;
            }
            D += 2*dx;
        }
    }

    @Override
    public void tick(PixelDisplay graphic) {
        drawLine(graphic);
    }

    public double getDistance(){
        return Math.sqrt(Math.pow(getDeltaX(), 2)+Math.pow(getDeltaY(), 2));
    }
    public double getDeltaX(){
        return point0.x-point1.x;
    }
    public double getDeltaY(){
        return point0.y-point1.y;
    }
}
