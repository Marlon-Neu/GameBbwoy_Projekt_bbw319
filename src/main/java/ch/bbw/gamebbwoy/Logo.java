package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;
import ch.bbw.gamebbwoy.internal.GameBbwoy;

import java.util.Random;

public class Logo implements PixelDrawing {
    CollidingSquare cSquare = new CollidingSquare(new Point(70,80),Math.PI/3,20);
    CollidingSquare cSquare1 = new CollidingSquare(new Point(80,20),0,7);
    Line connect = new Line(cSquare1.getPosition(),cSquare.getPoints()[0], PixelColor.HIGH);
    Square square1 = new Square(cSquare1.getPoints()[1],0,4);
    Square square2 = new Square(cSquare1.getPoints()[3],0,5);
    static final double radDelta = (Math.PI)/120;
    double rad = 0;
    int count = 0;
    final Background bg;
    Random ran = new Random();

    @Override
    public void initialize(PixelDisplay graphic) {
        PixelDrawing.super.initialize(graphic);
        square1.setColor(PixelColor.HIGH);
        square2.setColor(PixelColor.LOW);
    }

    public Logo(Background bg){
        this.bg = bg;
    }

    @Override
    public void tick(PixelDisplay graphic) {
        if (count<120) {
            bg.tick(graphic);
        }
        if(cSquare.collides(cSquare1)){
            cSquare1.setPosition(cSquare1.getPosition().shiftY(-1));
        }
        else{
            cSquare1.setPosition(cSquare1.getPosition().shiftY(1));
        }
        cSquare.tick(graphic);
        cSquare1.tick(graphic);
        cSquare.setAngleInRadians(-rad);
        cSquare1.setAngleInRadians(rad);
        connect.tick(graphic);
        square1.tick(graphic);
        square1.setAngleInRadians(-rad);
        square2.tick(graphic);
        square2.setAngleInRadians(rad);
        rad += radDelta;
        if(rad >= 2*Math.PI){
            rad -= 2*Math.PI;
        }
        if(count>180){
                for (int i = 0; i < 30; i++) {
                    graphic.setPixel(ran.nextInt(graphic.getPixelWidth())
                    , ran.nextInt(graphic.getPixelHeight())
                    , PixelColor.fromValue(ran.nextInt(1, 3)));
                }

        }
        count++;
    }

    public boolean readyToEnd(){
        return count>360;
    }
}
