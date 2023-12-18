package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.ButtonListener;
import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;
import ch.bbw.gamebbwoy.internal.GameBbwoy;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Game implements PixelDrawing, ButtonListener {

    Background bg;
    NumberDisplay scoreDisplay;
    NumberDisplay xsDisplay;
    NumberDisplay ysDisplay;


    Bounds drawingBoundary;
    Bounds gameBoundary;

    World world;
    final int minObjects = 9;
    Point playerPoint;
    PlayerSquare player;
    PlayerHeightTracker heightTracker;
    Square testSquare;

    @Override
    public void initialize(PixelDisplay graphic) {
        PixelDrawing.super.initialize(graphic);
        scoreDisplay = new NumberDisplay(new Point(graphic.getPixelWidth()-8,
                graphic.getPixelHeight()-15),123,4);
        xsDisplay = new NumberDisplay(new Point(graphic.getPixelWidth()-8,
                graphic.getPixelHeight()-30),123,4);
        ysDisplay = new NumberDisplay(new Point(graphic.getPixelWidth()-8,
                graphic.getPixelHeight()-45),123,4);
        drawingBoundary =  new Bounds(graphic.getPixelHeight(), graphic.getPixelWidth()-(scoreDisplay.getWidth()+8),0,scoreDisplay.getWidth()+8);
        gameBoundary = new Bounds(graphic.getPixelHeight()*2,
                graphic.getPixelWidth()*2,
                -graphic.getPixelWidth()*9,
                -graphic.getPixelWidth()*2);
        bg = new Background(drawingBoundary.left(), drawingBoundary.right());
        playerPoint = new Point(graphic.getPixelWidth()/2.0, graphic.getPixelHeight()*2.0/3);
        player = new PlayerSquare(playerPoint,0,5,drawingBoundary);
        heightTracker = new PlayerHeightTracker();
        world = new World(graphic, drawingBoundary,gameBoundary, player);
        world.add(heightTracker);
        StaticGameSquare initialSquare = new StaticGameSquare(new Point(graphic.getPixelWidth()/2.0, playerPoint.y-30),
                1,4,2);
        MovingGameSquare movingSquare = new MovingGameSquare(new Point(graphic.getPixelWidth()/2.0, playerPoint.y-50),new Point(graphic.getPixelWidth()/2.0+20, playerPoint.y-60),3,0,4,1);
        movingSquare.setBounds(drawingBoundary);
        world.add(initialSquare);
        world.add(movingSquare);
        GravityPoint test = new GravityPoint(playerPoint.x,30);
        testSquare = new Square(test,0,3);
        //world.add(test);
    }

    public static void main(String[] args) throws Throwable{
        GameBbwoy.playGame(new Game());
    }

    @Override
    public void tick(PixelDisplay graphic) {
        bg.tick(graphic);
        if(world.getObjectNum()<minObjects){
           world.add(generate());
        }
        if(world.getNumberOfBG() < 60){
            world.add(generateBGStars());
        }
        world.update();
        //testSquare.tick(graphic);
        scoreDisplay.setNum((int)(heightTracker.getPlayerHeight()/10));
        scoreDisplay.tick(graphic);
        xsDisplay.setNum((int) Math.abs(player.getCenterOfGravity().getXSpeed()));
        ysDisplay.setNum((int) Math.abs(player.getCenterOfGravity().getYSpeed()));
        xsDisplay.tick(graphic);
        ysDisplay.tick(graphic);
    }

    public GameObject generate(){
        IntStream stream = new Random().ints(10,-15,16);
        switch (new Random().nextInt(4)){
            case 0:
                return generateSGS(stream.limit(4).boxed().toList());
            case 1:
                return generateMGS(stream.limit(7).boxed().toList());
            case 2:
        }
        return generateSGS(stream.limit(4).boxed().toList());
    }

    public BackgroundStars generateBGStars(){
        List<Integer> randomNums = new Random().ints(3,-15,16).boxed().toList();
        return new BackgroundStars(
                playerPoint.x+randomNums.get(0)*3,
                playerPoint.y-randomNums.get(1)* (world.getNumberOfBG()/6),
                (Math.abs(randomNums.get(2))+5)/25d,
                PixelColor.fromValue(new Random().nextInt(2)+1));
    }

    private StaticGameSquare generateSGS(List<Integer> randomNums){
        Point point = new Point(playerPoint.x, playerPoint.y);
        point.x += randomNums.get(0)*2;
        point.y -= (world.getObjectNum()-2)*60 - randomNums.get(1)/3d;
        return new StaticGameSquare(point,0,
                Math.abs(randomNums.get(2)/5)+3,
                (randomNums.get(3)+2)/3);
    }

    private MovingGameSquare generateMGS(List<Integer> randomNums){
        Point point = new Point(playerPoint.x, playerPoint.y);
        point.x += randomNums.get(0)*2;
        point.y -= (world.getObjectNum()-2)*75 - randomNums.get(1)/3d;
        Point linePoint = new Point(point.x, point.y);
        linePoint.x += randomNums.get(2)*2;
        linePoint.y -= world.getObjectNum() - randomNums.get(3)*2;
        MovingGameSquare mgs = new MovingGameSquare(point,linePoint,
                randomNums.get(4)+5/20d,0,
                Math.abs(randomNums.get(5)/5)+3,
                randomNums.get(6)/3);
        mgs.setBounds(drawingBoundary);
        return mgs;
    }

    @Override
    public void onButtonPress(GameButton button) {
        player.onButton(button,true);
    }

    @Override
    public void onButtonRelease(GameButton button) {
        player.onButton(button,false);
    }
}
