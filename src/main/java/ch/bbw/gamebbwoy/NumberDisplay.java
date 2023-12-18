package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;

import java.util.ArrayList;
import java.util.List;

public class NumberDisplay implements PixelDrawing {
    private static final int width = 4;
    private static final int height = 7;
    private int maxDigits=8;
    private int displayWidth = (width)*(maxDigits);

    private Point pos;
    private final Box border;
    private final BoxFill fill;
    private final List<Integer[]> numbers = new ArrayList<>(){
        {
            add(new Integer[]{-1,3,3,-1,3,-1,-1,3,3,-1,-1,3,-1,-1,-1,-1,3,-1,-1,3,3,-1,-1,3,-1,3,3,-1});
            add(new Integer[]{-1,-1,-1,-1,-1,-1,-1,3,-1,-1,-1,3,-1,-1,-1,-1,-1,-1,-1,3,-1,-1,-1,3,-1,-1,-1,-1});
            add(new Integer[]{-1,3,3,-1,-1,-1,-1,3,-1,-1,-1,3,-1,3,3,-1,3,-1,-1,-1,3,-1,-1,-1,-1,3,3,-1,});
            add(new Integer[]{-1,3,3,-1,-1,-1,-1,3,-1,-1,-1,3,-1,3,3,-1,-1,-1,-1,3,-1,-1,-1,3,-1,3,3,-1,});
            add(new Integer[]{-1,-1,-1,-1,3,-1,-1,3,3,-1,-1,3,-1,3,3,-1,-1,-1,-1,3,-1,-1,-1,3,-1,-1,-1,-1,});
            add(new Integer[]{-1,3,3,-1,3,-1,-1,-1,3,-1,-1,-1,-1,3,3,-1,-1,-1,-1,3,-1,-1,-1,3,-1,3,3,-1,});
            add(new Integer[]{-1,3,3,-1,3,-1,-1,-1,3,-1,-1,-1,-1,3,3,-1,3,-1,-1,3,3,-1,-1,3,-1,3,3,-1,});
            add(new Integer[]{-1,3,3,-1,-1,-1,-1,3,-1,-1,-1,3,-1,-1,-1,-1,-1,-1,-1,3,-1,-1,-1,3,-1,-1,-1,-1,});
            add(new Integer[]{-1,3,3,-1,3,-1,-1,3,3,-1,-1,3,-1,3,3,-1,3,-1,-1,3,3,-1,-1,3,-1,3,3,-1,});
            add(new Integer[]{-1,3,3,-1,3,-1,-1,3,3,-1,-1,3,-1,3,3,-1,-1,-1,-1,3,-1,-1,-1,3,-1,3,3,-1,});
        }
    };
    private int num;
    public NumberDisplay(Point pos,int num){
        this.pos=pos;
        this.num=num;
        this.border = new Box(height+1,width+1,2,displayWidth-width+2,pos);
        this.fill = new BoxFill(height,width,1,displayWidth-width+1,pos,PixelColor.WHITE);
    }
    public NumberDisplay(Point pos,int num,int maxDigits){
        this.pos=pos;
        this.num=num;
        this.maxDigits = maxDigits;
        displayWidth = (width+1)*(maxDigits);
        this.border = new Box(height+1,width+1,2,displayWidth-width+1,pos);
        this.fill = new BoxFill(height+1,width+1,1,displayWidth-width+1,pos,PixelColor.WHITE);
    }
    public void setNum(int num) {
        this.num = num;
    }
    public int getNum() {
        return num;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public void setFillColor(PixelColor fillColor) {
        fill.setColor(fillColor);
    }

    public int getWidth() {
        return displayWidth;
    }

    @Override
    public void tick(PixelDisplay graphic) {
        fill.tick(graphic);
        boolean finished = num<0;
        int digits = num;
        int digit = 0;
        while(!finished){
            int newX = (int)pos.x - digit*(width+1);
            if(0<newX&&newX+width<graphic.getPixelWidth()){
                Integer[] toDisplay=numbers.get(digits%10);
                for (int h = 0; h < height; h++) {
                    for(int w = 0; w < width; w++)  {
                        var color = toDisplay[h*width+ w];
                        if(0<h+pos.y&&h+pos.y< graphic.getPixelHeight()) {
                            if(color!=-1){
                                graphic.setPixel(w + newX, h +(int) pos.y, PixelColor.fromValue(color));
                            }
                        }
                    }
                }
            }
            digit++;
            digits /= 10;
            if(digits==0||maxDigits-digit<=0){
                finished=true;
            }

        }
        border.tick(graphic);
    }
}
