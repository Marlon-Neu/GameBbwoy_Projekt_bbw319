package ch.bbw.gamebbwoy.testBeds;

import ch.bbw.gamebbwoy.*;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;
import ch.bbw.gamebbwoy.internal.GameBbwoy;

import java.util.List;

public class PhysicsObjectTestBed implements PixelDrawing {

    List<GravityPoint> points = TestCase.threePointsDifferentDeltaTest();

    Box box0 = new Box(2,2,2,2,points.get(0));
    Box box1 = new Box(2,2,2,2,points.get(1));
    Box box2 = new Box(2,2,2,2,points.size()>2?points.get(2):new Point(-99,-99));

    MovingGameSquare testSquare= new MovingGameSquare(new Point(50,50),new Point(50,40),15,5,3,5);


    public static void main(String[] args) throws Throwable {
        GameBbwoy.playGame(new PhysicsObjectTestBed());
    }

    @Override
    public void initialize(PixelDisplay graphic) {
        PixelDrawing.super.initialize(graphic);
    }

    @Override
    public void tick(PixelDisplay graphic){
        graphic.clear();
        points.get(0).interact(points.get(1));
        if(points.size()>2) {
            points.get(0).interact(points.get(2));
            points.get(1).interact(points.get(2));
            points.get(2).update();
        }
        //testSquare.interact(points.get(0));
        //testSquare.interact(points.get(1));
        points.get(0).update();
        points.get(1).update();
        //testSquare.update();
        box0.tick(graphic);
        box1.tick(graphic);
        box2.tick(graphic);
        //testSquare.tick(graphic);
    }
}
