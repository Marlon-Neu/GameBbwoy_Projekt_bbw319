package ch.bbw.gamebbwoy.testBeds;

import ch.bbw.gamebbwoy.*;
import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;
import ch.bbw.gamebbwoy.internal.GameBbwoy;

public class MyPixelDrawingTestBed implements PixelDrawing {

	Box box= new Box(10,10,10,10,new Point(90,30));
	Line line = new Line(new Point(111,65),new Point(90,30));

	Square square = new Square(new Point(111,65), Math.PI/6,10);

	CollidingSquare cSquare = new CollidingSquare(new Point(35,90),Math.PI/3,20);
	CollidingSquare cSquare1 = new CollidingSquare(new Point(70,98),0,6);
	CollidingSquare cSquare2 = new CollidingSquare(new Point(45,0),0,7);
	Line connect = new Line(cSquare2.getPosition(),cSquare.getPoints()[0]);
	Square square1 = new Square(cSquare2.getPoints()[1],0,4);
	Square square2 = new Square(cSquare2.getPoints()[3],0,5);
	Point numPoint = new Point(149,130);
	NumberDisplay numberDisplay = new NumberDisplay(numPoint, 0,4);
	BoxFill boxFill = new BoxFill(5,5,5,5,new Point(80,40),PixelColor.LOW);

	static final double radDelta = (Math.PI)/100;
	double rad = 0;
	boolean toggle = false;
	public static void main(String[] args) throws Throwable {
		GameBbwoy.playGame(new MyPixelDrawingTestBed());
	}

	@Override
	public void tick(PixelDisplay graphic) {
		graphic.clear();
		if(cSquare.collides(cSquare1)){
			cSquare1.setPosition(cSquare1.getPosition().shiftX(1));
		}
		else{
			cSquare1.setPosition(cSquare1.getPosition().shiftX(-1));
		}
		if(cSquare.collides(cSquare2)){
			cSquare2.setPosition(cSquare2.getPosition().shiftY(-1));
		}
		else{
			cSquare2.setPosition(cSquare2.getPosition().shiftY(1));
		}

		// sets a pixel top left
		graphic.setPixel(5, 5, PixelColor.BLACK);
		box.tick(graphic);
		line.tick(graphic);
		square.tick(graphic);
		cSquare.tick(graphic);
		cSquare1.tick(graphic);
		cSquare2.tick(graphic);
		cSquare.setAngleInRadians(-rad);
		cSquare2.setAngleInRadians(rad);
		connect.tick(graphic);
		numberDisplay.tick(graphic);
		numberDisplay.setNum((int) (rad*1000));
		square1.tick(graphic);
		square1.setAngleInRadians(-rad);
		square2.tick(graphic);
		square2.setAngleInRadians(rad);
		boxFill.tick(graphic);
		rad += radDelta;
		if(rad >= 2*Math.PI){
			rad -= 2*Math.PI;
			cSquare.setDrawCollision(toggle);
			toggle = !toggle;
		}
	}
}
