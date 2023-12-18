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
    NumberDisplay forcesDisplay;
    ForcesIcon forcesIcon;
    Bounds drawingBoundary;
    Bounds gameBoundary;

    World world;
    final int minObjects = 9;
    Point playerPoint;
    PlayerSquare player;
    PlayerHeightTracker heightTracker;
    Logo logo;
    boolean gameStart = false;

    @Override
    public void initialize(PixelDisplay graphic) {
        PixelDrawing.super.initialize(graphic);
        scoreDisplay = new NumberDisplay(new Point(graphic.getPixelWidth()-8,
                graphic.getPixelHeight()-15),123,4);
        forcesDisplay = new NumberDisplay(new Point(20,
                5),123,1);
        forcesIcon = new ForcesIcon(new Point(3,4));
        drawingBoundary =  new Bounds(graphic.getPixelHeight(), graphic.getPixelWidth()-(scoreDisplay.getWidth()+8),0,scoreDisplay.getWidth()+8);
        gameBoundary = new Bounds(graphic.getPixelHeight()*2,
                graphic.getPixelWidth()*2,
                -graphic.getPixelHeight()*10,
                -graphic.getPixelWidth()*2);
        bg = new Background(drawingBoundary.left(), drawingBoundary.right());
        logo = new Logo(bg);
        playerPoint = new Point(graphic.getPixelWidth()/2.0, graphic.getPixelHeight()*2.0/3);
        player = new PlayerSquare(playerPoint,0,5,drawingBoundary);
        heightTracker = new PlayerHeightTracker();
        world = new World(graphic, drawingBoundary,gameBoundary, player);
        world.add(heightTracker);
        StaticGameSquare initialSquare = new StaticGameSquare(new Point(graphic.getPixelWidth()/2.0, playerPoint.y-50),
                1,4,2);
        world.add(initialSquare);
    }

    public static void main(String[] args) throws Throwable{
        GameBbwoy.playGame(new Game());
    }

    @Override
    public void tick(PixelDisplay graphic) {
        if(!(logo.readyToEnd()&&gameStart))
        {
            logo.tick(graphic);
        }
        else{
            bg.tick(graphic);
            if(world.getObjectNum()<minObjects){
               world.add(generate());
            }
            if(world.getNumberOfBG() < 60){
                world.add(generateBGStars());
            }
            world.update();
            scoreDisplay.setNum((int)(heightTracker.getPlayerHeight()/10));
            scoreDisplay.tick(graphic);
            forcesDisplay.setNum(player.getNumberOfForces());
            forcesDisplay.tick(graphic);
            forcesIcon.tick(graphic);
        }
    }

    public GameObject generate(){
        IntStream stream = new Random().ints(10,-15,16);
        return switch (new Random().nextInt(3)) {
            case 1 -> generateMGS(stream.limit(7).boxed().toList());
            case 2 -> generateFUS(stream.limit(4).boxed().toList());
            default -> generateSGS(stream.limit(4).boxed().toList());
        };
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
        point.y -= (world.getObjectNum()-2)*85 - randomNums.get(1)/4d;
        return new StaticGameSquare(point,0,
                Math.abs(randomNums.get(2)/5)+3,
                (randomNums.get(3)+2)/3);
    }

    private MovingGameSquare generateMGS(List<Integer> randomNums){
        Point point = new Point(playerPoint.x, playerPoint.y);
        point.x += randomNums.get(0)*2;
        point.y -= (world.getObjectNum()-2)*85 - randomNums.get(1)/4d;
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

    private ForcesUp generateFUS(List<Integer> randomNums){
        Point point = new Point(playerPoint.x, playerPoint.y);
        point.x += randomNums.get(0)*2;
        point.y -= (world.getObjectNum()-2)*75 - randomNums.get(1)/4d;
        return new ForcesUp(point,0,
                Math.abs(randomNums.get(2)/5)+1,
                (randomNums.get(3)+2)/3);
    }

    @Override
    public void onButtonPress(GameButton button) {
        gameStart = true;
        player.onButton(button,true);
    }

    @Override
    public void onButtonRelease(GameButton button) {
        player.onButton(button,false);
    }
}
