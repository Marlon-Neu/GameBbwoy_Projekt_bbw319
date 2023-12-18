package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;

import java.util.List;

public class Box implements PixelDrawing{
    private int bottom;
    private int right;
    private int top;
    private int left;
    private Point position;

    private PixelColor color = PixelColor.BLACK;

    public Box(int bottom, int right, int top, int left, Point position) {
        this.bottom = bottom;
        this.right = right;
        this.top = top;
        this.left = left;
        this.position = position;
    }
    public void setBottom(int bottom) {
        this.bottom = bottom;
    }
    public void setRight(int right) {
        this.right = right;
    }
    public void setTop(int top) {
        this.top = top;
    }
    public void setLeft(int left) {
        this.left = left;
    }
    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public Bounds getBounds(){
        return new Bounds(
                (int)position.y + bottom,
                (int)position.x + right,
                (int)position.y - top,
                (int)position.x - left);
    }

    public void setBox(int bottom,int right,int top,int left,Point position){
        this.bottom=bottom;
        this.right=right;
        this.top=top;
        this.left=left;
        this.position = position;
    }

    public void setColor(PixelColor color) {
        this.color = color;
    }

    @Override
    public void tick(PixelDisplay graphic) {
        for(int offset=-left;offset<=right;offset++){
            int x =  (int)position.x+offset;
            if(0<x&&x<graphic.getPixelWidth()){
                int posTop = (int)position.y + bottom;
                if (0<= posTop && posTop < graphic.getPixelHeight()) {
                    graphic.setPixel(x,  (int)position.y + bottom, color);
                }
                int posBottom = (int)position.y - top;
                if( 0 <= posBottom && posBottom < graphic.getPixelHeight()) {
                    graphic.setPixel(x,  (int)position.y - top, color);
                }
            }
        }
        for(int offset = -top +1; offset< bottom; offset++){
            int y = (int)position.y+offset;
            if(0<=y&&y<graphic.getPixelHeight()) {
                int posRight =(int)position.x+right;
                if(0<=posRight&&posRight<graphic.getPixelWidth()) {
                    graphic.setPixel( posRight, y, color);
                }
                int posLeft =(int)position.x-left;
                if(0<=posLeft&&posLeft<graphic.getPixelWidth()) {
                    graphic.setPixel( posLeft, y, color);
                }
            }
        }
    }
}
