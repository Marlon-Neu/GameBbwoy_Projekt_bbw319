package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;

import java.util.Objects;

public class Point {
    public double x;
    public double y;

    public Point(double x,double y){
        this.x=x;
        this.y=y;
    }

    public Point(int x,int y){
        this.x=x;
        this.y=y;
    }

    public Point shiftX(int num){
        x+=num;
        return this;
    }

    public Point shiftY(int num){
        y+=num;
        return this;
    }

    public Point shiftX(double num){
        x+=num;
        return this;
    }

    public Point shiftY(double num){
        y+=num;
        return this;
    }

    public static Point origin(){
        return new Point(0,0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
