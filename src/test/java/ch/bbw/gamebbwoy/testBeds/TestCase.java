package ch.bbw.gamebbwoy.testBeds;

import ch.bbw.gamebbwoy.GravityPoint;

import java.util.ArrayList;
import java.util.List;

public class TestCase {

    private static final List<GravityPoint> points = new ArrayList<>();
    private static final GravityPoint point0 = new GravityPoint(60,50);
    private static final GravityPoint point1 = new GravityPoint(60,80);
    private static final GravityPoint point2 = new GravityPoint(90,65);
    private TestCase(){};

    public static List<GravityPoint> twoPointSameDeltaTest(){
        point0.setTimePerDeltaTime(2);
        point1.setTimePerDeltaTime(2);
        points.add(point0);
        points.add(point1);
        return points;
    }

    public static List<GravityPoint> twoPointsDifferentDeltaTest(){
        point0.setTimePerDeltaTime(2);
        point1.setTimePerDeltaTime(1);
        points.add(point0);
        points.add(point1);
        return points;
    }

    public static List<GravityPoint> twoPointsOppositeDeltaTest(){
        point0.setTimePerDeltaTime(4);
        point1.setTimePerDeltaTime(4);
        points.add(point0);
        points.add(point1);
        return points;
    }

    public static List<GravityPoint> twoPointsNegativeDeltaTest(){
        point0.setTimePerDeltaTime(2);
        point1.setTimePerDeltaTime(2);
        points.add(point0);
        points.add(point1);
        return points;
    }
    public static List<GravityPoint> twoPointsOrbitingTest(){
        point0.setTimePerDeltaTime(2);
        point1.setTimePerDeltaTime(1);
        point0.setXSpeed(1);
        point1.setXSpeed(-1);
        points.add(point0);
        points.add(point1);
        return points;
    }

    public static List<GravityPoint> threePointsDefaultTest(){
        points.add(point0);
        points.add(point1);
        points.add(point2);
        return points;
    }

    public static List<GravityPoint> threePointsDifferentDeltaTest(){
        point0.setTimePerDeltaTime(2);
        point1.setTimePerDeltaTime(1);
        point2.setTimePerDeltaTime(4);
        points.add(point0);
        points.add(point1);
        points.add(point2);
        return points;
    }

}
