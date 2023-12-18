package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;

public class BoundedLine extends Line{
    private Bounds drawingBound;
    private PixelColor color;
    public BoundedLine(Point point0, Point point1, Bounds drawingBound) {
        super(point0, point1);
        color = PixelColor.BLACK;
        this.drawingBound = drawingBound;
    }

    public BoundedLine(Point point0, Point point1, PixelColor color, Bounds drawingBound) {
        super(point0, point1, color);
        this.color=color;
        this.drawingBound = drawingBound;
    }

    @Override
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
            if((drawingBound.left()<x&&x< drawingBound.right())&&(drawingBound.top()<=y&&y<drawingBound.bottom())) {
                graphic.setPixel(x, y, color);
            }
            if(D>0){
                y += yi;
                D -= 2*dx;
            }
            D += 2*dy;
        }
    }

    @Override
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
            if((drawingBound.left()<x&&x< drawingBound.right())&&(drawingBound.top()<=y&&y<drawingBound.bottom())){
                graphic.setPixel(x, y, color);
            }
            if(D>0){
                x += xi;
                D -= 2*dy;
            }
            D += 2*dx;
        }
    }
}
